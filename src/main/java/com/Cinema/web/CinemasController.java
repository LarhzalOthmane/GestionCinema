package com.Cinema.web;

import com.Cinema.entities.Cinema;
import com.Cinema.repositories.CinemaRepository;
import com.Cinema.repositories.VilleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;

/**
 * CinemasController
 */
@Controller
public class CinemasController {

    @Autowired
    private CinemaRepository repository;
    @Autowired
    private VilleRepository villeRepository;

    @PostMapping(value = "/createCinema")
    public String createCinema(@RequestBody CinemaForm cinemaForm) {
        Cinema cinema = new Cinema();
        long id = cinemaForm.getId();
        long villeId = cinemaForm.getVilleId();
        cinema.setName(cinemaForm.getName());
        cinema.setNombreSalles(cinemaForm.getNombreSalles());
        cinema.setVille(villeRepository.getById(villeId));
        cinema.setAltitude(cinemaForm.getAltitude());
        cinema.setLatitude(cinemaForm.getLatitude());
        cinema.setLongitude(cinemaForm.getLatitude());
        cinema.setId(id == -1 ? null : id);
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

@Data

class CinemaForm {
    private int id;
    private String name;
    private double longitude, latitude, altitude;
    private int nombreSalles;
    private int villeId;
}