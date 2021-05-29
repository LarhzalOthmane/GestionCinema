package com.Cinema.web;

import java.util.Date;

import com.Cinema.entities.Seance;
import com.Cinema.repositories.SeanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SeancesController
 */

@Controller
public class SeancesController {

    @Autowired
    private SeanceRepository repository;

    @GetMapping(value = "/createSeance")
    public String createSeance(@RequestParam Date heureDebut) {
        Seance seance = new Seance();
        seance.setHeureDebut(heureDebut);
        repository.save(seance);
        return "redirect:/";
    }

    @GetMapping(value = "/editSeance")
    public String editSeance(@RequestParam Long id, @RequestParam Date heureDebut) {
        Seance seance = repository.findById(id).get();
        seance.setHeureDebut(heureDebut);
        repository.save(seance);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteSeance")
    public String deleteSeance(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }

}