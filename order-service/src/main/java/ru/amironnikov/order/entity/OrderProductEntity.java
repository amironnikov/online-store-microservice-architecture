package ru.amironnikov.order.entity;


import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.amironnikov.common.OrderProduct;


import java.util.UUID;

@Table("order_product")
public class OrderProductEntity implements OrderProduct {

    @Column("order_id")
    private UUID orderId;

    @Column("product_id")
    private UUID productId;

    private int quantity;

    public OrderProductEntity() {
    }

    public OrderProductEntity(UUID orderId, UUID productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public UUID product() {
        return productId;
    }

    @Override
    public int quantity() {
        return quantity;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
