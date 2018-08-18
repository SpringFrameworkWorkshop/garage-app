package io.spring.garage.repositories;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import io.spring.garage.common.CarFilter;
import io.spring.garage.entities.vehicle.Car;
import io.spring.garage.entities.vehicle.VehicleType;
import io.spring.garage.specifications.CarSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CarRepositoryTest {

    @Autowired
    private CarRepository repository;

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testFindAll() {
        // Act
        List<Car> all = (List<Car>) this.repository.findAll();

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
        this.repository.save(car);

        // Assert
        List<Car> all = (List<Car>) this.repository.findAll();
        Assert.assertEquals(6, all.size());
    }

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testDelete() {
        // Arrange
        final Car car = this.repository.findById(1L).get();

        // Act
        this.repository.delete(car);

        // Assert
        List<Car> all = (List<Car>) this.repository.findAll();
        Assert.assertEquals(4, all.size());
    }

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testFindAll_specColor() {
        // Arrange
        final CarFilter carFilter = new CarFilter("black", null);
        final Specification<Car> spec = this.buildSpecifications(carFilter);

        // Act
        List<Car> all = this.repository.findAll(spec);

        // Assert
        Assert.assertEquals(3, all.size());
    }

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testFindAll_specModel() {
        // Arrange
        final CarFilter carFilter = new CarFilter(null, "seat");
        final Specification<Car> spec = this.buildSpecifications(carFilter);

        // Act
        List<Car> all = this.repository.findAll(spec);

        // Assert
        Assert.assertEquals(4, all.size());
    }

    @Test
    @DatabaseSetup("/db/car.xml")
    public void testFindAll_specAll() {
        // Arrange
        final CarFilter carFilter = new CarFilter("black", "seat");
        final Specification<Car> spec = this.buildSpecifications(carFilter);

        // Act
        List<Car> all = this.repository.findAll(spec);

        // Assert
        Assert.assertEquals(3, all.size());
    }

    private Specification<Car> buildSpecifications(final CarFilter carFilter) {
        return where((Specification<Car>) null)
                // filter by color
                .and((carFilter.getColor() == null)? null : CarSpecification.hasColor(carFilter.getColor()))
                // filter by model
                .and((carFilter.getModel() == null)? null : CarSpecification.hasModel(carFilter.getModel()));
    }

}
