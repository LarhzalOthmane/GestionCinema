package com.Cinema.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "tp", types = {Ticket.class})
public interface ProjectionTicket {
    public Long getId();
    public String getNomClient();
    public double getPrix();
    public int getCodePayement();
    public boolean getReserve();
    public Place getPlace();
}
