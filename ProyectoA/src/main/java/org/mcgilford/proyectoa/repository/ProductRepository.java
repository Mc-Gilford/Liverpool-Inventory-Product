package org.mcgilford.proyectoa.repository;

import org.mcgilford.proyectoa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository <Product, String>{
    //private ProductRepository productRepository;
}
