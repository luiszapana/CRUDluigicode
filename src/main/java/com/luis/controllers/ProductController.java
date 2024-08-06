package com.luis.controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luis.dto.Message;
import com.luis.dto.ProductDto;
import com.luis.entities.Product;
import com.luis.services.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Product>> list(){
		List<Product> list = productService.list();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Integer id){
		if (!productService.existsById(id))
			return new ResponseEntity<>(new Message("No existe"), HttpStatus.NOT_FOUND);
		Product product = productService.getOne(id).get();
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@GetMapping("/detailname/{name}")
	public ResponseEntity<?> getByName(@PathVariable("name") String name){
		if (!productService.existsByName(name))
			return new ResponseEntity<>(new Message("No existe"), HttpStatus.NOT_FOUND);
		Product product = productService.getByName(name).get();
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody ProductDto productDto){
		if (StringUtils.isBlank(productDto.getName()))
			return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		if (productDto.getPrice() == null || productDto.getPrice() < 0)
			return new ResponseEntity<>(new Message("El precio no puede ser negativo"), HttpStatus.BAD_REQUEST);
		if (productService.existsByName(productDto.getName()))
			return new ResponseEntity<>(new Message("El nombre ya existe"), HttpStatus.BAD_REQUEST);

		Product product = new Product(productDto.getName(), productDto.getPrice());
		productService.save(product);
		return new ResponseEntity<>(new Message("Producto creado"), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ProductDto productDto){
		if (!productService.existsById(id))
			return new ResponseEntity<>(new Message("No existe"), HttpStatus.NOT_FOUND);
		
		if (productService.existsByName(productDto.getName()) && productService.getByName(productDto.getName()).get().getId() != id)
			return new ResponseEntity<>(new Message("El nombre ya existe"), HttpStatus.BAD_REQUEST);
		if (StringUtils.isBlank(productDto.getName()))
			return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		if (productDto.getPrice() == null || productDto.getPrice() < 0)
			return new ResponseEntity<>(new Message("El precio no puede ser negativo"), HttpStatus.BAD_REQUEST);
		

		Product product = productService.getOne(id).get();
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		productService.save(product);
		return new ResponseEntity<>(new Message("Producto actualizado"), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id){
		if (!productService.existsById(id))
			return new ResponseEntity<>(new Message("No existe"), HttpStatus.NOT_FOUND);
		productService.delete(id);
		return new ResponseEntity<>(new Message("Producto eliminado"), HttpStatus.OK);
		
	}
}
