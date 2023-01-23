package com.app.coffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.coffee.entity.ProductDetail;
import com.app.coffee.entity.ProductDetailId;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, ProductDetailId>{
    
}
