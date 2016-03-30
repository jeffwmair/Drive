package com.jwm.drive.domain.modelling;

import com.jwm.drive.domain.interfaces.SystemClock;

public class SystemClockStub implements SystemClock {

	private long delta;

	public void setMillisecondDelta(long val) {
		this.delta = val;
	}
	@Override
	public long getMillisecondDelta() {
		return delta;
	}

}
