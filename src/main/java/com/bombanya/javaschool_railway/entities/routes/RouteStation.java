package com.bombanya.javaschool_railway.entities.routes;

import com.bombanya.javaschool_railway.utils.JacksonView;
import com.bombanya.javaschool_railway.entities.geography.Station;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(JacksonView.UserInfo.class)
    private RouteStationId id = new RouteStationId();

    @MapsId("routeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @MapsId("stationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Column(name = "serial_number_on_the_route", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer serialNumberOnTheRoute;

    @Column(name = "stage_price", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer stagePrice;

    @Column(name = "stage_distance", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer stageDistance;

    @Column(name = "stage_departure", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Long stageDeparture;

    @Column(name = "stage_arrival", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Long stageArrival;
}