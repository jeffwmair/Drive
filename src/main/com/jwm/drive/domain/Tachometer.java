package com.jwm.drive.domain;

interface Tachometer {
	void update(int rpm);
	int getRpm();
}
