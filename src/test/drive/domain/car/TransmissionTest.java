package com.jwm.drive.domain.car;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.jwm.drive.domain.interfaces.TransmissionGear;

public class TransmissionTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void makeTransmissionWithTooManyGearsThrowsException() {
		List<TransmissionGear> forwardGears = new ArrayList<TransmissionGear>();
		TransmissionGear reverse = null;
		exception.expect(IllegalArgumentException.class);
		TransmissionImpl trans = new TransmissionImpl(forwardGears, reverse);
	}
}
