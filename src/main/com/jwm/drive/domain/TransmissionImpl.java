package com.jwm.drive.domain;

import java.util.List;

class TransmissionImpl implements Transmission {


    private TransmissionGear currentGear;

    public TransmissionImpl(List<TransmissionGear> forwardGears, TransmissionGear reverse) {
		if (forwardGears.size() < 1 || forwardGears.size() > 6) {
			throw new IllegalArgumentException("Transmission must have at least 1 and at most 6 gears");
		}
		TransmissionGear neutral = new TransmissionGearNeutral();
        currentGear = neutral;
	}

    /**
     * Pass the torque force into the transmission to turn the gear
     * @param engineTorque
     */
	@Override
	public void update(double engineTorque) {
		/**
		 * The transmission introduces some energy losses
		 */
		double TransmissionEfficiency = 0.8;
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
