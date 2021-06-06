package com.Cinema.web;

import com.Cinema.entities.Ville;
import com.Cinema.repositories.VilleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;

@Controller
public class VillesController {

    @Autowired
    private VilleRepository repository;

    @PostMapping("/createVille")
    public String createVille(@RequestBody VilleForm villeForm) {
        Ville ville = new Ville();
        long id = villeForm.getId();
        ville.setName(villeForm.getName());
        ville.setAltitude(villeForm.getAltitude());
        ville.setLatitude(villeForm.getLatitude());
        ville.setLongitude(villeForm.getLongitude());
        ville.setId(id == -1 ? null : id);
        repository.save(ville);
        return "redirect:/";
    }

    @GetMapping(value = "/editVille")
    public String editVille(@RequestBody VilleForm villeForm) {
        long id = villeForm.getId();
        Ville ville = repository.findById(id).get();
        ville.setName(villeForm.getName());
        ville.setAltitude(villeForm.getAltitude());
        ville.setLatitude(villeForm.getLatitude());
        ville.setLongitude(villeForm.getLongitude());
        repository.save(ville);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteVille")
    public String deleteVille(@RequestParam Long id) {
        repository.delete(repository.findById(id).get());
        return "redirect:/";
    }

}

@Data
/**
 * VilleForm
 */
class VilleForm {
    private int id;
    private String name;
    private double longitude, latitude, altitude;
}
