package com.example.demo.repository;

import com.example.demo.Model.BusLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusLogRepository extends JpaRepository<BusLog, Long> {
    Optional<BusLog> findTopByBus_BusIdOrderByScanTimeDesc(Long busId);
}