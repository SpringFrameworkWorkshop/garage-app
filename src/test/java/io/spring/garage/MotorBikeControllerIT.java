package io.spring.garage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import io.spring.garage.controllers.dtos.MotorBikeDTO;
import io.spring.garage.entities.vehicle.MotorBike;
import io.spring.garage.entities.vehicle.VehicleType;
import io.spring.garage.repositories.MotorBikeRepository;
import lombok.extern.java.Log;
import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MotorBikeControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MotorBikeRepository repository;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        log.info("Removing all data at init...");
        this.clearData();
    }

    @After
    public void finish() {
        log.info("Removing all data at finish...");
        this.clearData();
    }

    private void clearData() {
        this.repository.deleteAll();
    }

    @Test
    public void testFindAll() throws JSONException {
        // Arrange
        final MotorBike saved1 = this.repository.save(this.buildMotorBike("model-1", "color-1", "plate-1"));
        final MotorBike saved2 = this.repository.save(this.buildMotorBike("model-2", "color-2", "plate-2"));
        final MotorBike saved3 = this.repository.save(this.buildMotorBike("model-3", "color-3", "plate-3"));
        final MotorBike saved4 = this.repository.save(this.buildMotorBike("model-4", "color-4", "plate-4"));

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/motor-bike", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        JSONAssert.assertEquals("[{'id':" + saved1.getId() + ",'type':'MOTORBIKE','numWheels':2,'model':'model-1','color':'color-1','plate':'plate-1'},"+
                        "{'id':" + saved2.getId() + ",'type':'MOTORBIKE','numWheels':2,'model':'model-2','color':'color-2','plate':'plate-2'},"+
                        "{'id':" + saved3.getId() + ",'type':'MOTORBIKE','numWheels':2,'model':'model-3','color':'color-3','plate':'plate-3'},"+
                        "{'id':" + saved4.getId() + ",'type':'MOTORBIKE','numWheels':2,'model':'model-4','color':'color-4','plate':'plate-4'}]",
                exchange.getBody(),
                JSONCompareMode.LENIENT);
    }

    @Test
    public void testFindOne() throws JSONException {
        // Arrange
        final MotorBike saved = this.repository.save(this.buildMotorBike("model-1", "color-1", "plate-1"));

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/motor-bike/" + saved.getId(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        JSONAssert.assertEquals("{'id':" + +saved.getId() + ",'type':'MOTORBIKE','numWheels':2,'model':'model-1','color':'color-1','plate':'plate-1'}",
                exchange.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testCreate() throws JSONException, JsonProcessingException {
        // Arrange
        final MotorBikeDTO motorBikeDTO = this.buildMotorBikeDTO("model-1", "color-1", "plate-1");

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/motor-bike", HttpMethod.POST, this.getPostRequest(motorBikeDTO), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.ACCEPTED, exchange.getStatusCode());
        JSONAssert.assertEquals("{'type':'MOTORBIKE','numWheels':2,'model':'model-1','color':'color-1','plate':'plate-1'}",
                exchange.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testUpdate() throws JSONException, JsonProcessingException {
        // Arrange
        final MotorBike saved = this.repository.save(this.buildMotorBike("model-1", "color-1", "plate-1"));
        final MotorBikeDTO motorBikeDTO = this.buildMotorBikeDTO("model-changed", "color-changed", "plate-changed");
        motorBikeDTO.setId(saved.getId());

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/motor-bike/" + saved.getId(), HttpMethod.PUT, this.getPostRequest(motorBikeDTO), String.class);

        // Arrange
        Assert.assertEquals(HttpStatus.ACCEPTED, exchange.getStatusCode());
        JSONAssert.assertEquals("{'id':" + +saved.getId() + ",'type':'MOTORBIKE','numWheels':2,'model':'model-changed','color':'color-changed','plate':'plate-changed'}",
                exchange.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testRemove() {
        // Arrange
        final MotorBike saved = this.repository.save(this.buildMotorBike("model-1", "color-1", "plate-1"));

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/motor-bike/" + saved.getId(), HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }

    private HttpEntity<String> getPostRequest(final Object object) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(mapper.writeValueAsString(object), headers);
    }

    private MotorBike buildMotorBike(final String model, final String color, final String plate) {
        final MotorBike motorBike = new MotorBike();
        motorBike.setType(VehicleType.MOTORBIKE);
        motorBike.setNumWheels(2);
        motorBike.setModel(model);
        motorBike.setColor(color);
        motorBike.setPlate(plate);
        return motorBike;
    }

    private MotorBikeDTO buildMotorBikeDTO(final String model, final String color, final String plate) {
        final MotorBikeDTO motorBikeDTO = new MotorBikeDTO();
        motorBikeDTO.setType(VehicleType.MOTORBIKE);
        motorBikeDTO.setNumWheels(2);
        motorBikeDTO.setModel(model);
        motorBikeDTO.setColor(color);
        motorBikeDTO.setPlate(plate);
        return motorBikeDTO;
    }
}
