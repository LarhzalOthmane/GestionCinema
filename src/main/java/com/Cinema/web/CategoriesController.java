package com.Cinema.web;

import com.Cinema.entities.Categorie;
import com.Cinema.repositories.CategorieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * CategoriesController
 */

@Controller
public class CategoriesController {

    @Autowired
    private CategorieRepository repository;

    @GetMapping(value="/createCategorie")
    public String createCategorie(@RequestParam String categorieName) {
        Categorie categorie = new Categorie();
        categorie.setNom(categorieName);
        repository.save(categorie);
        return "redirect:/";
    }
    
    @GetMapping(value = "/editCategorie")
    public String editCategorie(@RequestParam Long id, @RequestParam String nomCategorie) {
        Categorie categorie = repository.findById(id).get();
        categorie.setNom(nomCategorie);
        repository.save(categorie);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteCategorie")
    public String deleteCategorie(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }
    
}