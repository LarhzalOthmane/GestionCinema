package com.Cinema.repositories;

import com.Cinema.entities.Cinema;

import org.springframework.data.jpa.repository.JpaRepository;
// @RepositoryRestResource
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

}
