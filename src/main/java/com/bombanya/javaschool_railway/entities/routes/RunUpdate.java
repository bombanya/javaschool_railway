package com.bombanya.javaschool_railway.entities.routes;

import com.bombanya.javaschool_railway.entities.geography.Station;
import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "run_updates")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunUpdate {

    @EmbeddedId
    private RunUpdateId id;

    @MapsId("runId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "run_id", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Run run;

    @MapsId("stationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "station_id", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Station station;

    @Column(name = "arrival_delta", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Long arrivalDelta;

    @Column(name = "departure_delta", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Long departureDelta;
}