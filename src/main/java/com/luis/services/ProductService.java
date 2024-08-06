package com.luis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luis.entities.Product;
import com.luis.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> list(){
		return productRepository.findAll();
	}
	
	public Optional<Product> getOne(Integer id){
		return productRepository.findById(id);
	}
	
	public Optional<Product> getByName(String name){
		return productRepository.findByName(name);
	}
	
	public void save(Product product) {
		productRepository.save(product);
	}
	
	public void delete(Integer id) {
		productRepository.deleteById(id);
	}
	
	public boolean existsById(Integer id) {
		return productRepository.existsById(id);
	}
	
	public boolean existsByName(String name) {
		return productRepository.existsByName(name);
	}
}
