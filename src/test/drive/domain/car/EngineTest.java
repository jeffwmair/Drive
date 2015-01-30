package drive.domain.car;

import junit.framework.Assert;

import org.junit.Test;

import drive.domain.interfaces.Engine;
import drive.domain.interfaces.Engine.PedalPress;
import drive.domain.interfaces.Transmission;
import drive.domain.modelling.Curve;
import drive.domain.modelling.SystemClockStub;

public class EngineTest {

	@Test
	public void engineRpmTestRunOutTheClock() {

		Curve torqueCurve = EngineImpl.getTorqueCurve();
		Curve rpmUpCurve = EngineImpl.getRpmIncreaseCurve();
		Curve rpmDnCurve = EngineImpl.getRpmDecreaseCurve();

		Transmission trans = new TransmissionStub();
		SystemClockStub clock = new SystemClockStub();
		Engine engine = new EngineImpl(torqueCurve, rpmUpCurve, rpmDnCurve, trans, clock);

		clock.setMillisecondDelta(1500);
		engine.update(PedalPress.Full);
		double rpm = engine.getRpm();
		Assert.assertEquals(rpmUpCurve.getMax(), rpm);
	}

	@Test
	public void engineRpmTestSmallStartingIncrement() {

		Curve torqueCurve = EngineImpl.getTorqueCurve();
		Curve rpmUpCurve = EngineImpl.getRpmIncreaseCurve();
		Curve rpmDnCurve = EngineImpl.getRpmDecreaseCurve();

		Transmission trans = new TransmissionStub();
		SystemClockStub clock = new SystemClockStub();
		Engine engine = new EngineImpl(torqueCurve, rpmUpCurve, rpmDnCurve, trans, clock);

		clock.setMillisecondDelta(15);
		engine.update(PedalPress.Full);
		double rpm = engine.getRpm();
		Assert.assertTrue(rpm > 1500 && rpm < 1600);
	}

	@Test
	public void engineTorqueCurveDown() {

		Curve torqueCurve = EngineImpl.getTorqueCurve();
		Curve rpmUpCurve = EngineImpl.getRpmIncreaseCurve();
		Curve rpmDnCurve = EngineImpl.getRpmDecreaseCurve();

		Transmission trans = new TransmissionStub();
		SystemClockStub clock = new SystemClockStub();
		
		Engine engine = new EngineImpl(torqueCurve, rpmUpCurve, rpmDnCurve, trans, clock);
		clock.setMillisecondDelta(1500);	// should take us to full rpm
		engine.update(PedalPress.Full);
		Assert.assertTrue(engine.getRpm() > 6200);
		
		clock.setMillisecondDelta(15);

		engine.update(PedalPress.None);
		Assert.assertTrue(engine.getRpm() > 6000);
		engine.update(PedalPress.None);
		Assert.assertTrue(engine.getRpm() > 6000);

	}
	
	@Test
	public void engineTorqueCurvePrintlines() {

		Curve torqueCurve = EngineImpl.getTorqueCurve();
		Curve rpmUpCurve = EngineImpl.getRpmIncreaseCurve();
		Curve rpmDnCurve = EngineImpl.getRpmDecreaseCurve();

		Transmission trans = new TransmissionStub();
		SystemClockStub clock = new SystemClockStub();
		long time = 10;
		clock.setMillisecondDelta(time);
		Engine engine = new EngineImpl(torqueCurve, rpmUpCurve, rpmDnCurve, trans, clock);

		System.out.println("Time_ms,RPM,Torque");
		long currentTime = time;
		for (int i = 0; i < 150; i++) {
			engine.update(PedalPress.Full);
			double rpm = engine.getRpm();
			double trq = engine.getTorque();
			System.out.format("%d,%s,%f,%f\n", currentTime, "Full", rpm, trq);
			currentTime += time;
		}
		for (int i = 0; i < 175; i++) {
			engine.update(PedalPress.None);
			double rpm = engine.getRpm();
			double trq = engine.getTorque();
			System.out.format("%d,%s,%f,%f\n", currentTime, "None", rpm, trq);
			currentTime += time;
		}
	}

}
