package com.Cinema.repositories;

import com.Cinema.entities.Projection;

import org.springframework.data.jpa.repository.JpaRepository;

// @RepositoryRestResource
public interface ProjectionRepository extends JpaRepository<Projection, Long> {

}
