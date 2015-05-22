package drive.domain.interfaces;

public interface Engine {

	enum PedalPress {
		
		None(0), Quarter(0.25), Half(0.5), ThreeQuarters(0.75), Full(1.0);
		private final double value;

		PedalPress(double value) {
			this.value = value;
		}

		public double getValue() {
			return value;
		}
	};

	void update(PedalPress accelerator);
	double getTorque();
	double getRpm();
	double getMaxRpm();

}
