package drive.domain.car;

import drive.domain.interfaces.TransmissionGear;

/**
 * Represents neutral, which is common in any transmission.
 */
public class TransmissionGearNeutral extends TransmissionGearImpl {

    public TransmissionGearNeutral() {
       super(0);
   }
}
