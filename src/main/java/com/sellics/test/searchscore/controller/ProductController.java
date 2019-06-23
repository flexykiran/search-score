package com.sellics.test.searchscore.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sellics.test.searchscore.model.Product;
import com.sellics.test.searchscore.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/estimate", method = RequestMethod.GET)
	public Product getProduct(@RequestParam String searchKey) throws IOException {
		return productService.getProduct(searchKey);
	}
	
	@RequestMapping(value="/estimate/products", method = RequestMethod.GET)
	public List<Product> getProducts(@RequestParam String searchKey) throws IOException {
		return productService.getProducts(searchKey);
	}
}
