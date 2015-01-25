package drive.domain.modelling;

import drive.domain.interfaces.SystemClock;

public class SystemClockImpl implements SystemClock {

	private long lastClockTime;

	public SystemClockImpl() {
		lastClockTime = System.currentTimeMillis();
	}

	@Override
	public long getMillisecondDelta() {
		long delta = System.currentTimeMillis() - lastClockTime;
		lastClockTime = System.currentTimeMillis();
		return delta;
	}

}
