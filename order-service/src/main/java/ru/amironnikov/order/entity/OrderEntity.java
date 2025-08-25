package ru.amironnikov.order.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.amironnikov.common.Order;
import ru.amironnikov.common.OrderProduct;
import ru.amironnikov.common.dto.order.OrderDto;

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

    @CreatedDate
    @Column("created")
    private LocalDateTime created;

    @LastModifiedDate
    @Column("updated")
    private LocalDateTime updated;

    public OrderEntity() {

    }

    public OrderEntity(OrderDto order) {
        this(
                order.id(),
                order.userId(),
                order.zipCode()
        );
    }

    public OrderEntity(UUID id, UUID userId, int zipCode) {
        this.id = id;
        this.userId = userId;
        this.zipCode = zipCode;
    }

    @Override
    public UUID getId() {
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
}
