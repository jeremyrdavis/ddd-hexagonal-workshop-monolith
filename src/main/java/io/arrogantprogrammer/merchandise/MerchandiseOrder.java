package io.arrogantprogrammer.merchandise;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "merchandise_orders")
public class MerchandiseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attendee_id")
    private Long attendeeId;

    @Column(name = "t_shirt_size")
    private String tShirtSize;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    public MerchandiseOrder() {
        this.createdAt = ZonedDateTime.now();
        this.orderStatus = "PENDING";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getTShirtSize() {
        return tShirtSize;
    }

    public void setTShirtSize(String tShirtSize) {
        this.tShirtSize = tShirtSize;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 