package com.Cinema.web;

import com.Cinema.entities.Place;
import com.Cinema.repositories.PlaceRepository;
import com.Cinema.repositories.SalleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * PlacesController
 */

@Controller
public class PlacesController {

    @Autowired
    private PlaceRepository repository;

    @Autowired 
    private SalleRepository salleRepository;

    @GetMapping(value="/createPlace")
    public String createPlace(@RequestParam int numero, @RequestParam Long salleId) {
        Place place = new Place();
        place.setSalle(salleRepository.findById(salleId).get());
        place.setNumero(numero);
        repository.save(place);
        return "redirect:/";
    }
    
    @GetMapping(value = "/editPlace")
    public String editPlace(@RequestParam Long id, @RequestParam int numero, @RequestParam Long salleId) {
        Place place = repository.findById(id).get();
        place.setSalle(salleRepository.findById(salleId).get());
        place.setNumero(numero);
        repository.save(place);
        return "redirect:/";
    }

    @GetMapping(value = "/deletePlace")
    public String deletePlace(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }
    
}