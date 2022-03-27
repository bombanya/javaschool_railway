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



    @GetMapping("/all/{runId}")
    @JsonView(JacksonView.MinimalInfo.class)
    public ResponseEntity<ServiceAnswer<List<Ticket>>> getAllPurchasedRunTickets(@PathVariable int runId){
        return ServiceAnswerHelper.wrapIntoResponse(ticketService.getAllPurchasedRunTickets(runId));
    }
}
