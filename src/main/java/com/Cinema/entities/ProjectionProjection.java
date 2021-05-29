package com.Cinema.entities;

import java.util.Date;
import java.util.List;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "p1", types = {com.Cinema.entities.Projection.class})
public interface ProjectionProjection {
    public Long getId();
    public double getPrix();
    public Date getDateProjection();
    public Salle getSalle();
    public Film getFilm();
    public Seance getSeance();
    public List<Ticket> getTickets();
}
