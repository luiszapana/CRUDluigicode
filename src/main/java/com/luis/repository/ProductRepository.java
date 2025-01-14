package com.luis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luis.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	Optional<Product> findByName(String name);
	boolean existsByName(String name);
}
