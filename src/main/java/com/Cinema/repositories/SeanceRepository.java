package com.Cinema.repositories;

import com.Cinema.entities.Seance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeanceRepository extends JpaRepository<Seance, Long> {

}
