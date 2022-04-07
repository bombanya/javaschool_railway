package com.bombanya.javaschool_railway.entities.routes;

import com.bombanya.javaschool_railway.entities.geography.Station;
import com.bombanya.javaschool_railway.entities.trains.Train;
import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "run")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "run_id", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Route route;

    @Column(name = "start_utc", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Instant startUtc;

    @Column(name = "finish_utc", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Instant finishUtc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "train_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Train train;

    @ManyToMany
    @JoinTable(name = "run_cancelled_stations",
            joinColumns = @JoinColumn(name = "run_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id"))
    @JsonView(JacksonView.RunFullInfo.class)
    private List<Station> cancelledStations;

}
