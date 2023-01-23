package com.app.coffee.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductDetailId {
    @Column (name = "branch_id")
    private UUID branchId;

    @Column (name = "product_id")
    private UUID productId;

}
