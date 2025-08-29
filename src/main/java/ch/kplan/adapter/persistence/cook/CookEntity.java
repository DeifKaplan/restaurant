package ch.kplan.adapter.persistence.cook;

import jakarta.persistence.*;

@Entity
@Table(name = "cook")
public class CookEntity {
    @Id
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    public CookEntity() {}

    public CookEntity(Long id, Long orderId) {
        this.id = id;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}

