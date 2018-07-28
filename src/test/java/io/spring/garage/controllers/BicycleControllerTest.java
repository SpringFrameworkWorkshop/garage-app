package io.spring.garage.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.garage.controllers.dtos.BicycleDTO;
import io.spring.garage.entities.vehicle.Bicycle;
import io.spring.garage.entities.vehicle.VehicleType;
import io.spring.garage.manager.BicycleManager;
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
public class BicycleControllerTest {

    @InjectMocks
    private BicycleControllerImpl controller;

    @Mock
    private BicycleManager mockManager;

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
        ResultActions perform = mockMvc.perform(get("/bicycle"));

        // Assert
        perform.andExpect(status().isOk());
    }

    @Test
    public void testGetAllWithContent() throws Exception {
        // Arrange
        Bicycle bicycle_1 = new Bicycle();
        bicycle_1.setId(1L);

        Bicycle bicycle_2 = new Bicycle();
        bicycle_2.setId(2L);

        List<Bicycle> bicycles = new ArrayList<>();
        bicycles.add(bicycle_1);
        bicycles.add(bicycle_2);

        BicycleDTO bicycleDTO_1 = new BicycleDTO();
        bicycleDTO_1.setId(1L);

        BicycleDTO bicycleDTO_2 = new BicycleDTO();
        bicycleDTO_2.setId(2L);

        List<BicycleDTO> bicycleDTOs = new ArrayList<>();
        bicycleDTOs.add(bicycleDTO_1);
        bicycleDTOs.add(bicycleDTO_2);

        Mockito.when(mockManager.findAll()).thenReturn(bicycles);
        Mockito.when(mockModelMapper.map(bicycle_1, BicycleDTO.class)).thenReturn(bicycleDTO_1);
        Mockito.when(mockModelMapper.map(bicycle_2, BicycleDTO.class)).thenReturn(bicycleDTO_2);

        // Act
        ResultActions perform = mockMvc.perform(get("/bicycle"));

        // Assert
        perform.andExpect(status().isOk());
        perform.andExpect(content().json(mapper.writeValueAsString(bicycleDTOs)));
    }

    @Test
    public void testFindById() throws Exception {
        // Arrange
        Bicycle bicycle = new Bicycle();
        bicycle.setId(1L);

        BicycleDTO bicycleDTO = new BicycleDTO();
        bicycleDTO.setId(1L);

        Mockito.when(mockManager.get(1L)).thenReturn(bicycle);
        Mockito.when(mockModelMapper.map(bicycle, BicycleDTO.class)).thenReturn(bicycleDTO);

        // Act
        ResultActions perform = mockMvc.perform(get("/bicycle/1"));

        // Assert
        perform.andExpect(status().isOk());
        perform.andExpect(content().json(mapper.writeValueAsString(bicycleDTO)));
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        Bicycle bicycle = new Bicycle();
        bicycle.setType(VehicleType.BICYCLE);
        bicycle.setNumWheels(2);
        bicycle.setModel("orbea");

        BicycleDTO bicycleDTO = new BicycleDTO();
        bicycleDTO.setId(1L);
        bicycleDTO.setType(VehicleType.BICYCLE);
        bicycleDTO.setNumWheels(2);
        bicycleDTO.setModel("orbea");

        Mockito.when(mockModelMapper.map(Mockito.any(BicycleDTO.class), Mockito.eq(Bicycle.class))).thenReturn(bicycle);
        Mockito.when(mockManager.save(bicycle)).thenReturn(bicycle);
        Mockito.when(mockModelMapper.map(bicycle, BicycleDTO.class)).thenReturn(bicycleDTO);

        // Act
        ResultActions perform = mockMvc.perform(post("/bicycle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bicycleDTO)));

        // Assert
        perform.andExpect(status().isAccepted());
        perform.andExpect(content().json(mapper.writeValueAsString(bicycleDTO)));
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        Bicycle bicycle = new Bicycle();
        bicycle.setType(VehicleType.BICYCLE);
        bicycle.setNumWheels(2);
        bicycle.setModel("orbea");

        BicycleDTO bicycleDTO = new BicycleDTO();
        bicycleDTO.setId(1L);
        bicycleDTO.setType(VehicleType.BICYCLE);
        bicycleDTO.setNumWheels(2);
        bicycleDTO.setModel("orbea");

        Mockito.when(mockModelMapper.map(Mockito.any(BicycleDTO.class), Mockito.eq(Bicycle.class))).thenReturn(bicycle);
        Mockito.when(mockManager.save(bicycle)).thenReturn(bicycle);
        Mockito.when(mockModelMapper.map(bicycle, BicycleDTO.class)).thenReturn(bicycleDTO);

        // Act
        ResultActions perform = mockMvc.perform(put("/bicycle/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bicycleDTO)));

        // Assert
        perform.andExpect(status().isAccepted());
        perform.andExpect(content().json(mapper.writeValueAsString(bicycleDTO)));
    }

    @Test
    public void testDelete() throws Exception {
        // Act
        ResultActions perform = mockMvc.perform(delete("/bicycle/1"));

        // Assert
        perform.andExpect(status().isOk());
        Mockito.verify(mockManager, Mockito.times(1)).delete(Mockito.any());

    }


}
