package com.bombanya.javaschool_railway.services;

import com.bombanya.javaschool_railway.dao.TicketDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.Ticket;
import com.bombanya.javaschool_railway.entities.routes.Route;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.trains.Seat;
import com.bombanya.javaschool_railway.entities.trains.Wagon;
import com.bombanya.javaschool_railway.services.routes.RunService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketDAO dao;
    private final RunService runService;

    @Transactional(readOnly = true)
    public ServiceAnswer<Integer> countAvailableTickets(int runId, int serialFrom, int serialTo){
        ServiceAnswer<Run> run = runService.getById(runId);
        if (!run.isSuccess()) return ServiceAnswerHelper.badRequest(run.getErrorMessage());
        int allTickets = run.getServiceResult()
                .getTrain()
                .getWagons()
                .stream()
                .mapToInt(wagon -> wagon.getWagonType().getSeats().size())
                .sum();
        return ServiceAnswerHelper.ok(allTickets
                - dao.getAllOccupiedOnRange(runId, serialFrom, serialTo).size());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Ticket>> getAvailableTickets(int runId, int stationFrom, int stationTo){
        ServiceAnswer<Run> runWrapper = runService.getById(runId);
        if (!runWrapper.isSuccess()) return ServiceAnswerHelper.badRequest(runWrapper.getErrorMessage());
        Run run = runWrapper.getServiceResult();
        ServiceAnswer<RouteStation> from = runService.getRouteStationFromRunByStationId(run, stationFrom);
        ServiceAnswer<RouteStation> to = runService.getRouteStationFromRunByStationId(run, stationTo);
        if (!from.isSuccess()) return ServiceAnswerHelper.badRequest(from.getErrorMessage());
        if (!to.isSuccess()) return ServiceAnswerHelper.badRequest(to.getErrorMessage());
        List<Ticket> occupied = dao.getAllOccupiedOnRange(runId,
                from.getServiceResult().getSerialNumberOnTheRoute(),
                to.getServiceResult().getSerialNumberOnTheRoute());
        List<Ticket> tickets = new ArrayList<>();
        for (Wagon wagon: run.getTrain().getWagons()) {
            for (Seat seat: wagon.getWagonType().getSeats()) {
                if (occupied.stream()
                        .noneMatch(ticket -> ticket.getWagon().getId().equals(wagon.getId()) &&
                                ticket.getSeat().getId().equals(seat.getId()))){
                    tickets.add(Ticket.builder()
                            .run(run)
                            .seat(seat)
                            .wagon(wagon)
                            .startStation(from.getServiceResult().getStation())
                            .finishStation(to.getServiceResult().getStation())
                            .price(countPrice(run.getRoute(),
                                    from.getServiceResult().getSerialNumberOnTheRoute(),
                                    to.getServiceResult().getSerialNumberOnTheRoute(),
                                    seat))
                            .build());
                }
            }
        }
        return ServiceAnswerHelper.ok(tickets);
    }

    @Transactional(readOnly = true)
    public int countPrice(Route route, int serialFrom, int serialTo, Seat seat){
        int price = 0;
        for (int i = serialFrom + 1; i <= serialTo; i++){
            int finalI = i;
            price += route.getRouteStations()
                    .stream()
                    .filter(routeStation -> routeStation
                            .getSerialNumberOnTheRoute()
                            .equals(finalI))
                    .findFirst()
                    .get()
                    .getStagePrice() * seat.getSeatClass();
        }
        return price;
    }
}
