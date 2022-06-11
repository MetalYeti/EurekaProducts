package com.geekbrains.productservice.controller;

import com.geekbrains.productservice.entity.CustomPage;
import com.geekbrains.productservice.entity.Product;
import com.geekbrains.productservice.entity.ProductImage;
import com.geekbrains.productservice.service.CategoryService;
import com.geekbrains.productservice.service.ImageSaverService;
import com.geekbrains.productservice.service.ProductService;
import com.geekbrains.productservice.specs.ProductSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController

public class ProductController implements ProductClient {
    private ProductService productService;
    private CategoryService categoryService;
    private ImageSaverService imageSaverService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setImageSaverService(ImageSaverService imageSaverService) {
        this.imageSaverService = imageSaverService;
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    public CustomPage<Product> getProductsWithPagingAndFiltering(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam(value = "word", required = false) String word,
            @RequestParam(value = "min", required = false) Double min,
            @RequestParam(value = "max", required = false) Double max) {

        Specification<Product> spec = Specification.where(null);
        StringBuilder filters = new StringBuilder();
        if (word != null) {
            spec = spec.and(ProductSpecs.titleContains(word));
            filters.append("&word=" + word);
        }
        if (min != null) {
            spec = spec.and(ProductSpecs.priceGreaterThanOrEq(min));
            filters.append("&min=" + min);
        }
        if (max != null) {
            spec = spec.and(ProductSpecs.priceLesserThanOrEq(max));
            filters.append("&max=" + max);
        }

        Page<Product> page = productService.getProductsWithPagingAndFiltering(pageNumber, pageSize, spec);
        return new CustomPage<>(page.getTotalElements(), page.getContent());
    }
}
