package com.geekbrains.productservice.controller;

import com.geekbrains.productservice.entity.CustomPage;
import com.geekbrains.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/products")
public interface ProductClient {

    @GetMapping("/product")
    List<Product> getAllProducts();

    @GetMapping("/product/{id}")
     Product getProductById(@PathVariable("id") Long id);

    @GetMapping(value = "/product/page")
    CustomPage<Product> getProductsWithPagingAndFiltering(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam(value = "word", required = false) String word,
            @RequestParam(value = "min", required = false) Double min,
            @RequestParam(value = "max", required = false) Double max);
}
