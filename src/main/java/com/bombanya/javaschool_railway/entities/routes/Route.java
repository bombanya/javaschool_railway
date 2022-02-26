package com.bombanya.javaschool_railway.entities.routes;

import com.bombanya.javaschool_railway.entities.trains.Train;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "route")
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<RouteStation> routeStations = new ArrayList<>();

}
