package com.jwm.drive.domain.car;

import java.util.List;

import com.jwm.drive.domain.interfaces.Transmission;
import com.jwm.drive.domain.interfaces.TransmissionGear;

public class TransmissionImpl implements Transmission {

    /**
     * The transmission introduces some energy losses
     */
    private final double TransmissionEfficiency = 0.8;

    private TransmissionGear currentGear;
	private TransmissionGear neutral = new TransmissionGearNeutral();

    public TransmissionImpl(List<TransmissionGear> forwardGears, TransmissionGear reverse) {
		if (forwardGears.size() < 1 || forwardGears.size() > 6) {
			throw new IllegalArgumentException("Transmission must have at least 1 and at most 6 gears");
		}
        currentGear = neutral;
	}

    /**
     * Pass the torque force into the transmission to turn the gear
     * @param engineTorque
     */
	@Override
	public void update(double engineTorque) {
        currentGear.applyTorque(engineTorque * TransmissionEfficiency);
	}

	@Override
	public double getTorqueLoadPct() {
		return currentGear.getLoad();
	}

	@Override
	public void shift(ShiftType shift) {
		// TODO: consider making a shift type class, and transferring any extra responsibilities there.
	}

}
