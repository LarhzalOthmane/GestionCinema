package com.Cinema.repositories;

import com.Cinema.entities.Categorie;

import org.springframework.data.jpa.repository.JpaRepository;

// @RepositoryRestResource
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}