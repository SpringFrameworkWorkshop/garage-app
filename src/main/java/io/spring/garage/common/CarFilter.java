package io.spring.garage.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CarFilter {
    private String color;
    private String model;
}
