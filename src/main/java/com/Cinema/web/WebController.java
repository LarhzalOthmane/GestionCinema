package com.Cinema.web;

import java.util.List;

import com.Cinema.entities.Ville;
import com.Cinema.repositories.CategorieRepository;
import com.Cinema.repositories.CinemaRepository;
import com.Cinema.repositories.FilmRepository;
import com.Cinema.repositories.PlaceRepository;
import com.Cinema.repositories.ProjectionRepository;
import com.Cinema.repositories.SalleRepository;
import com.Cinema.repositories.SeanceRepository;
import com.Cinema.repositories.TicketRepository;
import com.Cinema.repositories.VilleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Ville> villes = villeRepository.findAll();
        model.addAttribute("title", "Cinema Hub");
        model.addAttribute("villes", villes);
        return "index";
    }

    
    @GetMapping(value="/dashboard")
    public String getDashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        return new String("dashboard");
    }

}
