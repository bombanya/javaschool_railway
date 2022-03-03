package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.JacksonView;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Country;
import com.bombanya.javaschool_railway.entities.geography.Region;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.geography.CountryService;
import com.bombanya.javaschool_railway.services.geography.RegionService;
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
    public ResponseEntity<ServiceAnswer<Country>> saveNewCountry(@PathVariable String country){
        return ServiceAnswerHelper.wrapIntoResponse(countryService.saveNewCountry(country));
    }

    @GetMapping("/country/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Country>>> getAllCountries(){
        return ServiceAnswerHelper.wrapIntoResponse(countryService.getAllCountries());
    }

    @PostMapping("/region/new/{country}/{region}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Region>> saveNewRegion(@PathVariable String country,
                                                          @PathVariable String region){
        return ServiceAnswerHelper.wrapIntoResponse(regionService.saveNewRegion(country, region));
    }

    @GetMapping("/region/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Region>>> getAllRegions(){
        return ServiceAnswerHelper.wrapIntoResponse(regionService.getAllRegions());
    }

}
