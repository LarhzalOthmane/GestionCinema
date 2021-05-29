package com.Cinema.web;

import java.util.Date;

import com.Cinema.entities.Film;
import com.Cinema.repositories.CategorieRepository;
import com.Cinema.repositories.FilmRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FilmsController
 */

@Controller
public class FilmsController {

    @Autowired
    private FilmRepository repository;
    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping(value = "/createFilm")
    public String createFilm(@RequestParam String filmTitre, @RequestParam String description,
            @RequestParam String realisateur, @RequestParam Date dateSortie, @RequestParam double duree,
            @RequestParam String photo, @RequestParam Long categorieId) {
        Film film = new Film();
        film.setTitre(filmTitre);
        film.setDescription(description);
        film.setRealisateur(realisateur);
        film.setDateSortie(dateSortie);
        film.setDuree(duree);
        film.setPhoto(photo);
        film.setCategorie(categorieRepository.findById(categorieId).get());
        repository.save(film);
        return "redirect:/";
    }

    @GetMapping(value = "/editFilm")
    public String editFilm(@RequestParam Long id, @RequestParam String filmTitre, @RequestParam String description,
            @RequestParam String realisateur, @RequestParam Date dateSortie, @RequestParam double duree,
            @RequestParam String photo, @RequestParam Long categorieId) {
        Film film = repository.findById(id).get();
        film.setTitre(filmTitre);
        film.setDescription(description);
        film.setRealisateur(realisateur);
        film.setDateSortie(dateSortie);
        film.setDuree(duree);
        film.setPhoto(photo);
        film.setCategorie(categorieRepository.findById(categorieId).get());
        repository.save(film);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteFilm")
    public String deleteFilm(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }

}