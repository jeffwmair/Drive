package drive.domain.interfaces;

public interface Transmission {

	enum ShiftType { UP, DOWN, TO_N, TO_P };
	void update(double engineTorque);
	double getTorqueLoadPct();
	void shift(ShiftType shift);
}
