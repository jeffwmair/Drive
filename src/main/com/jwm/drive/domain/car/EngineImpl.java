package com.jwm.drive.domain.car;

import com.jwm.drive.domain.interfaces.Engine;
import com.jwm.drive.domain.interfaces.SystemClock;
import com.jwm.drive.domain.interfaces.Transmission;
import com.jwm.drive.domain.modelling.Curve;
import com.jwm.drive.domain.modelling.Curve.CurveDirection;

public class EngineImpl implements Engine {

	private static final double MIN_RPM = 1500;
	private static final double MAX_RPM = 6250;
	private static final double MIN_RPM_TIME = 100;
	private static final double NO_LOAD_RPM_PEAK_TIME_MS = 1500;

	public static Curve getTorqueCurve() {
		double[] vals = { 50, 80, 110, 125, 150, 165, 180, 180, 185, 190, 195, 198, 195, 192, 191, 190, 185, 175, 170,
				170 };
		return new Curve(vals, MIN_RPM, MAX_RPM, CurveDirection.Up);
	}
	public static Curve getRpmIncreaseCurve() {
		double[] vals = { 1500, 2000, 2800, 3500, 4100, 4600, 4900, 5151, 5350, 5600, 5800, 5975, 6100, 6200, 6250 };
		return new Curve(vals, MIN_RPM_TIME, NO_LOAD_RPM_PEAK_TIME_MS, CurveDirection.Up);
	}
	public static Curve getRpmDecreaseCurve() {
		double[] vals = { 6250, 6150, 6050, 5800, 5500, 5100, 4500, 3800, 3000, 2200, 1785, 1600, 1575, 1550, 1500 };
		return new Curve(vals, MIN_RPM_TIME, NO_LOAD_RPM_PEAK_TIME_MS, CurveDirection.Dn);
	}

	private Curve rpmUpCurve, rpmDnCurve;
	private Curve torqueCurve;
	private Transmission transmission;
	private SystemClock updateClock;
	private double torque;
	private double rpm;
	private double max_rpm;
	private boolean rpmIncreasing = true;
	private PedalPress previousAccelerator;

	public EngineImpl(Curve torqueCurve, Curve rpmUpCurve, Curve rpmDnCurve, Transmission trans, SystemClock clock) {
		this.transmission = trans;
		this.updateClock = clock;
		this.torqueCurve = torqueCurve;
		this.rpmUpCurve = rpmUpCurve;
		this.rpmDnCurve = rpmDnCurve;
		this.max_rpm = rpmUpCurve.getMax();
		this.rpm = rpmUpCurve.getFirst();
		this.previousAccelerator = PedalPress.None;
	}

	/**
	 * 1. Accelerator determines how the torque range is scaled. Eg., 1/2
	 * accelerator down means we only achieve 50% of the torque.
	 * 
	 * 2. Load of the transmission determines how quickly we move up or down the
	 * torque range
	 */
	@Override
	public void update(PedalPress accelerator) {

		Curve currentRpmCurve = getRpmCurve(accelerator);
		double targetRpm = getTargetRpm(currentRpmCurve, accelerator.getValue());
		double transmissionLoad = this.transmission.getTorqueLoadPct();
		double actualRpm = getActualRpm(currentRpmCurve, targetRpm, transmissionLoad, updateClock.getMillisecondDelta());

		rpm = actualRpm;
		torque = torqueCurve.getValueAtDomainValue(rpm);

		// update torque output to the transmission.
		this.transmission.update(torque);

	}

	/**
	 * load will be from 0 to 1.0. Basically to simulate the load, stretch the
	 * delta time. 0 means no time scaling - increase/decrease rpm as fast as
	 * possible. 1 would mean we can't increase (can decrease though) at all, so
	 * just put the time at 0 (as if no time has passed).
	 * 
	 * @param rpmCurve
	 *            The rpm curve to use to find the rpm from
	 * @param targetRpm
	 *            Ideal target rpm if enough time passes (and the load is light
	 *            enough) to allow us to get there
	 * @param load
	 *            Load on the engine (from the transmission). Load will be from
	 *            0 to 1.0
	 * @param deltaTime
	 *            How much time has passed since the last update.
	 * @return
	 */
	private double getActualRpm(Curve rpmCurve, double targetRpm, double load, long deltaTime) {

		if (load < 0 || load > 1.0) {
			throw new IllegalArgumentException(
					"Engine load cannot be less than 0 or greater than 1.0.  Provided load was:" + load);
		}
		
		double time_scaled = deltaTime;
		if (this.rpmIncreasing) {
			double scale = 1.0 - load;
			time_scaled = time_scaled * scale;
		}

		double rpmRelativeToTime = rpmCurve.getRelativeValue(rpm, time_scaled);
		if (rpmIncreasing) {
			return Math.min(targetRpm, rpmRelativeToTime);
		} else {
			return Math.max(targetRpm, rpmRelativeToTime);
		}
	}

	/**
	 * Gets either the upward or downward RPM curve depending on the position of
	 * the accelerator.
	 * 
	 * @param accelerator
	 * @return
	 */
	private Curve getRpmCurve(PedalPress accelerator) {

		rpmIncreasing = accelerator.getValue() > 0 && accelerator.getValue() >= previousAccelerator.getValue();
		previousAccelerator = accelerator;
		Curve rpmCurve = rpmIncreasing ? this.rpmUpCurve : this.rpmDnCurve;
		return rpmCurve;
	}
	private double getTargetRpm(Curve rpmCurve, double acceleratorValue) {
		double accelValue = acceleratorValue;
		if (!rpmIncreasing) {
			accelValue = 1 - accelValue;
		}
		return rpmCurve.getValueAtPctPosition(accelValue);
	}

	@Override
	public double getTorque() {
		return torque;
	}

	@Override
	public double getRpm() {
		return rpm;
	}
	@Override
	public double getMaxRpm() {
		return this.max_rpm;
	}

}
