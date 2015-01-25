package drive.domain.car;

import drive.domain.interfaces.Tachometer;

public class TachometerImpl implements Tachometer {

	private int rpm;

	@Override
	public void update(int rpm) {
		this.rpm = rpm;
		System.out.println("RPM: " + rpm);
	}

	@Override
	public int getRpm() {
		return rpm;
	}

}
