package com.Cinema.web;

import java.util.Date;

import com.Cinema.entities.Projection;
import com.Cinema.repositories.FilmRepository;
import com.Cinema.repositories.ProjectionRepository;
import com.Cinema.repositories.SalleRepository;
import com.Cinema.repositories.SeanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ProjectionsController
 */

@Controller
public class ProjectionsController {

    @Autowired
    private ProjectionRepository repository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SalleRepository salleRepository;

    @GetMapping(value="/createProjection")
    public String createProjection(@RequestParam Date dateProjection, @RequestParam double prix, @RequestParam Long salleId, @RequestParam Long filmId, @RequestParam Long seanceId) {
        Projection projection = new Projection();
        projection.setDateProjection(dateProjection);
        projection.setPrix(prix);
        projection.setSalle(salleRepository.findById(seanceId).get());
        projection.setFilm(filmRepository.findById(filmId).get());
        projection.setSeance(seanceRepository.findById(seanceId).get());
        repository.save(projection);
        return "redirect:/";
    }

    @GetMapping(value = "/editProjection")
    public String editProjection(@RequestParam Long id, @RequestParam Date dateProjection, @RequestParam double prix, @RequestParam Long salleId, @RequestParam Long filmId, @RequestParam Long seanceId) {
        Projection projection = repository.findById(id).get();
        projection.setDateProjection(dateProjection);
        projection.setPrix(prix);
        projection.setSalle(salleRepository.findById(seanceId).get());
        projection.setFilm(filmRepository.findById(filmId).get());
        projection.setSeance(seanceRepository.findById(seanceId).get());
        repository.save(projection);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteProjection")
    public String deleteProjection(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }

}