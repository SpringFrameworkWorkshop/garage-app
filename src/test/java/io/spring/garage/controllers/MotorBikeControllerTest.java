package io.spring.garage.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.garage.controllers.dtos.MotorBikeDTO;
import io.spring.garage.entities.vehicle.MotorBike;
import io.spring.garage.entities.vehicle.VehicleType;
import io.spring.garage.manager.MotorBikeManager;
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
public class MotorBikeControllerTest {

    @InjectMocks
    private MotorBikeControllerImpl controller;

    @Mock
    private MotorBikeManager mockManager;

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
        ResultActions perform = mockMvc.perform(get("/motor-bike"));

        // Assert
        perform.andExpect(status().isOk());
    }

    @Test
    public void testGetAllWithContent() throws Exception {
        // Arrange
        MotorBike motorBike_1 = new MotorBike();
        motorBike_1.setId(1L);

        MotorBike motorBike_2 = new MotorBike();
        motorBike_2.setId(2L);

        List<MotorBike> motorBikes = new ArrayList<>();
        motorBikes.add(motorBike_1);
        motorBikes.add(motorBike_2);

        MotorBikeDTO motorBikeDTO_1 = new MotorBikeDTO();
        motorBikeDTO_1.setId(1L);

        MotorBikeDTO motorBikeDTO_2 = new MotorBikeDTO();
        motorBikeDTO_2.setId(2L);

        List<MotorBikeDTO> motorBikeDTOs = new ArrayList<>();
        motorBikeDTOs.add(motorBikeDTO_1);
        motorBikeDTOs.add(motorBikeDTO_2);

        Mockito.when(mockManager.findAll()).thenReturn(motorBikes);
        Mockito.when(mockModelMapper.map(motorBike_1, MotorBikeDTO.class)).thenReturn(motorBikeDTO_1);
        Mockito.when(mockModelMapper.map(motorBike_2, MotorBikeDTO.class)).thenReturn(motorBikeDTO_2);

        // Act
        ResultActions perform = mockMvc.perform(get("/motor-bike"));

        // Assert
        perform.andExpect(status().isOk());
        perform.andExpect(content().json(mapper.writeValueAsString(motorBikeDTOs)));
    }

    @Test
    public void testFindById() throws Exception {
        // Arrange
        MotorBike motorBike = new MotorBike();
        motorBike.setId(1L);

        MotorBikeDTO motorBikeDTO = new MotorBikeDTO();
        motorBikeDTO.setId(1L);

        Mockito.when(mockManager.get(1L)).thenReturn(motorBike);
        Mockito.when(mockModelMapper.map(motorBike, MotorBikeDTO.class)).thenReturn(motorBikeDTO);

        // Act
        ResultActions perform = mockMvc.perform(get("/motor-bike/1"));

        // Assert
        perform.andExpect(status().isOk());
        perform.andExpect(content().json(mapper.writeValueAsString(motorBikeDTO)));
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        MotorBike motorBike = new MotorBike();
        motorBike.setType(VehicleType.MOTORBIKE);
        motorBike.setNumWheels(2);
        motorBike.setModel("honda");

        MotorBikeDTO motorBikeDTO = new MotorBikeDTO();
        motorBikeDTO.setId(1L);
        motorBikeDTO.setType(VehicleType.MOTORBIKE);
        motorBikeDTO.setNumWheels(2);
        motorBikeDTO.setModel("honda");

        Mockito.when(mockModelMapper.map(Mockito.any(MotorBikeDTO.class), Mockito.eq(MotorBike.class))).thenReturn(motorBike);
        Mockito.when(mockManager.save(motorBike)).thenReturn(motorBike);
        Mockito.when(mockModelMapper.map(motorBike, MotorBikeDTO.class)).thenReturn(motorBikeDTO);

        // Act
        ResultActions perform = mockMvc.perform(post("/motor-bike")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(motorBikeDTO)));

        // Assert
        perform.andExpect(status().isAccepted());
        perform.andExpect(content().json(mapper.writeValueAsString(motorBikeDTO)));
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        MotorBike motorBike = new MotorBike();
        motorBike.setType(VehicleType.MOTORBIKE);
        motorBike.setNumWheels(2);
        motorBike.setModel("honda");

        MotorBikeDTO motorBikeDTO = new MotorBikeDTO();
        motorBikeDTO.setId(1L);
        motorBikeDTO.setType(VehicleType.MOTORBIKE);
        motorBikeDTO.setNumWheels(2);
        motorBikeDTO.setModel("honda");

        Mockito.when(mockModelMapper.map(Mockito.any(MotorBikeDTO.class), Mockito.eq(MotorBike.class))).thenReturn(motorBike);
        Mockito.when(mockManager.save(motorBike)).thenReturn(motorBike);
        Mockito.when(mockModelMapper.map(motorBike, MotorBikeDTO.class)).thenReturn(motorBikeDTO);

        // Act
        ResultActions perform = mockMvc.perform(put("/motor-bike/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(motorBikeDTO)));

        // Assert
        perform.andExpect(status().isAccepted());
        perform.andExpect(content().json(mapper.writeValueAsString(motorBikeDTO)));
    }

    @Test
    public void testDelete() throws Exception {
        // Act
        ResultActions perform = mockMvc.perform(delete("/motor-bike/1"));

        // Assert
        perform.andExpect(status().isOk());
        Mockito.verify(mockManager, Mockito.times(1)).delete(Mockito.any());

    }


}
