package com.jwm.drive.domain.modelling;

public class Curve {
	
	public enum CurveDirection { Up, Dn }

	private double[] val;
	private double domainMin, domainMax;

	private final double MIN_ACCURACY = 0.000000001;
	private CurveDirection direction;

	public Curve(double[] values, double domainMin, double domainMax, CurveDirection dir) {
		if (values.length < 3) {
			throw new IllegalArgumentException("Must provide at least 3 elements to a curve");
		}
		if (domainMax <= domainMin) {
			throw new IllegalArgumentException("DomainMax must be greater than domainMin");
		}
		this.direction = dir;
		this.val = values;
		this.domainMin = domainMin;
		this.domainMax = domainMax;
	}
	
	public CurveDirection getDirection() { return this.direction; }

	/**
	 * get the max value in the curve
	 * 
	 * @return
	 */
	public double getLast() {
		return val[val.length - 1];
	}
	public double getMin() {
		double min = Double.MAX_VALUE;
		for (double aVal : val) {
			if (aVal < min) {
				min = aVal;
			}
		}
		return min;
	}
	public double getMax() {
		double max = Double.MIN_VALUE;
		for (double aVal : val) {
			if (aVal > max) {
				max = aVal;
			}
		}
		return max;
	}

	/**
	 * 
	 * @return
	 */
	public double getFirst() {
		return val[0];
	}

	/**
	 * Get the value at this approximate percentage of the way through the curve
	 * 
	 * @param pct
	 * @return
	 */
	public double getValueAtPctPosition(double pct) {
		int count = val.length - 1;
		double domainPos = count * pct;
		int pos = (int) domainPos;
		if (pos == val.length - 1) {
			return val[pos];
		}

		double remainderPct = domainPos - pos;
		double extraNonIntegerAmount = remainderPct * (val[pos + 1] - val[pos]);
		return val[pos] + extraNonIntegerAmount;
	}

	public double getValueAtDomainValue(double domainVal) {
		double range = domainMax - domainMin;
		double pct = (domainVal - domainMin) / range;
		return getValueAtPctPosition(pct);
	}

	public int getNearestIndex(double currentValue) {
		int nearestIndex = -1;
		double prevClosestDiff = Double.MAX_VALUE;
		for (int i = 0; i < val.length; i++) {
			double diff = Math.abs(currentValue - val[i]);
			if (diff <= prevClosestDiff) {
				nearestIndex = i;
				prevClosestDiff = diff;
			}
		}

		return nearestIndex;
	}
	public double getRelativeValue(double currentValue, double domainDelta) {
		if (currentValue > getMax()) {
			return getMax();
		}
		if (currentValue < getMin()) {
			return getMin();
		}

		if (domainDelta == 0) {
			return currentValue;
		}

		int nearestIndex = getNearestIndex(currentValue);
		if (nearestIndex == val.length - 1) {
			return getLast();
		}
		double indexValue = val[nearestIndex];
		double currentValueDiff = currentValue - indexValue;

		double domainInterval = getDomainInterval();
		// how far ahead do we need to index to get close to the desired value
		int deltaIndexChange = (int) (domainDelta / domainInterval);
		// plus some remainder
		double domainDeltaRemainder = domainDelta - (deltaIndexChange * domainInterval);

		// if we're going to be at the end, just return the last value
		if (nearestIndex + deltaIndexChange >= val.length - 1) {
			return getLast();
		}
		if (nearestIndex + deltaIndexChange <= 0 && domainDeltaRemainder == 0) {
			return getFirst();
		}

		double newValueOnTheCurve = val[nearestIndex + deltaIndexChange];
		double nextValueOnTheCurve = val[nearestIndex + deltaIndexChange + 1];
		double newValueRange = nextValueOnTheCurve - newValueOnTheCurve;
		double extraPct = domainDeltaRemainder / domainInterval;
		double extraValue = extraPct * newValueRange;

		return newValueOnTheCurve + currentValueDiff + extraValue;

	}
	private double getDomainInterval() {
		return (domainMax - domainMin) / (val.length - 1);
	}
}
