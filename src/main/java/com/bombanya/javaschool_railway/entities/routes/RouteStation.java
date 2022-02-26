package com.bombanya.javaschool_railway.entities.routes;

import com.bombanya.javaschool_railway.entities.geography.Station;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "route_stations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteStation {

    @EmbeddedId
    private RouteStationId id;

    @MapsId("routeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @MapsId("stationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Column(name = "serial_number_on_the_route", nullable = false)
    private Integer serialNumberOnTheRoute;

    @Column(name = "stage_price", nullable = false)
    private Integer stagePrice;

    @Column(name = "stage_distance", nullable = false)
    private Integer stageDistance;

    @Column(name = "stage_departure", nullable = false)
    private Long stageDeparture;

    @Column(name = "stage_arrival", nullable = false)
    private Long stageArrival;

}