package org.mcgilford.proyectoa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mcgilford.proyectoa.dto.ProductResponse;
import org.mcgilford.proyectoa.entity.Product;
import org.mcgilford.proyectoa.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
@Tag(name = "Products Service", description = "Obtain a list of product by keyword")
public class ProductController {
    private final ProductService productService;
    @GetMapping ("/search")
    @Operation(summary = "Obtain a list of possible results", description = "Endpoint to obtain similar attributes with a word")
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestParam String query){
        //ResponseEntity<Product> responseEntity = new ResponseEntity<Product>();
        List<ProductResponse> products = productService.getProducts(query);
        return ResponseEntity.ok(products);
    }
    @GetMapping ("/search/{keyword}")
    @Operation(summary = "Obtain the first result", description = "Endpoint to obtain the fist result similar")
    public ResponseEntity<Product> getProduct(@PathVariable String keyword){
        //ResponseEntity<Product> responseEntity = new ResponseEntity<Product>();
        Product product = productService.getProduct(keyword);
        return ResponseEntity.ok(product);
    }


    @PostMapping
    @Operation(summary = "Create a new product", description = "Register a new values in the database")
    public ResponseEntity<Product> saveProduct(@RequestBody  Product product){
         return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
    }
}
