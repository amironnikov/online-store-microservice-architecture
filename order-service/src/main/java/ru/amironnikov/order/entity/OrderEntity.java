package ru.amironnikov.order.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.amironnikov.order.dto.Order;
import ru.amironnikov.order.dto.OrderDto;
import ru.amironnikov.order.dto.OrderProduct;
import ru.amironnikov.order.kafka.OrderStatus;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Table("orders")
public class OrderEntity implements Order {
    @Id
    private UUID id;

    @Column("user_id")
    private UUID userId;

    @Column("zip_code")
    private int zipCode;

    @Column("status")
    private OrderStatus status;

    @CreatedDate
    @Column("created")
    private LocalDateTime created;

    @LastModifiedDate
    @Column("updated")
    private LocalDateTime updated;

    @Column("total_cost")
    private int totalCost;

    @Column("total_weight")
    private double totalWeight;

    public OrderEntity() {

    }

    public OrderEntity(OrderDto order, int totalCost, double totalWeight) {
        this(
                order.userId(),
                order.zipCode(),
                OrderStatus.CREATED,
                totalCost,
                totalWeight
        );
    }

    public OrderEntity(UUID userId,
                       int zipCode, OrderStatus status,
                       int totalCost, double totalWeight) {
        this.userId = userId;
        this.zipCode = zipCode;
        this.status = status;
        this.totalCost = totalCost;
        this.totalWeight = totalWeight;
    }

    @Override
    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID userId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public int zipCode() {
        return zipCode;
    }

    @Override
    public LocalDateTime created() {
        return created;
    }

    @Override
    public LocalDateTime updated() {
        return updated;
    }

    @Override
    public OrderStatus status() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public List<OrderProduct> products() {
        return Collections.emptyList();
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public int totalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public double totalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }
}
