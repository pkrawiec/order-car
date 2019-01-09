package com.car.order.ordercar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.car.order.ordercar.dto.CarCreationDto;
import com.car.order.ordercar.model.Car;
import com.car.order.ordercar.model.RoleName;
import com.car.order.ordercar.model.User;
import com.car.order.ordercar.repository.CarRepository;
import com.car.order.ordercar.repository.UserRepository;
import com.car.order.ordercar.utils.AuthenticationUtils;

public class CarServiceTest {
	
	static UserService userService;
    static CarRepository carRepository;
    static CarService carService;;
    
    static Car testCar;
    static CarCreationDto creationDto;
    
	@BeforeAll
    public static void setUp() {
		UserRepository userRepo = Mockito.mock(UserRepository.class);
		
        userService = new UserService(userRepo, null, null);
        carRepository = Mockito.mock(CarRepository.class);
        carService = new CarService(carRepository, userService);
    }

    // metoda odpalana przed kazdym testem testem
    @BeforeEach
    public void setUpEach() {
    	testCar = new Car();
    	testCar.setName("test_name");
    	testCar.setName("test_name");
    	testCar.setManufacturer("test");
    	testCar.setModel("test");
    	testCar.setProductionYear(1999);
    	
    	creationDto = new CarCreationDto();
    	creationDto.setName("test_name");
    	creationDto.setManufacturer("test");
    	creationDto.setModel("test");
    	creationDto.setProductionYear(1999);
    }

    @Test
	public void testEditCar() throws Exception {
    	Mockito.when(carRepository.getOne(Mockito.any(Integer.class))).thenReturn(testCar);
    	Mockito.when(carRepository.save(Mockito.any(Car.class))).thenReturn(testCar);
    	creationDto.setId(1);
		Car editCar = carService.editCar(creationDto);
		
		assertEquals("test_name", editCar.getName());
	}

	@Test
	public void testSaveCar() throws Exception {
		AuthenticationUtils.setAuthentication(RoleName.ROLE_USER.name(), "test_user");
		UserRepository userRepo = Mockito.mock(UserRepository.class);
    	Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(new User());
    	
    	Mockito.when(carRepository.save(Mockito.any(Car.class))).thenReturn(testCar);
		Car editCar = carService.saveCar(creationDto);
		
		assertEquals("test_name", editCar.getName());
	}
}