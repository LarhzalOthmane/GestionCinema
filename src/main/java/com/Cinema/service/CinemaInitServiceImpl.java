package com.Cinema.service;

import com.Cinema.entities.Categorie;
import com.Cinema.entities.Cinema;
import com.Cinema.entities.Film;
import com.Cinema.entities.Place;
import com.Cinema.entities.Projection;
import com.Cinema.entities.Salle;
import com.Cinema.entities.Seance;
import com.Cinema.entities.Ticket;
import com.Cinema.entities.Ville;
import com.Cinema.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

@Service
@Transactional
@EntityScan("com.Cinema.entities")
@EnableJpaRepositories("com.Cinema.repositories")
public class CinemaInitServiceImpl implements ICinemaInitService {

    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void initVilles() {
        Stream.of("Casablanca", "Marrakech", "Rabat", "Tanger").forEach(nomVille -> {
            Ville ville = new Ville();
            ville.setName(nomVille);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(ville -> {
            Stream.of("Megarama", "IMAX", "FOUNOUN", "CHAHRAZAD", "DAOULIZ").forEach(nomCinema -> {
                Cinema cinema = new Cinema();
                cinema.setName(nomCinema);
                cinema.setNombreSalles(3 + (int) (Math.random() * 7));
                cinema.setVille(ville);
                cinemaRepository.save(cinema);
            });
        });

    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
            for (int i = 0; i < cinema.getNombreSalles(); i++) {
                Salle salle = new Salle();
                salle.setName("Salle " + (i + 1));
                salle.setCinema(cinema);
                salle.setNombrePlace(15 + (int) (Math.random() * 20));
                salleRepository.save(salle);
            }
        });

    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNombrePlace(); i++) {
                Place place = new Place();
                place.setNumero(i + 1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });

    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(s -> {
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void initCategories() {
        Stream.of("Histoire", "Action", "Fiction", "Drama").forEach(c -> {
            Categorie categorie = new Categorie();
            categorie.setNom(c);
            categorieRepository.save(categorie);
        });

    }

    @Override
    public void initFilms() {
        double[] durees = new double[] { 1, 1.5, 2, 2.5, 3 };
        List<Categorie> categories = categorieRepository.findAll();
        Stream.of("Avengers: End Game", "Captain America: Civil War", "Avengers: Infinity War", "Green Book",
                "Forrest Gump").forEach(nomFilm -> {
                    Film film = new Film();
                    film.setTitre(nomFilm);
                    film.setDuree(durees[new Random().nextInt(durees.length)]);
                    film.setPhoto(nomFilm.replace(" ", "").replace(":", "") + ".jpg");
                    film.setCategorie(categories.get(new Random().nextInt(categories.size())));
                    filmRepository.save(film);
                });

    }

    @Override
    public void initProjections() {
        double[] prix = new double[] { 30, 50, 60, 70, 90, 100 };
        List<Film> films = filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                    Film film = films.get(index);
                        seanceRepository.findAll().forEach(seance -> {
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setSalle(salle);
                            projection.setPrix(prix[new Random().nextInt(prix.length)]);
                            projection.setSeance(seance);
                            projectionRepository.save(projection);
                        });
                });
            });
        });
    }


    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(projection -> {
            projection.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projection.getPrix());
                ticket.setProjection(projection);
                ticket.setReserve(false);
                ticketRepository.save(ticket);
            });
        });

    }

}
