package com.jwm.drive.domain;

import junit.framework.Assert;
import org.junit.Test;

public class CurveTest {
	
	@Test
	public void curveTestTooSmall() {
		double[] vals = { 1, 2 };
		boolean excep = false;
		try {
			Curve c = new Curve(vals, 0, 1, Curve.CurveDirection.Up);
		} catch (Exception e) {
			excep = true;
		}
		Assert.assertEquals(true, excep);
	}

	@Test
	public void curveDomainInvalid() {
		double[] vals = { 1, 2, 3 };
		boolean excep = false;
		try {
			Curve c = new Curve(vals, 0, 0, Curve.CurveDirection.Up);
		} catch (Exception e) {
			excep = true;
		}
		Assert.assertEquals(true, excep);
	}

	@Test
	public void curveValueAtPos() {
		double[] vals = { 1, 2, 3 };
		Curve c = new Curve(vals, 0, 1, Curve.CurveDirection.Up);

		double val = c.getValueAtPctPosition(0.2);
		Assert.assertTrue(val < 2.0);
		Assert.assertTrue(val > 1.0);

		val = c.getValueAtPctPosition(0.5);
		Assert.assertTrue(val > 1.9);
		Assert.assertTrue(val < 2.1);

		val = c.getValueAtPctPosition(1.0);
		Assert.assertTrue(val > 2.9);
	}
	
	@Test
	public void curveGetRelValueRpm() {
		double[] vals = { 1500, 2000, 2800, 3500, 4100, 4600, 4900, 5151, 5350, 5600, 5800, 5975, 6100, 6200, 6250 };
		Curve c = new Curve(vals, 100, 1500, Curve.CurveDirection.Up);
		Assert.assertEquals(6250.0, c.getRelativeValue(1500, 1500));

		double val = c.getRelativeValue(1500, 10);
		Assert.assertEquals(1550.0, val);
	}

	@Test
	public void curveGetRelValue() {
		double[] vals = { 1, 2, 3, 4, 5 };
		Curve c = new Curve(vals, 1, 5, Curve.CurveDirection.Up);
		Assert.assertEquals(1.0, c.getRelativeValue(1, 0));
		Assert.assertEquals(2.0, c.getRelativeValue(1, 1));
		Assert.assertEquals(3.0, c.getRelativeValue(1, 2));
		Assert.assertEquals(1.0, c.getRelativeValue(2, -1));
		Assert.assertEquals(1.0, c.getRelativeValue(2, -2));
		Assert.assertEquals(1.0, c.getRelativeValue(2, -3));
		Assert.assertEquals(5.0, c.getRelativeValue(4, 1));
		Assert.assertEquals(5.0, c.getRelativeValue(4, 2));
	}
	
	@Test
	public void curveDownwardGetRelativeValue() {
		double[] vals = { 6250, 6150, 6050, 5800, 5500, 5100, 4500, 3800, 3000, 2200, 1785, 1600, 1575, 1550, 1500 };
		Curve c = new Curve(vals, 0, 1500, Curve.CurveDirection.Dn);
		double nextVal = c.getRelativeValue(6250.0, 15.0);
		Assert.assertTrue(nextVal > 6000);
	}

	@Test
	public void curveNearestIndex() {
		double[] vals = { 1, 2, 3, 4, 5 };
		Curve c = new Curve(vals, 0, 1, Curve.CurveDirection.Up);
		Assert.assertEquals(1, c.getNearestIndex(1.5));
		Assert.assertEquals(2, c.getNearestIndex(2.5));
		Assert.assertEquals(2, c.getNearestIndex(2.9));
		Assert.assertEquals(2, c.getNearestIndex(3.0));
		Assert.assertEquals(2, c.getNearestIndex(3.1));
		Assert.assertEquals(4, c.getNearestIndex(4.9));
		Assert.assertEquals(4, c.getNearestIndex(5.0));
	}

	@Test
	public void curveGetValAtDomainVal() {
		double[] vals = { 1, 2, 3, 4, 5 };
		Curve c = new Curve(vals, 11, 15, Curve.CurveDirection.Up);
		Assert.assertEquals(1.0, c.getValueAtDomainValue(11.0));
		double val = c.getValueAtDomainValue(11.5);
		Assert.assertTrue(val > 1.45 && val < 1.55);
		Assert.assertEquals(2.0, c.getValueAtDomainValue(12.0));
		Assert.assertEquals(3.0, c.getValueAtDomainValue(13.0));
		Assert.assertEquals(4.0, c.getValueAtDomainValue(14.0));
		Assert.assertEquals(5.0, c.getValueAtDomainValue(15.0));
	}

}
