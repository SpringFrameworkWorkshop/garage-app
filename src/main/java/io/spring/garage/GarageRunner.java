package io.spring.garage;

import io.spring.commandlinemenu.Menu;
import io.spring.commandlinemenu.MenuBuilder;
import io.spring.garage.entities.vehicle.Bicycle;
import io.spring.garage.entities.vehicle.Car;
import io.spring.garage.entities.vehicle.MotorBike;
import io.spring.garage.manager.vehicle.BicycleManager;
import io.spring.garage.manager.vehicle.CarManager;
import io.spring.garage.manager.vehicle.MotorBikeManager;

import java.util.Scanner;

import static java.lang.System.exit;


public class GarageRunner {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        String option;


        do {
            option = generateMenu().play();
            if (option != null) {
                switch (option) {
                    case "1.0":
                        System.out.println(CarManager.getInstance().getDao().findAllByColor(selectColor()));
                        break;
                    case "1.1":
                        System.out.println(CarManager.getInstance().findAll());
                        break;
                    case "1.2":
                        CarManager.getInstance().save(carBuilder());
                        break;
                    case "1.3":
                        CarManager.getInstance().delete(selectCar());
                        break;
                    case "2.1":
                        System.out.println(MotorBikeManager.getInstance().findAll());
                        break;
                    case "2.2":
                        MotorBikeManager.getInstance().save(motorBikeBuilder());
                        break;
                    case "2.3":
                        MotorBikeManager.getInstance().delete(selectMotorBike());
                        break;
                    case "3.1":
                        System.out.println(BicycleManager.getInstance().findAll());
                        break;
                    case "3.2":
                        BicycleManager.getInstance().save(bicycleBuilder());
                        break;
                    case "3.3":
                        BicycleManager.getInstance().delete(selectBicycle());
                        break;
                }
            }
        } while (option != null);

        System.out.println("GOOD BYE");

        exit(0);

    }

    private static Menu generateMenu() {
        io.spring.commandlinemenu.Menu submenu1 = MenuBuilder.createSubMenu("CARS", 1)
                .addSimpleOption(0, "Filter by color")
                .addSimpleOption(1, "List")
                .addSimpleOption(2, "New")
                .addSimpleOption(3, "Delete")
                .addExitOption(4, "Back")
                .build();
        Menu submenu2 = MenuBuilder.createSubMenu("MOTORBIKES", 2)
                .addSimpleOption(1, "List")
                .addSimpleOption(2, "New")
                .addSimpleOption(3, "Delete")
                .addExitOption(4, "Back")
                .build();
        Menu submenu3 = MenuBuilder.createSubMenu("BICYCLE", 3)
                .addSimpleOption(1, "List")
                .addSimpleOption(2, "New")
                .addSimpleOption(3, "Delete")
                .addExitOption(4, "Back")
                .build();

        return MenuBuilder.createMainMenu("GARAGE MENU")
                .addSubMenuOption("Cars", submenu1)
                .addSubMenuOption("Motorbikes", submenu2)
                .addSubMenuOption("Bicycle", submenu3)
                .addExitOption(4, "Exit")
                .build();
    }

    private static String selectColor() {
        System.out.println("Introduce a color");
        return in.nextLine();
    }

    private static Car selectCar() {
        System.out.println(CarManager.getInstance().findAll());
        System.out.println("Select an element of the list");
        int element = in.nextInt();
        return CarManager.getInstance().get(element);
    }

    private static MotorBike selectMotorBike() {
        System.out.println(MotorBikeManager.getInstance().findAll());
        System.out.println("Select an element of the list");
        int element = in.nextInt();
        return MotorBikeManager.getInstance().get(element);
    }

    private static Bicycle selectBicycle() {
        System.out.println(BicycleManager.getInstance().findAll());
        System.out.println("Select an element of the list");
        int element = in.nextInt();
        return BicycleManager.getInstance().get(element);
    }

    private static Car carBuilder() {
        System.out.println("Set the plate value");
        String plate = in.nextLine();
        System.out.println("Set the color value");
        String color = in.nextLine();
        System.out.println("Set the  model value");
        String model = in.nextLine();

        final Car car = new Car();
        car.setPlate(plate);
        car.setColor(color);
        car.setModel(model);
        return car;
    }

    private static MotorBike motorBikeBuilder() {
        System.out.println("Set the plate value");
        String plate = in.nextLine();
        System.out.println("Set the color value");
        String color = in.nextLine();
        System.out.println("Set the model value");
        String model = in.nextLine();

        final MotorBike motorBike = new MotorBike();
        motorBike.setPlate(plate);
        motorBike.setColor(color);
        motorBike.setModel(model);
        return motorBike;
    }

    private static Bicycle bicycleBuilder() {
        System.out.println("Set the color value");
        String color = in.nextLine();
        System.out.println("Set the model value");
        String model = in.nextLine();

        final Bicycle bicycle = new Bicycle();
        bicycle.setColor(color);
        bicycle.setModel(model);
        return bicycle;
    }

}
