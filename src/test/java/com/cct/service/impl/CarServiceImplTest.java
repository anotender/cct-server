package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.Car;
import com.cct.model.User;
import com.cct.model.Version;
import com.cct.model.dto.CarDTO;
import com.cct.repository.api.CarRepository;
import com.cct.service.api.CarService;
import com.cct.util.ModelMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.cct.exception.ErrorInfo.CAR_NOT_FOUND;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceImplTest {

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private CarService carService;

    private Car car1, car2;
    private Version version1, version2;
    private CarDTO carDTO1, carDTO2;
    private User user;

    @Before
    public void init() {
        car1 = new Car();
        car2 = new Car();

        car1.setId(1L);
        car2.setId(2L);

        user = new User();
        user.setId(1L);
        user.setEmail("email@email.com");
        user.setCars(Sets.newSet(car1, car2));

        car1.setUser(user);
        car2.setUser(user);

        version1 = new Version();
        version2 = new Version();

        version1.setId("1");
        version2.setId("2");

        car1.setVersion(version1);
        car2.setVersion(version2);

        carDTO1 = new CarDTO();
        carDTO2 = new CarDTO();

        carDTO1.setId(1L);
        carDTO2.setId(2L);
        carDTO1.setUserId(1L);
        carDTO2.setUserId(2L);
        carDTO1.setVersionId("1");
        carDTO2.setVersionId("2");
    }

    @Test
    public void getCar_CarExists_CarDTOReturned() throws Exception {
        //when
        when(carRepository.findOneById(1L)).thenReturn(Optional.of(car1));
        when(modelMapper.convertToDTO(car1)).thenReturn(carDTO1);

        //then
        CarDTO result = carService.getCar(1L);
        assertEquals(new Long(1), result.getId());
        assertEquals(new Long(1), result.getUserId());
        assertEquals("1", result.getVersionId());
    }

    @Test(expected = BadRequestException.class)
    public void getCar_CarDoesNotExist_BadRequestExceptionThrown() throws Exception {
        //when
        when(carRepository.findOneById(1L)).thenThrow(new BadRequestException(CAR_NOT_FOUND));

        //then
        carService.getCar(1L);
    }

    @Test
    public void getCarsForUser_UserHasTwoCars_TwoCarDTOsReturned() {
        //when
        HashSet<Car> cars = new HashSet<>();
        cars.add(car1);
        cars.add(car2);
        when(carRepository.findByUserId(1L)).thenReturn(cars);
        when(modelMapper.convertToDTO(car1)).thenReturn(carDTO1);
        when(modelMapper.convertToDTO(car2)).thenReturn(carDTO2);

        //then
        Collection<CarDTO> carDTOs = carService.getCarsForUser(1L);
        assertThat(carDTOs, instanceOf(Set.class));
        assertEquals(2, carDTOs.size());
        assertTrue(carDTOs.contains(carDTO1));
        assertTrue(carDTOs.contains(carDTO2));
    }

}