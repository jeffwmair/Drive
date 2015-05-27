package drive.domain.modelling;

import drive.domain.interfaces.SystemClock;

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
