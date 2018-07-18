package io.spring.garage;

import io.spring.commandlinemenu.Menu;
import io.spring.commandlinemenu.MenuBuilder;

import java.util.Scanner;


public class GarageRunner {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        String option;


        do {
            option = generateMenu().play();
            if (option != null) {
                switch (option) {
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

    }

    private static Menu generateMenu() {
        io.spring.commandlinemenu.Menu submenu1 = MenuBuilder.createSubMenu("CARS", 1)
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

    private static Car selectCar() {
        //TODO:
        return null;
    }

    private static MotorBike selectMotorBike() {
        //TODO:
        return null;
    }

    private static Bicycle selectBicycle() {
        //TODO:
        return null;
    }

    private static Car carBuilder() {
        //TODO:
        return null;
    }

    private static MotorBike motorBikeBuilder() {
        //TODO:
        return null;
    }

    private static Bicycle bicycleBuilder() {
        //TODO:
        return null;
    }

}
