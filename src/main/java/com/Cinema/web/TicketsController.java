package com.Cinema.web;

import com.Cinema.entities.Ticket;
import com.Cinema.repositories.PlaceRepository;
import com.Cinema.repositories.ProjectionRepository;
import com.Cinema.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TicketsController
 */

@Controller
public class TicketsController {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ProjectionRepository projectionRepository;

    @GetMapping(value = "/createTicket")
    public String createTicket(@RequestParam String nomClient, @RequestParam double prix,
            @RequestParam int codePayement, @RequestParam boolean reserve, @RequestParam Long placeId,
            @RequestParam Long projectionId) {
        Ticket ticket = new Ticket();
        ticket.setNomClient(nomClient);
        ticket.setPrix(prix);
        ticket.setCodePayement(codePayement);
        ticket.setReserve(reserve);
        ticket.setPlace(placeRepository.getById(placeId));
        ticket.setProjection(projectionRepository.getById(projectionId));
        repository.save(ticket);
        return "redirect:/";
    }

    @GetMapping(value = "/editTicket")
    public String editTicket(@RequestParam Long id, @RequestParam String nomClient, @RequestParam double prix,
            @RequestParam int codePayement, @RequestParam boolean reserve, @RequestParam Long placeId,
            @RequestParam Long projectionId) {
        Ticket ticket = repository.findById(id).get();
        ticket.setNomClient(nomClient);
        ticket.setPrix(prix);
        ticket.setCodePayement(codePayement);
        ticket.setReserve(reserve);
        ticket.setPlace(placeRepository.getById(placeId));
        ticket.setProjection(projectionRepository.getById(projectionId));
        repository.save(ticket);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteTicket")
    public String deleteTicket(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }

}