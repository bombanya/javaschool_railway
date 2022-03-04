package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.JacksonView;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Country;
import com.bombanya.javaschool_railway.entities.geography.Region;
import com.bombanya.javaschool_railway.entities.geography.Settlement;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.geography.CountryService;
import com.bombanya.javaschool_railway.services.geography.RegionService;
import com.bombanya.javaschool_railway.services.geography.SettlementService;
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
    private final SettlementService settlementService;

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

    @PostMapping("/settlement/new/{country}/{region}/{settlement}/{timeZone1}/{timeZone2}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Settlement>> saveNewSettlement(@PathVariable String country,
                                                                       @PathVariable String region,
                                                                       @PathVariable String settlement,
                                                                       @PathVariable String timeZone1,
                                                                       @PathVariable String timeZone2){
        return ServiceAnswerHelper
                .wrapIntoResponse(settlementService.saveNewSettlement(country, region,
                        settlement, timeZone1 + timeZone2));
    }

    @GetMapping("/settlement/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Settlement>>> getAllSettlements(){
        return ServiceAnswerHelper.wrapIntoResponse(settlementService.getAllSettlements());
    }

}
