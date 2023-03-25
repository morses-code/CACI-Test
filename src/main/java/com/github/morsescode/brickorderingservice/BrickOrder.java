package com.github.morsescode.brickorderingservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BrickOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderReference;

    @Column(nullable = false)
    private int bricksOrdered;

    @Column(nullable = false)
    private Boolean isDispatched;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public int getBricksOrdered() {
        return bricksOrdered;
    }

    public void setBricksOrdered(int bricksOrdered) {
        this.bricksOrdered = bricksOrdered;
    }

    public Boolean getIsDispatched() {
        return isDispatched;
    }

    public void setIsDispatched(Boolean isDispatched) {
        this.isDispatched = isDispatched;
    }

}
