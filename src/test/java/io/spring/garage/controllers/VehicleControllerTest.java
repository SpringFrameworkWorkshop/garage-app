package io.spring.garage.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.garage.controllers.dtos.VehicleDTO;
import io.spring.garage.entities.vehicle.Vehicle;
import io.spring.garage.entities.vehicle.VehicleType;
import io.spring.garage.manager.VehicleManager;
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
public class VehicleControllerTest {

    @InjectMocks
    private VehicleControllerImpl controller;

    @Mock
    private VehicleManager mockManager;

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
        ResultActions perform = mockMvc.perform(get("/vehicle"));

        // Assert
        perform.andExpect(status().isOk());
    }

    @Test
    public void testGetAllWithContent() throws Exception {
        // Arrange
        Vehicle vehicle_1 = new Vehicle();
        vehicle_1.setId(1L);

        Vehicle vehicle_2 = new Vehicle();
        vehicle_2.setId(2L);

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle_1);
        vehicles.add(vehicle_2);

        VehicleDTO vehicleDTO_1 = new VehicleDTO();
        vehicleDTO_1.setId(1L);

        VehicleDTO vehicleDTO_2 = new VehicleDTO();
        vehicleDTO_2.setId(2L);

        List<VehicleDTO> vehicleDTOs = new ArrayList<>();
        vehicleDTOs.add(vehicleDTO_1);
        vehicleDTOs.add(vehicleDTO_2);

        Mockito.when(mockManager.findAll()).thenReturn(vehicles);
        Mockito.when(mockModelMapper.map(vehicle_1, VehicleDTO.class)).thenReturn(vehicleDTO_1);
        Mockito.when(mockModelMapper.map(vehicle_2, VehicleDTO.class)).thenReturn(vehicleDTO_2);

        // Act
        ResultActions perform = mockMvc.perform(get("/vehicle"));

        // Assert
        perform.andExpect(status().isOk());
        perform.andExpect(content().json(mapper.writeValueAsString(vehicleDTOs)));
    }

    @Test
    public void testFindById() throws Exception {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(1L);

        Mockito.when(mockManager.get(1L)).thenReturn(vehicle);
        Mockito.when(mockModelMapper.map(vehicle, VehicleDTO.class)).thenReturn(vehicleDTO);

        // Act
        ResultActions perform = mockMvc.perform(get("/vehicle/1"));

        // Assert
        perform.andExpect(status().isOk());
        perform.andExpect(content().json(mapper.writeValueAsString(vehicleDTO)));
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setType(VehicleType.CAR);
        vehicle.setNumWheels(4);
        vehicle.setModel("seat");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(1L);
        vehicleDTO.setType(VehicleType.CAR);
        vehicleDTO.setNumWheels(4);
        vehicleDTO.setModel("seat");

        Mockito.when(mockModelMapper.map(Mockito.any(VehicleDTO.class), Mockito.eq(Vehicle.class))).thenReturn(vehicle);
        Mockito.when(mockManager.save(vehicle)).thenReturn(vehicle);
        Mockito.when(mockModelMapper.map(vehicle, VehicleDTO.class)).thenReturn(vehicleDTO);

        // Act
        ResultActions perform = mockMvc.perform(post("/vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vehicleDTO)));

        // Assert
        perform.andExpect(status().isAccepted());
        perform.andExpect(content().json(mapper.writeValueAsString(vehicleDTO)));
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setType(VehicleType.CAR);
        vehicle.setNumWheels(4);
        vehicle.setModel("seat");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(1L);
        vehicleDTO.setType(VehicleType.CAR);
        vehicleDTO.setNumWheels(4);
        vehicleDTO.setModel("seat");

        Mockito.when(mockModelMapper.map(Mockito.any(VehicleDTO.class), Mockito.eq(Vehicle.class))).thenReturn(vehicle);
        Mockito.when(mockManager.save(vehicle)).thenReturn(vehicle);
        Mockito.when(mockModelMapper.map(vehicle, VehicleDTO.class)).thenReturn(vehicleDTO);

        // Act
        ResultActions perform = mockMvc.perform(put("/vehicle/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vehicleDTO)));

        // Assert
        perform.andExpect(status().isAccepted());
        perform.andExpect(content().json(mapper.writeValueAsString(vehicleDTO)));
    }

    @Test
    public void testDelete() throws Exception {
        // Act
        ResultActions perform = mockMvc.perform(delete("/vehicle/1"));

        // Assert
        perform.andExpect(status().isOk());
        Mockito.verify(mockManager, Mockito.times(1)).delete(Mockito.any());

    }


}
