package com.Cinema.repositories;

import com.Cinema.entities.Film;

import org.springframework.data.jpa.repository.JpaRepository;

// @RepositoryRestResource
public interface FilmRepository extends JpaRepository<Film, Long> {

}
