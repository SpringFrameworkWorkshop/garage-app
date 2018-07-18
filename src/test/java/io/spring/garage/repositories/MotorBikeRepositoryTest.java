package io.spring.garage.repositories;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import io.spring.garage.entities.vehicle.MotorBike;
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
public class MotorBikeRepositoryTest {

    @Autowired
    private MotorBikeRepository repository;

    @Test
    @DatabaseSetup("/db/motor_bike.xml")
    public void testFindAll() {
        // Act
        List<MotorBike> all = (List<MotorBike>) this.repository.findAll();

        // Assert
        Assert.assertEquals(2, all.size());
    }

    @Test
    @DatabaseSetup("/db/motor_bike.xml")
    public void testSave() {
        // Arrange
        final MotorBike motorBike = new MotorBike();
        motorBike.setColor("A");
        motorBike.setNumWheels(2);
        motorBike.setModel("C");
        motorBike.setPlate("D");
        motorBike.setType(VehicleType.MOTORBIKE);

        // Act
        this.repository.save(motorBike);

        // Assert
        List<MotorBike> all = (List<MotorBike>) this.repository.findAll();
        Assert.assertEquals(3, all.size());
    }

    @Test
    @DatabaseSetup("/db/motor_bike.xml")
    public void testDelete() {
        // Arrange
        final MotorBike motorBike = this.repository.findById(1L).get();

        // Act
        this.repository.delete(motorBike);

        // Assert
        List<MotorBike> all = (List<MotorBike>) this.repository.findAll();
        Assert.assertEquals(1, all.size());
    }

}
