package drive.domain.car;

import drive.domain.interfaces.TransmissionGear;

/**
 * Represents a gear in the transmission
 */
public class TransmissionGearImpl implements TransmissionGear {

    private double ratio;
    private double speed;
    public TransmissionGearImpl(double gearRatio)
    {
        ratio = gearRatio;
    }

    @Override
    public  void applyTorque(double torque) {
       /* TODO: figure out an approximation to convert torque and gear ratio into a speed.
       For now: speed = (1/ratio) * torque
       That should get us in the right direction anyway.  Then fine-tune.
        */

        speed = (1 / ratio) * torque;
    }

    @Override
    public double getRatio() {
        return ratio;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public double getLoad() {
        /* todo: figure out approximation for a "load" of the gear on the upstream components.  Ie, the engine.
        Eg, the greater the load of the gear (and subsequently the transmission), the greater the load on the
         engine, which slows the ascent through the torque curve.

        Will be roughly based on the speed and the ratio.
         */
        return 0;
    }
}
