package com.bombanya.javaschool_railway.services.routes;

import com.bombanya.javaschool_railway.dao.routes.RouteDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Country;
import com.bombanya.javaschool_railway.entities.geography.Region;
import com.bombanya.javaschool_railway.entities.geography.Settlement;
import com.bombanya.javaschool_railway.entities.geography.Station;
import com.bombanya.javaschool_railway.entities.routes.Route;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.RouteStationId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @Mock
    private RouteDAO daoMock;
    @InjectMocks
    private RouteService serviceWithMocks;
    private List<Route> testRoutes;

    @BeforeEach
    void setUp() {
        Country country1 = new Country();
        country1.setName("Russia");
        country1.setId(1);

        Region region1 = new Region();
        region1.setId(1);
        region1.setName("Moscow");
        region1.setCountry(country1);

        Settlement settlement1 = Settlement.builder()
                .id(1)
                .name("Moscow1")
                .region(region1)
                .timeZone(ZoneId.of("Europe/Moscow"))
                .build();
        Settlement settlement2 = Settlement.builder()
                .id(2)
                .name("Moscow2")
                .region(region1)
                .timeZone(ZoneId.of("Europe/Moscow"))
                .build();
        Settlement settlement3 = Settlement.builder()
                .id(3)
                .name("Moscow3")
                .region(region1)
                .timeZone(ZoneId.of("Europe/Moscow"))
                .build();
        Station station1 = new Station();
        station1.setId(1);
        station1.setName("Station1");
        station1.setSettlement(settlement1);
        Station station2 = new Station();
        station2.setId(2);
        station2.setName("Station2");
        station2.setSettlement(settlement2);
        Station station3 = new Station();
        station3.setId(3);
        station3.setName("Station3");
        station3.setSettlement(settlement3);

        Route testRoute1 = new Route();
        testRoute1.setId(1);

        RouteStationId routeStationId1 = new RouteStationId();
        routeStationId1.setRouteId(1);
        routeStationId1.setStationId(1);
        RouteStation routeStation1 = RouteStation.builder()
                .id(routeStationId1)
                .route(testRoute1)
                .station(station1)
                .serialNumberOnTheRoute(1)
                .build();

        RouteStationId routeStationId2 = new RouteStationId();
        routeStationId1.setRouteId(1);
        routeStationId1.setStationId(2);
        RouteStation routeStation2 = RouteStation.builder()
                .id(routeStationId2)
                .route(testRoute1)
                .station(station2)
                .serialNumberOnTheRoute(2)
                .build();

        RouteStationId routeStationId3 = new RouteStationId();
        routeStationId1.setRouteId(1);
        routeStationId1.setStationId(3);
        RouteStation routeStation3 = RouteStation.builder()
                .id(routeStationId3)
                .route(testRoute1)
                .station(station3)
                .serialNumberOnTheRoute(3)
                .build();

        testRoute1.setRouteStations(Arrays.asList(routeStation1, routeStation2, routeStation3));
        testRoutes = Arrays.asList(testRoute1);
    }

    @Test
    void testGetByStartAndFinishSettlementsFilteringPositive() {
        Mockito.when(daoMock.findByTwoSettlements(1, 3)).thenReturn(testRoutes);
        ServiceAnswer<List<Route>> result = serviceWithMocks.getByStartAndFinishSettlements(1, 3);
        assertTrue(result.isSuccess());
        assertTrue(verifyRoutesList(testRoutes, result.getServiceResult()));
    }

    @Test
    void testGetByStartAndFinishSettlementsFilteringNegative() {
        Mockito.when(daoMock.findByTwoSettlements(3, 1)).thenReturn(testRoutes);
        ServiceAnswer<List<Route>> result = serviceWithMocks.getByStartAndFinishSettlements(3, 1);
        assertTrue(result.isSuccess());
        assertTrue(result.getServiceResult().isEmpty());
    }

    private boolean verifyRoutesList(List<Route> expected, List<Route> actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        if (expected.size() != actual.size()) return false;
        for (Route route : expected) {
            if (actual.stream()
                    .filter(route1 -> route1.getId().equals(route.getId()))
                    .count() != 1) return false;
        }
        return true;
    }
}