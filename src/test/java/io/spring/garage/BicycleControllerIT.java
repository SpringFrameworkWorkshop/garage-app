package io.spring.garage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import io.spring.garage.controllers.dtos.BicycleDTO;
import io.spring.garage.entities.vehicle.Bicycle;
import io.spring.garage.entities.vehicle.VehicleType;
import io.spring.garage.repositories.BicycleRepository;
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
public class BicycleControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BicycleRepository repository;

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
        final Bicycle saved1 = this.repository.save(this.buildBicycle("model-1", "color-1"));
        final Bicycle saved2 = this.repository.save(this.buildBicycle("model-2", "color-2"));
        final Bicycle saved3 = this.repository.save(this.buildBicycle("model-3", "color-3"));
        final Bicycle saved4 = this.repository.save(this.buildBicycle("model-4", "color-4"));

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/bicycle", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        JSONAssert.assertEquals("[{'id':" + saved1.getId() + ",'type':'BICYCLE','numWheels':2,'model':'model-1','color':'color-1'},"+
                        "{'id':" + saved2.getId() + ",'type':'BICYCLE','numWheels':2,'model':'model-2','color':'color-2'},"+
                        "{'id':" + saved3.getId() + ",'type':'BICYCLE','numWheels':2,'model':'model-3','color':'color-3'},"+
                        "{'id':" + saved4.getId() + ",'type':'BICYCLE','numWheels':2,'model':'model-4','color':'color-4'}]",
                exchange.getBody(),
                JSONCompareMode.LENIENT);
    }

    @Test
    public void testFindOne() throws JSONException {
        // Arrange
        final Bicycle saved = this.repository.save(this.buildBicycle("model-1", "color-1"));

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/bicycle/" + saved.getId(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        JSONAssert.assertEquals("{'id':" + +saved.getId() + ",'type':'BICYCLE','numWheels':2,'model':'model-1','color':'color-1'}",
                exchange.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testCreate() throws JSONException, JsonProcessingException {
        // Arrange
        final BicycleDTO bicycleDTO = this.buildBicycleDTO("model-1", "color-1");

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/bicycle", HttpMethod.POST, this.getPostRequest(bicycleDTO), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.ACCEPTED, exchange.getStatusCode());
        JSONAssert.assertEquals("{'type':'BICYCLE','numWheels':2,'model':'model-1','color':'color-1'}",
                exchange.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testUpdate() throws JSONException, JsonProcessingException {
        // Arrange
        final Bicycle saved = this.repository.save(this.buildBicycle("model-1", "color-1"));
        final BicycleDTO bicycleDTO = this.buildBicycleDTO("model-changed", "color-changed");
        bicycleDTO.setId(saved.getId());

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/bicycle/" + saved.getId(), HttpMethod.PUT, this.getPostRequest(bicycleDTO), String.class);

        // Arrange
        Assert.assertEquals(HttpStatus.ACCEPTED, exchange.getStatusCode());
        JSONAssert.assertEquals("{'id':" + +saved.getId() + ",'type':'BICYCLE','numWheels':2,'model':'model-changed','color':'color-changed'}",
                exchange.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testRemove() {
        // Arrange
        final Bicycle saved = this.repository.save(this.buildBicycle("model-1", "color-1"));

        // Act
        ResponseEntity<String> exchange = testRestTemplate.exchange("/bicycle/" + saved.getId(), HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), String.class);

        // Assert
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }

    private HttpEntity<String> getPostRequest(final Object object) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(mapper.writeValueAsString(object), headers);
    }

    private Bicycle buildBicycle(final String model, final String color) {
        final Bicycle bicycle = new Bicycle();
        bicycle.setType(VehicleType.BICYCLE);
        bicycle.setNumWheels(2);
        bicycle.setModel(model);
        bicycle.setColor(color);
        return bicycle;
    }

    private BicycleDTO buildBicycleDTO(final String model, final String color) {
        final BicycleDTO bicycleDTO = new BicycleDTO();
        bicycleDTO.setType(VehicleType.BICYCLE);
        bicycleDTO.setNumWheels(2);
        bicycleDTO.setModel(model);
        bicycleDTO.setColor(color);
        return bicycleDTO;
    }
}
