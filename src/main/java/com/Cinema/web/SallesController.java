package com.Cinema.web;

import com.Cinema.entities.Salle;
import com.Cinema.repositories.CinemaRepository;
import com.Cinema.repositories.SalleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * SallesController
 */

@Controller
public class SallesController {

    @Autowired
    private SalleRepository repository;
    @Autowired
    private CinemaRepository cinemaRepository;

    @GetMapping(value="/createSalle")
    public String createSalle(@RequestParam String name, @RequestParam int nombrePlace, @RequestParam Long cinemaId) {
        Salle salle = new Salle();
        salle.setName(name);
        salle.setNombrePlace(nombrePlace);
        salle.setCinema(cinemaRepository.getById(cinemaId));
        repository.save(salle);
        return "redirect:/";
    }
    
    @GetMapping(value = "/editSalle")
    public String editSalle(@RequestParam Long id, @RequestParam String name, @RequestParam int nombrePlace, @RequestParam Long cinemaId) {
        Salle salle = repository.findById(id).get();
        salle.setName(name);
        salle.setNombrePlace(nombrePlace);
        salle.setCinema(cinemaRepository.getById(cinemaId));
        repository.save(salle);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteSalle")
    public String deleteSalle(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }
    
}