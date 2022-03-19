package com.bombanya.javaschool_railway.services.routes;

import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.routes.RunSearchingResultDTO;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RunSearchingService {

    private final RunService runService;
    private final TicketService ticketService;
    private final RouteService routeService;

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Run>> getByStartAndFinishSettlementsAndStartDay(int settlFrom,
                                                                              int settlTo,
                                                                              LocalDate date){
        return ServiceAnswerHelper.ok(routeService.getByStartAndFinishSettlements(settlFrom, settlTo)
                .getServiceResult()
                .stream()
                .flatMap(route -> runService.getAllByRouteId(route.getId())
                        .getServiceResult()
                        .stream())
                .filter(run -> {
                    RouteStation startStation = runService.getRouteStationFromRunBySettlId(run, settlFrom)
                            .getServiceResult();
                    return runService.getStationLocalTimeDeparture(run, startStation)
                            .toLocalDate()
                            .equals(date);
                })
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<RunSearchingResultDTO>> searchForRuns(int settlFrom,
                                                                    int settlTo,
                                                                    LocalDate date){
        return ServiceAnswerHelper.ok(getByStartAndFinishSettlementsAndStartDay(
                settlFrom,
                settlTo,
                date)
                .getServiceResult()
                .stream()
                .map(run -> getResultDTOFromRun(run, settlFrom, settlTo))
                .filter(runSearchingResultDTO -> runSearchingResultDTO.getTicketsAvailable() > 0)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<RunSearchingResultDTO>> getTrainSchedule(int trainId){
        return ServiceAnswerHelper.ok(runService.getAllByTrainId(trainId)
                .getServiceResult()
                .stream()
                .map(run -> getResultDTOFromRun(run,
                        run
                                .getRoute()
                                .getRouteStations()
                                .stream()
                                .min(Comparator.comparing(RouteStation::getSerialNumberOnTheRoute))
                                .get()
                                .getStation()
                                .getSettlement()
                                .getId(),
                        run
                                .getRoute()
                                .getRouteStations()
                                .stream()
                                .max(Comparator.comparing(RouteStation::getSerialNumberOnTheRoute))
                                .get()
                                .getStation()
                                .getSettlement()
                                .getId()))
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public RunSearchingResultDTO getResultDTOFromRun(Run run, int settlFrom, int settlTo){
        RouteStation from = runService.getRouteStationFromRunBySettlId(run, settlFrom).getServiceResult();
        RouteStation to = runService.getRouteStationFromRunBySettlId(run, settlTo).getServiceResult();
        return RunSearchingResultDTO.builder()
                .runId(run.getId())
                .startStation(from.getStation())
                .finishStation(to.getStation())
                .startStationTimeDeparture(runService.getStationLocalTimeDeparture(run, from))
                .finishStationTimeArrival(runService.getStationLocalTimeArrival(run, to))
                .ticketsAvailable(ticketService.countAvailableTickets(run.getId(),
                                from.getSerialNumberOnTheRoute(),
                                to.getSerialNumberOnTheRoute())
                        .getServiceResult())
                .travelTime(to.getStageArrival() - from.getStageDeparture())
                .startTime(run.getStartUtc())
                .finishTime(run.getFinishUtc())
                .routeId(run.getRoute().getId())
                .build();
    }

}
