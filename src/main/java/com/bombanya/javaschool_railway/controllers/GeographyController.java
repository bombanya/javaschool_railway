package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.JacksonView;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Country;
import com.bombanya.javaschool_railway.entities.geography.Region;
import com.bombanya.javaschool_railway.entities.geography.Settlement;
import com.bombanya.javaschool_railway.entities.geography.Station;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.geography.CountryService;
import com.bombanya.javaschool_railway.services.geography.RegionService;
import com.bombanya.javaschool_railway.services.geography.SettlementService;
import com.bombanya.javaschool_railway.services.geography.StationService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geography")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class GeographyController {

    private final CountryService countryService;
    private final RegionService regionService;
    private final SettlementService settlementService;
    private final StationService stationService;

    @PostMapping("/country/new/{country}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Country>> saveNewCountry(@PathVariable String country){
        return ServiceAnswerHelper.wrapIntoResponse(countryService.saveNew(country));
    }

    @GetMapping("/country/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Country>>> getAllCountries(){
        return ServiceAnswerHelper.wrapIntoResponse(countryService.getAll());
    }

    @PostMapping("/region/new/{country}/{region}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Region>> saveNewRegion(@PathVariable String country,
                                                          @PathVariable String region){
        return ServiceAnswerHelper.wrapIntoResponse(regionService.saveNew(country, region));
    }

    @GetMapping("/region/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Region>>> getAllRegions(){
        return ServiceAnswerHelper.wrapIntoResponse(regionService.getAll());
    }

    @PostMapping("/settlement/new/{country}/{region}/{settlement}/{timeZone1}/{timeZone2}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Settlement>> saveNewSettlement(@PathVariable String country,
                                                                       @PathVariable String region,
                                                                       @PathVariable String settlement,
                                                                       @PathVariable String timeZone1,
                                                                       @PathVariable String timeZone2){
        return ServiceAnswerHelper
                .wrapIntoResponse(settlementService.saveNew(country, region,
                        settlement, timeZone1 + "/" + timeZone2));
    }

    @GetMapping("/settlement/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Settlement>>> getAllSettlements(){
        return ServiceAnswerHelper.wrapIntoResponse(settlementService.getAll());
    }

    @GetMapping("/settlement/all/{nameStart}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Settlement>>> getAllSettlementsByNameStart(
            @PathVariable String nameStart){
        return ServiceAnswerHelper.wrapIntoResponse(settlementService
                .getByNameStartsWith(nameStart));
    }

    @PostMapping("/station/new/id/{settlId}/{name}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Station>> saveNewStationBySettlId(@PathVariable int settlId,
                                                                          @PathVariable String name){
        return ServiceAnswerHelper.wrapIntoResponse(stationService.saveNewBySettlId(settlId, name));
    }

    @GetMapping("/station/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Station>>> getAllStations(){
        return ServiceAnswerHelper.wrapIntoResponse(stationService.getAll());
    }
}
