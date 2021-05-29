package com.Cinema;

import com.Cinema.entities.Categorie;
import com.Cinema.entities.Cinema;
import com.Cinema.entities.Film;
import com.Cinema.entities.Place;
import com.Cinema.entities.Projection;
import com.Cinema.entities.Salle;
import com.Cinema.entities.Seance;
import com.Cinema.entities.Ticket;
import com.Cinema.entities.Ville;
import com.Cinema.service.ICinemaInitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {

	@Autowired
	private ICinemaInitService cinemaInitService;
	@Autowired
	private RepositoryRestConfiguration configuration;

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
		System.out.println("Application started");
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		configuration.exposeIdsFor(Film.class, Salle.class, Ticket.class, Categorie.class, Cinema.class, Place.class,
				Projection.class, Seance.class, Ville.class);
		// cinemaInitService.initTickets();
		// cinemaInitService.initProjections();
		// cinemaInitService.initVilles();
		// cinemaInitService.initFilms();
	}

}
