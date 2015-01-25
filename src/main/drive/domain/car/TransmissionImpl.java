package drive.domain.car;

import java.util.List;

import drive.domain.interfaces.Transmission;
import drive.domain.interfaces.TransmissionGear;

public class TransmissionImpl implements Transmission {

	public TransmissionImpl(List<TransmissionGear> forwardGears, TransmissionGear reverse) {

		if (forwardGears.size() < 1 || forwardGears.size() > 6) {
			throw new IllegalArgumentException("Transmission must have at least 1 and at most 6 gears");
		}
	}

	@Override
	public void update(double engineTorque) {

	}

	@Override
	public double getTorqueLoadPct() {
		/*
		 * Load is based on: - what gear we are in - how fast we are going
		 */
		return 0;
	}

	@Override
	public void shift(ShiftType shift) {
		
	}

}
