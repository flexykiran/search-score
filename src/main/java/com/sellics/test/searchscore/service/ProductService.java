package com.sellics.test.searchscore.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sellics.test.searchscore.model.Product;

@Service("productService")
public class ProductService {

	private List<Product> products;
	private static final int MAX_SCORE = 100;
	private static final int TOP_10 = 10;
	
	/**
	 * default constructor.
	 * Initialize the productList.
	 */
	public ProductService() {
		this.setProducts(new ArrayList<Product>());
	}
	
	
	/**
	 * Service method to get the product based on the search key.
	 * If the keyword exists in the productList then return the product increasing the rank by one
	 * Else create a new product with the keyword and rank as 1.
	 * @param searchKey
	 * @return Product
	 */
	public Product getProduct(String searchKey) {
		return addProduct(searchKey);
	}
	
	/**
	 * Service method to get the products list based on the search key.
	 * If the keyword exists in the productList then return the product increasing the rank by one
	 * Else create a new product with the keyword and rank as 1.
	 * @param searchKey
	 * @return
	 */
	public List<Product> getProducts(String searchKey) {
		return listProduct(searchKey);
	}
	
	
	private List<Product> listProduct(String searchKey) {
		
		//Add the searchKey to existing products list
		addProduct(searchKey);
		//Grab the candidateList in the existing list searchKey
		return getCandidateList(searchKey);
	}

	/**
	 * To add the product to the productList, first check the product exists?
	 * If product doesn't exists then add the product with rank as 1, 
	 * If the product exists, then grab the product from the list and increment the rank of the product.
	 * 
	 * @param searchKey
	 * @return
	 */
	private Product addProduct(String searchKey) { 
		Product product = null;
		Product tempProduct = null;

		//Grab the actual product if exists
		List<Product> matchingProducts = this.getProducts().stream()
					.filter(x -> x.getKeyword().equalsIgnoreCase(searchKey))
					.collect(Collectors.toList());
		
		if(null != matchingProducts && !matchingProducts.isEmpty()) {
			tempProduct = matchingProducts.get(0);
			product = tempProduct; // Only one product is expected
			if(MAX_SCORE != tempProduct.getScore()) {
				product.setScore(tempProduct.getScore() + 1);
			}
		} else {
			product = newProduct(searchKey);
		}
			
		this.getProducts().remove(tempProduct);
		this.getProducts().add(product);
		
		return product;
	}

	/**
	 * Returns top 10 searchKeys matching with the searchKey and 
	 * in the sorted descending order order of the rank.
	 * 
	 * @param searchKey
	 * @return list<product>
	 */
	private List<Product> getCandidateList(String searchKey) {
		return this.getProducts().stream()
		.filter(x -> x.getKeyword().startsWith(searchKey))
		.sorted(Comparator.comparing(Product::getScore).reversed()).limit(TOP_10)
		.collect(Collectors.toList());
	}

	/**
	 * @param searchKey
	 * @return
	 */
	private Product newProduct(String searchKey) {
		Product product = new Product();
		product.setKeyword(searchKey);
		product.setScore(1);
		return product;
	}

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
