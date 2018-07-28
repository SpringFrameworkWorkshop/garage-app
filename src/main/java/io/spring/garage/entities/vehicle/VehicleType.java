package io.spring.garage.entities.vehicle;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum VehicleType {

    BASE_NOT_DEFINED(Values.BASE_NOT_DEFINED),
    BICYCLE(Values.BICYCLE_TYPE),
    CAR(Values.CAR_TYPE),
    MOTORBIKE(Values.MOTORBIKE_TYPE);

    private String value;

    VehicleType(final String value) {
        this.value = value;
    }

    public static class Values {

        private Values() {}

        public static final String BASE_NOT_DEFINED = "BASE_NOT_DEFINED";
        public static final String BICYCLE_TYPE = "BICYCLE";
        public static final String CAR_TYPE = "CAR";
        public static final String MOTORBIKE_TYPE = "MOTORBIKE";
    }

    public static VehicleType fromString(final String value) {
        return Arrays.stream(VehicleType.values()).filter(element -> element.name().equals(value)).findFirst().orElse(null);
    }

}
