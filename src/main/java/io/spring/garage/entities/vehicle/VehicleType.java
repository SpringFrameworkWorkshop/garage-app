package io.spring.garage.entities.vehicle;

import lombok.Getter;

@Getter
public enum VehicleType {

    BICYCLE(Values.BICYCLE_TYPE),
    CAR(Values.CAR_TYPE),
    MOTORBIKE(Values.MOTORBIKE_TYPE);

    private String value;

    VehicleType(final String value) {
        this.value = value;
    }

    public static class Values {

        private Values() {}

        public static final String BICYCLE_TYPE = "BICYCLE";
        public static final String CAR_TYPE = "CAR";
        public static final String MOTORBIKE_TYPE = "MOTORBIKE";
    }

}
