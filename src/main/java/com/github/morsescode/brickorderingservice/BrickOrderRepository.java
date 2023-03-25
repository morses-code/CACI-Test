package com.github.morsescode.brickorderingservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrickOrderRepository extends JpaRepository<BrickOrder, Long> {
    BrickOrder findByOrderReference(String orderReference);
}
