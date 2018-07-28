package io.spring.garage.controllers.dtos.resolver;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.spring.garage.controllers.dtos.BicycleDTO;
import io.spring.garage.controllers.dtos.CarDTO;
import io.spring.garage.controllers.dtos.MotorBikeDTO;
import io.spring.garage.controllers.dtos.VehicleDTO;
import io.spring.garage.entities.vehicle.VehicleType;

import java.io.IOException;

public class VehicleTypeResolver implements TypeIdResolver {

    private static final BiMap<Class, VehicleType> MAPPING = HashBiMap.create();

    private JavaType baseType;

    static {
        MAPPING.put(CarDTO.class, VehicleType.CAR);
        MAPPING.put(MotorBikeDTO.class, VehicleType.MOTORBIKE);
        MAPPING.put(BicycleDTO.class, VehicleType.BICYCLE);
        MAPPING.put(VehicleDTO.class, VehicleType.BASE_NOT_DEFINED);
    }

    @Override
    public void init(JavaType javaType) {
        this.baseType = javaType;
    }

    @Override
    public String idFromValue(Object o) {
        return idFromValueAndType(o, o.getClass());
    }

    @Override
    public String idFromValueAndType(Object o, Class<?> aClass) {
        return MAPPING.get(aClass).getValue();
    }

    @Override
    public String idFromBaseType() {
        return VehicleType.BASE_NOT_DEFINED.getValue();
    }

    @Override
    public JavaType typeFromId(DatabindContext databindContext, String s) throws IOException {
        VehicleType vehicleType = VehicleType.fromString(s);
        if (vehicleType == null) {
            vehicleType = VehicleType.BASE_NOT_DEFINED;
        }
        return TypeFactory.defaultInstance().constructSpecializedType(this.baseType, MAPPING.inverse().get(vehicleType));
    }

    @Override
    public String getDescForKnownTypeIds() {
        return null;
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}
