package com.car.order.ordercar.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(

{ CarServiceTest.class, UserServiceTest.class, CarLoanServiceTest.class, UserAuthServiceImplTest.class })
public class TestSuite { // nothing
}
