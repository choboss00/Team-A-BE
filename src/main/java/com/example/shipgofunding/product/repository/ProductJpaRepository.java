package com.example.shipgofunding.product.repository;

import com.example.shipgofunding.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {
}
