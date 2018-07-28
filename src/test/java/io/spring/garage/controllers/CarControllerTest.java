package io.spring.garage.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.garage.common.CarFilter;
import io.spring.garage.controllers.dtos.CarDTO;
import io.spring.garage.entities.vehicle.Car;
import io.spring.garage.entities.vehicle.VehicleType;
import io.spring.garage.manager.CarManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class CarControllerTest {

    @InjectMocks
    private CarControllerImpl controller;

    @Mock
    private CarManager mockManager;

    @Mock
    private ModelMapper mockModelMapper;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.mapper = new ObjectMapper();
    }

    @Test
    public void testGetAllNoContent() throws Exception {
        // Arrange
        Mockito.when(mockManager.findAll()).thenReturn(null);

        // Act
        ResultActions perform = mockMvc.perform(get("/car"));

        // Assert
        perform.andExpect(status().isOk());
    }

    @Test
    public void testGetAllWithContent() throws Exception {
        // Arrange
        Car car_1 = new Car();
        car_1.setId(1L);

        Car car_2 = new Car();
        car_2.setId(2L);

        List<Car> cars = new ArrayList<>();
        cars.add(car_1);
        cars.add(car_2);

        CarDTO carDTO_1 = new CarDTO();
        carDTO_1.setId(1L);

        CarDTO carDTO_2 = new CarDTO();
        carDTO_2.setId(2L);

        List<CarDTO> carDTOs = new ArrayList<>();
        carDTOs.add(carDTO_1);
        carDTOs.add(carDTO_2);

        Mockito.when(mockManager.findAll(Mockito.any(CarFilter.class))).thenReturn(cars);
        Mockito.when(mockModelMapper.map(car_1, CarDTO.class)).thenReturn(carDTO_1);
        Mockito.when(mockModelMapper.map(car_2, CarDTO.class)).thenReturn(carDTO_2);

        // Act
        ResultActions perform = mockMvc.perform(get("/car"));

        // Assert
        perform.andExpect(status().isOk());
        perform.andExpect(content().json(mapper.writeValueAsString(carDTOs)));
    }

    @Test
    public void testFindById() throws Exception {
        // Arrange
        Car car = new Car();
        car.setId(1L);

        CarDTO carDTO = new CarDTO();
        carDTO.setId(1L);

        Mockito.when(mockManager.get(1L)).thenReturn(car);
        Mockito.when(mockModelMapper.map(car, CarDTO.class)).thenReturn(carDTO);

        // Act
        ResultActions perform = mockMvc.perform(get("/car/1"));

        // Assert
        perform.andExpect(status().isOk());
        perform.andExpect(content().json(mapper.writeValueAsString(carDTO)));
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        Car car = new Car();
        car.setType(VehicleType.CAR);
        car.setNumWheels(4);
        car.setModel("seat");

        CarDTO carDTO = new CarDTO();
        carDTO.setId(1L);
        carDTO.setType(VehicleType.CAR);
        carDTO.setNumWheels(4);
        carDTO.setModel("seat");

        Mockito.when(mockModelMapper.map(Mockito.any(CarDTO.class), Mockito.eq(Car.class))).thenReturn(car);
        Mockito.when(mockManager.save(car)).thenReturn(car);
        Mockito.when(mockModelMapper.map(car, CarDTO.class)).thenReturn(carDTO);

        // Act
        ResultActions perform = mockMvc.perform(post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(carDTO)));

        // Assert
        perform.andExpect(status().isAccepted());
        perform.andExpect(content().json(mapper.writeValueAsString(carDTO)));
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        Car car = new Car();
        car.setType(VehicleType.CAR);
        car.setNumWheels(4);
        car.setModel("seat");

        CarDTO carDTO = new CarDTO();
        carDTO.setId(1L);
        carDTO.setType(VehicleType.CAR);
        carDTO.setNumWheels(4);
        carDTO.setModel("seat");

        Mockito.when(mockModelMapper.map(Mockito.any(CarDTO.class), Mockito.eq(Car.class))).thenReturn(car);
        Mockito.when(mockManager.save(car)).thenReturn(car);
        Mockito.when(mockModelMapper.map(car, CarDTO.class)).thenReturn(carDTO);

        // Act
        ResultActions perform = mockMvc.perform(put("/car/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(carDTO)));

        // Assert
        perform.andExpect(status().isAccepted());
        perform.andExpect(content().json(mapper.writeValueAsString(carDTO)));
    }

    @Test
    public void testDelete() throws Exception {
        // Act
        ResultActions perform = mockMvc.perform(delete("/car/1"));

        // Assert
        perform.andExpect(status().isOk());
        Mockito.verify(mockManager, Mockito.times(1)).delete(Mockito.any());

    }


}
