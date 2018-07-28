package io.spring.garage.manager;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import io.spring.garage.common.CarFilter;
import io.spring.garage.entities.vehicle.Car;
import io.spring.garage.entities.vehicle.VehicleType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CarManagerTest {

    @Autowired
    private CarManager manager;

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testFindAll() {
        // Act
        List<Car> all = this.manager.findAll();

        // Assert
        Assert.assertEquals(5, all.size());
    }

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testSave() {
        // Arrange
        final Car car = new Car();
        car.setColor("A");
        car.setNumWheels(4);
        car.setModel("C");
        car.setPlate("D");
        car.setType(VehicleType.CAR);

        // Act
        this.manager.save(car);

        // Assert
        List<Car> all = this.manager.findAll();
        Assert.assertEquals(6, all.size());
    }

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testDelete() {
        // Arrange
        final Car car = this.manager.get(1L);

        // Act
        this.manager.delete(car);

        // Assert
        List<Car> all = this.manager.findAll();
        Assert.assertEquals(4, all.size());
    }

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testFindAll_specColor() {
        // Arrange
        final CarFilter carFilter = new CarFilter("black", null);

        // Act
        List<Car> all = this.manager.findAll(carFilter);

        // Assert
        Assert.assertEquals(3, all.size());
    }

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testFindAll_specModel() {
        // Arrange
        final CarFilter carFilter = new CarFilter(null, "seat");

        // Act
        List<Car> all = this.manager.findAll(carFilter);

        // Assert
        Assert.assertEquals(4, all.size());
    }

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testFindAll_specAll() {
        // Arrange
        final CarFilter carFilter = new CarFilter("black", "seat");

        // Act
        List<Car> all = this.manager.findAll(carFilter);

        // Assert
        Assert.assertEquals(3, all.size());
    }

}
