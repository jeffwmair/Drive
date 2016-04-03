package com.jwm.drive.domain.car;

class TransmissionStub implements Transmission {

	private double load;

//	public TransmissionStub(double load) {
//		this.load = load;
//	}

	@Override
	public void update(double engineTorque) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shift(ShiftType shift) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getTorqueLoadPct() {
		return load;
	}

}
