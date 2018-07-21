package io.spring.garage.dao.vehicle;

import io.spring.garage.entities.vehicle.Car;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CarDAO extends AbstractVehicleDAO<Car> {

    private static CarDAO instance;

    private CarDAO() {
        super(Car.class);
    }

    public static CarDAO getInstance() {
        if (instance == null) {
            instance = new CarDAO();
        }
        return instance;
    }

    public List<Car> findAllByColor(final String color) {

        List<Car> elements = null;

        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Make the query
            CriteriaBuilder cb = ENTITY_MANAGER_FACTORY.getCriteriaBuilder();
            CriteriaQuery<Car> c = cb.createQuery(Car.class);
            Root<Car> car = c.from(Car.class);
            CriteriaQuery<Car> select = c.select(car).where(cb.equal(car.get("color"), color));
            // Get a List of elements
            elements = manager.createQuery(select).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return elements;
    }

}
