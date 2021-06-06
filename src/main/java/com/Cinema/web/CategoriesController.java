package com.Cinema.web;

import com.Cinema.entities.Categorie;
import com.Cinema.repositories.CategorieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;

/**
 * CategoriesController
 */

@Controller
public class CategoriesController {

    @Autowired
    private CategorieRepository repository;

    @PostMapping(value = "/createCategorie")
    public String createCategorie(@RequestBody CategorieForm categorieForm) {
        Categorie categorie = new Categorie();
        long id = categorieForm.getId();
        categorie.setName(categorieForm.getName());
        categorie.setId(id == -1 ? null : id);
        repository.save(categorie);
        return "redirect:/";
    }

    @GetMapping(value = "/editCategorie")
    public String editCategorie(@RequestParam Long id, @RequestParam String nomCategorie) {
        Categorie categorie = repository.findById(id).get();
        categorie.setName(nomCategorie);
        repository.save(categorie);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteCategorie")
    public String deleteCategorie(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }

}

@Data

class CategorieForm {
    private int id;
    private String name;
}