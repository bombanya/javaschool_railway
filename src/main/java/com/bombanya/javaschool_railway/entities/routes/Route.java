package com.bombanya.javaschool_railway.entities.routes;

import com.bombanya.javaschool_railway.JacksonView;
import com.bombanya.javaschool_railway.entities.trains.Train;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "route")
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "train_id", nullable = false)
    @JsonView(JacksonView.RouteFullInfo.class)
    private Train train;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    @JsonView(JacksonView.RouteFullInfo.class)
    private List<RouteStation> routeStations;

}
