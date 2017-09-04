package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.Car;
import com.cct.model.User;
import com.cct.model.Version;
import com.cct.model.dto.CarDTO;
import com.cct.repository.api.CarRepository;
import com.cct.service.api.CarService;
import com.cct.util.ModelMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.cct.exception.ErrorInfo.CAR_NOT_FOUND;
import static org.junit.Assert.assertEquals;
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

    @Test
    public void getCar_CarExists_CarDTOReturned() throws Exception {
        //given
        Car car = new Car();
        car.setId(1L);

        User user = new User();
        user.setId(1L);
        car.setUser(user);

        Version version = new Version();
        version.setId("id");
        car.setVersion(version);

        CarDTO carDTO = new CarDTO();
        carDTO.setId(1L);
        carDTO.setUserId(1L);
        carDTO.setVersionId("id");

        //when
        when(carRepository.findOneById(1L)).thenReturn(Optional.of(car));
        when(modelMapper.convertToDTO(car)).thenReturn(carDTO);

        //then
        CarDTO result = carService.getCar(1L);
        assertEquals(new Long(1), result.getId());
        assertEquals(new Long(1), result.getUserId());
        assertEquals("id", result.getVersionId());
    }

    @Test(expected = BadRequestException.class)
    public void getCar_CarDoesNotExist_BadRequestExceptionThrown() throws Exception {
        //when
        when(carRepository.findOneById(1L)).thenThrow(new BadRequestException(CAR_NOT_FOUND));

        //then
        carService.getCar(1L);
    }

}