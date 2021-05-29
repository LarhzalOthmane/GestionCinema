package com.Cinema.web;

import com.Cinema.entities.Cinema;
import com.Cinema.repositories.CinemaRepository;
import com.Cinema.repositories.VilleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * CinemasController
 */
@Controller
public class CinemasController {

    @Autowired
    private CinemaRepository repository;
    @Autowired
    private VilleRepository villeRepository;

    @GetMapping(value = "/createCinema")
    public String createCinema(@RequestParam String cinemaName, @RequestParam int nombreSalles,
            @RequestParam Long villeId) {
        Cinema cinema = new Cinema();
        cinema.setName(cinemaName);
        cinema.setNombreSalles(nombreSalles);
        cinema.setVille(villeRepository.findById(villeId).get());
        repository.save(cinema);
        return "redirect:/";
    }

    @GetMapping(value = "/editCinema")
    public String editCinema(@RequestParam Long id, @RequestParam String nomCinema, @RequestParam int nombreSalles,
            @RequestParam Long villeId) {
        Cinema cinema = repository.findById(id).get();
        cinema.setName(nomCinema);
        cinema.setNombreSalles(nombreSalles);
        cinema.setVille(villeRepository.findById(villeId).get());
        repository.save(cinema);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteCinema")
    public String deleteCinema(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }

}