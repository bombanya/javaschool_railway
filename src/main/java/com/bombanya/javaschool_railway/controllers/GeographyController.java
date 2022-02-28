package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.JacksonView;
import com.bombanya.javaschool_railway.entities.geography.Country;
import com.bombanya.javaschool_railway.entities.geography.Region;
import com.bombanya.javaschool_railway.services.geography.CountryService;
import com.bombanya.javaschool_railway.services.geography.RegionService;
import com.bombanya.javaschool_railway.services.geography.ServiceAnswer;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geography")
@RequiredArgsConstructor
public class GeographyController {

    private final CountryService countryService;
    private final RegionService regionService;

    @PostMapping("/country/new/{country}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<?>> saveNewCountry(@PathVariable String country){
        ServiceAnswer<?> result = countryService.saveNewCountry(country);
        return ResponseEntity.status(result.getHttpStatus()).body(result);
    }

    @GetMapping("/country/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Country>>> getAllCountries(){
        ServiceAnswer<List<Country>> result = countryService.getAllCountries();
        return ResponseEntity.status(result.getHttpStatus()).body(result);
    }

    @PostMapping("/region/new/{country}/{region}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<?>> saveNewRegion(@PathVariable String country,
                                                          @PathVariable String region){
        ServiceAnswer<?> result = regionService.saveNewRegion(country, region);
        return ResponseEntity.status(result.getHttpStatus()).body(result);
    }

    @GetMapping("/region/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Region>>> getAllRegions(){
        ServiceAnswer<List<Region>> result = regionService.getAllRegions();
        return ResponseEntity.status(result.getHttpStatus()).body(result);
    }
}
