package com.Cinema.repositories;

import com.Cinema.entities.Place;

import org.springframework.data.jpa.repository.JpaRepository;

// @RepositoryRestResource
public interface PlaceRepository extends JpaRepository<Place, Long> {

}
