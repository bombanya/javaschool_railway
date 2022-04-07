package com.bombanya.javaschool_railway.entities.stationSchedule;

import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class StationScheduleInfo {

    @JsonView(JacksonView.UserInfo.class)
    private final int runId;
    @JsonView(JacksonView.UserInfo.class)
    private final int trainId;
    @JsonView(JacksonView.UserInfo.class)
    private final LocalDateTime arrival;
    @JsonView(JacksonView.UserInfo.class)
    private final LocalDateTime departure;
    @JsonView(JacksonView.UserInfo.class)
    private final int finalStationId;
    @JsonView(JacksonView.UserInfo.class)
    private final String finalStationName;
    @JsonView(JacksonView.UserInfo.class)
    private final TrainStatus status;
}
