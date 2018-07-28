package io.spring.garage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import io.spring.garage.controllers.dtos.CarDTO;
import io.spring.garage.entities.vehicle.Car;
import io.spring.garage.entities.vehicle.VehicleType;
import io.spring.garage.repositories.CarRepository;
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
public class CarControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CarRepository repository;

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
        final Car saved1 = this.repository.save(this.buildCar("model-1", "color-1", "plate-1"));
        final Car saved2 = this.repository.save(this.buildCar("model-2", "color-2", "plate-2"));
        final Car saved3 = this.repository.save(this.buildCar("model-3", "color-3", "plate-3"));
        final Car saved4 = this.repository.save(this.buildCar("model-4", "color-4", "plate-4"));

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/car", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        JSONAssert.assertEquals("[{'id':" + saved1.getId() + ",'type':'CAR','numWheels':4,'model':'model-1','color':'color-1','plate':'plate-1'},"+
                        "{'id':" + saved2.getId() + ",'type':'CAR','numWheels':4,'model':'model-2','color':'color-2','plate':'plate-2'},"+
                        "{'id':" + saved3.getId() + ",'type':'CAR','numWheels':4,'model':'model-3','color':'color-3','plate':'plate-3'},"+
                        "{'id':" + saved4.getId() + ",'type':'CAR','numWheels':4,'model':'model-4','color':'color-4','plate':'plate-4'}]",
                exchange.getBody(),
                JSONCompareMode.LENIENT);
    }

    @Test
    public void testFindAll_filter() throws JSONException {
        // Arrange
        final Car saved1 = this.repository.save(this.buildCar("model-1", "color-1", "plate-1"));
        final Car saved2 = this.repository.save(this.buildCar("model-1", "color-2", "plate-2"));
        final Car saved3 = this.repository.save(this.buildCar("model-2", "color-2", "plate-3"));
        final Car saved4 = this.repository.save(this.buildCar("model-2", "color-2", "plate-4"));

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/car?model=model-2&color=color-2", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        JSONAssert.assertEquals("[{'id':" + saved3.getId() + ",'type':'CAR','numWheels':4,'model':'model-2','color':'color-2','plate':'plate-3'},"+
                        "{'id':" + saved4.getId() + ",'type':'CAR','numWheels':4,'model':'model-2','color':'color-2','plate':'plate-4'}]",
                exchange.getBody(),
                JSONCompareMode.LENIENT);
    }

    @Test
    public void testFindOne() throws JSONException {
        // Arrange
        final Car saved = this.repository.save(this.buildCar("model-1", "color-1", "plate-1"));

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/car/" + saved.getId(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        JSONAssert.assertEquals("{'id':" + +saved.getId() + ",'type':'CAR','numWheels':4,'model':'model-1','color':'color-1','plate':'plate-1'}",
                exchange.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testCreate() throws JSONException, JsonProcessingException {
        // Arrange
        final CarDTO carDTO = this.buildCarDTO("model-1", "color-1", "plate-1");

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/car", HttpMethod.POST, this.getPostRequest(carDTO), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.ACCEPTED, exchange.getStatusCode());
        JSONAssert.assertEquals("{'type':'CAR','numWheels':4,'model':'model-1','color':'color-1','plate':'plate-1'}",
                exchange.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testUpdate() throws JSONException, JsonProcessingException {
        // Arrange
        final Car saved = this.repository.save(this.buildCar("model-1", "color-1", "plate-1"));
        final CarDTO carDTO = this.buildCarDTO("model-changed", "color-changed", "plate-changed");
        carDTO.setId(saved.getId());

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/car/" + saved.getId(), HttpMethod.PUT, this.getPostRequest(carDTO), String.class);

        // Arrange
        Assert.assertEquals(HttpStatus.ACCEPTED, exchange.getStatusCode());
        JSONAssert.assertEquals("{'id':" + +saved.getId() + ",'type':'CAR','numWheels':4,'model':'model-changed','color':'color-changed','plate':'plate-changed'}",
                exchange.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testRemove() {
        // Arrange
        final Car saved = this.repository.save(this.buildCar("model-1", "color-1", "plate-1"));

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/car/" + saved.getId(), HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }

    private HttpEntity<String> getPostRequest(final Object object) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(mapper.writeValueAsString(object), headers);
    }

    private Car buildCar(final String model, final String color, final String plate) {
        final Car car = new Car();
        car.setType(VehicleType.CAR);
        car.setNumWheels(4);
        car.setModel(model);
        car.setColor(color);
        car.setPlate(plate);
        return car;
    }

    private CarDTO buildCarDTO(final String model, final String color, final String plate) {
        final CarDTO carDTO = new CarDTO();
        carDTO.setType(VehicleType.CAR);
        carDTO.setNumWheels(4);
        carDTO.setModel(model);
        carDTO.setColor(color);
        carDTO.setPlate(plate);
        return carDTO;
    }
}
