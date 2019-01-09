package com.car.order.ordercar;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class OrderCarApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void shouldReturnSum() {
		int expectedValue = 10;
		int resultValue = sum(5, 5);

		Assertions.assertEquals(expectedValue, resultValue);
	}

	int sum(int a, int b) {
		return a + b;
	}
}

