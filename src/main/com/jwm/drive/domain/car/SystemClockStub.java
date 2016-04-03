package com.jwm.drive.domain.car;

class SystemClockStub implements SystemClock {

	private long delta;

	public void setMillisecondDelta(long val) {
		this.delta = val;
	}
	@Override
	public long getMillisecondDelta() {
		return delta;
	}

}
