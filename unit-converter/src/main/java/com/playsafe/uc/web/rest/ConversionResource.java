package com.playsafe.uc.web.rest;

import com.playsafe.uc.service.UnitConversionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConversionResource {

    @Autowired
    private UnitConversionService conversionService;



    @GetMapping("/conversions/ktoc")
    public String convertKelvinToCelsius(@RequestParam("kelvin") Float kelvin) {

        return conversionService.convertKelvinToCelsius(kelvin);
    }
    @GetMapping("/conversions/ctok")
    public String convertCelsiusToKelvin(@RequestParam("celsius") Float celsius) {

        return conversionService.convertCelsiusToKelvin(celsius);
    }
    @GetMapping("/conversions/mtok")
    public String convertMilesToKiloMeters(@RequestParam("miles") Float miles) {

        return conversionService.convertMilesToKiloMeters(miles);
    }

    @GetMapping("/conversions/ktom")
    public String convertKilometersToMiles(@RequestParam("kilometers") Float kilometers) {
              return conversionService.convertKilometersToMiles(kilometers);
    }
}
