package com.jwm.drive.domain.car;

interface Tachometer {
	void update(int rpm);
	int getRpm();
}
