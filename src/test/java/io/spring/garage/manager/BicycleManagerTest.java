package io.spring.garage.manager;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import io.spring.garage.entities.vehicle.Bicycle;
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
public class BicycleManagerTest {

    @Autowired
    private BicycleManager manager;

    @Test
    @DatabaseSetup("/db/bicycle.xml")
    public void testFindAll() {
        // Act
        List<Bicycle> all = this.manager.findAll();

        // Assert
        Assert.assertEquals(2, all.size());
    }

    @Test
    @DatabaseSetup("/db/bicycle.xml")
    public void testSave() {
        // Arrange
        final Bicycle bicycle = new Bicycle();
        bicycle.setColor("A");
        bicycle.setNumWheels(2);
        bicycle.setModel("C");
        bicycle.setType(VehicleType.BICYCLE);

        // Act
        this.manager.save(bicycle);

        // Assert
        List<Bicycle> all = this.manager.findAll();
        Assert.assertEquals(3, all.size());
    }

    @Test
    @DatabaseSetup("/db/bicycle.xml")
    public void testDelete() {
        // Arrange
        final Bicycle bicycle = this.manager.get(1L);

        // Act
        this.manager.delete(bicycle);

        // Assert
        List<Bicycle> all = this.manager.findAll();
        Assert.assertEquals(1, all.size());
    }

}
