package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.Ticket;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.TicketService;
import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    //public
    @GetMapping("/public/tickets/available/{runId}/{stationFrom}/{stationTo}")
    @JsonView(JacksonView.MinimalInfo.class)
    public ResponseEntity<ServiceAnswer<List<Ticket>>> getAvailableTickets(@PathVariable int runId,
                                                                           @PathVariable int stationFrom,
                                                                           @PathVariable int stationTo){
        return ServiceAnswerHelper.wrapIntoResponse(ticketService.getAvailableTickets(runId, stationFrom, stationTo));
    }

    @PostMapping("/public/tickets/buy")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Integer>> buyTickets(@RequestBody List<Ticket> tickets){
        return ServiceAnswerHelper.wrapIntoResponse(ticketService.buyTickets(tickets));
    }

    //private
    @GetMapping("/all/{runId}")
    @JsonView(JacksonView.MinimalInfo.class)
    public ResponseEntity<ServiceAnswer<List<Ticket>>> getAllPurchasedRunTickets(@PathVariable int runId){
        return ServiceAnswerHelper.wrapIntoResponse(ticketService.getAllPurchasedRunTickets(runId));
    }
}
