package com.playsafe.uc.service;

import org.springframework.stereotype.Service;

@Service
public class UnitConversionService {

    public String convertKelvinToCelsius(Float kelvin) {
        Float celsius = kelvin - 273.15F;
        return "Celsius : " + celsius;

    }

    public String convertCelsiusToKelvin(Float celsius) {
        float kelvin = celsius + 273.15F;
        return "Kelvin : " + kelvin;
    }

    public String convertMilesToKiloMeters(Float miles) {
        float km = miles * 1.609344f;
        return "Kilometers : " + km;
    }

    public String convertKilometersToMiles(Float kilometers) {
        float miles = kilometers * 0.621f;
        return "Miles : " + miles;
    }
}
