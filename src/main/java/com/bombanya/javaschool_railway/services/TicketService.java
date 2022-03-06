package com.bombanya.javaschool_railway.services;

import com.bombanya.javaschool_railway.dao.TicketDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.Route;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.services.routes.RunService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .getRoute()
                .getTrain()
                .getWagons()
                .stream()
                .mapToInt(wagon -> wagon.getWagonType().getSeats().size())
                .sum();
        return ServiceAnswerHelper.ok(allTickets
                - dao.getAllOccupiedOnRange(runId, serialFrom, serialTo).size());
    }

    @Transactional(readOnly = true)
    public int countPrice(Route route, int serialFrom, int serialTo){
        return 0;
    }
}
