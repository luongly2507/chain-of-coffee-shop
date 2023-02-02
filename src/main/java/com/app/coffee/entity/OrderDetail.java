package com.app.coffee.entity;

import com.app.coffee.entity.key.OrderDetailId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name = "orders_detail")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId orderDetailId;
    
    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    
    @ManyToOne
    @MapsId("orders_id")
    @JoinColumn(name = "orders_id")
    private Order order;

    @Column
    @Min(0)
    private int quantity;
    
    @Column(nullable = true)
    private String ice;

    @Column(nullable = true)
    private String sugar;
}
