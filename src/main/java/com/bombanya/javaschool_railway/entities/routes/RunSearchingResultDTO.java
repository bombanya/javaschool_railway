package com.bombanya.javaschool_railway.entities.routes;

import com.bombanya.javaschool_railway.utils.JacksonView;
import com.bombanya.javaschool_railway.entities.geography.Station;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class RunSearchingResultDTO {

    @JsonView(JacksonView.UserInfo.class)
    private final int runId;
    @JsonView(JacksonView.UserInfo.class)
    private final Station startStation;
    @JsonView(JacksonView.UserInfo.class)
    private final Station finishStation;
    @JsonView(JacksonView.UserInfo.class)
    private final LocalDateTime startStationTimeDeparture;
    @JsonView(JacksonView.UserInfo.class)
    private final LocalDateTime finishStationTimeArrival;
    @JsonView(JacksonView.UserInfo.class)
    private final Instant startTime;
    @JsonView(JacksonView.UserInfo.class)
    private final Instant finishTime;
    @JsonView(JacksonView.UserInfo.class)
    private final int ticketsAvailable;
    @JsonView(JacksonView.UserInfo.class)
    private final long travelTime;
    @JsonView(JacksonView.UserInfo.class)
    private final int routeId;
}
