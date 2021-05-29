package com.Cinema.web;

import com.Cinema.entities.Ville;
import com.Cinema.repositories.VilleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VillesController {

    @Autowired
    private VilleRepository repository;

    @GetMapping(value = "/createVille")
    public String createVille(@RequestParam String nomVille) {
        Ville ville = new Ville();
        ville.setName(nomVille);
        repository.save(ville);
        return "redirect:/";
    }

    @GetMapping(value = "/editVille")
    public String editVille(@RequestParam Long id, @RequestParam String nomVille) {
        Ville ville = repository.findById(id).get();
        ville.setName(nomVille);
        repository.save(ville);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteVille")
    public String deleteVille(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }

}
