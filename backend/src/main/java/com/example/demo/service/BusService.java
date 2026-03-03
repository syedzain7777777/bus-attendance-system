package com.example.demo.service;

import com.example.demo.Model.Bus;
import com.example.demo.Model.BusLog;
import com.example.demo.repository.BusLogRepository;
import com.example.demo.repository.BusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BusService {

    private final BusRepository busRepository;
    private final BusLogRepository busLogRepository;

    public BusService(BusRepository busRepository, BusLogRepository busLogRepository) {
        this.busRepository = busRepository;
        this.busLogRepository = busLogRepository;
    }

    // Create a new bus
    public Bus createBus(String busNumber, String route) {
        Bus bus = new Bus();
        bus.setBusNumber(busNumber);
        bus.setRoute(route);
        bus.setCreatedAt(new java.util.Date());
        return busRepository.save(bus);
    }

    // Log a manual scan with user-provided action (ENTRY/EXIT)
    public BusLog scanBus(Long busId, String action) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        if (action == null) {
            throw new RuntimeException("Action cannot be null. Use ENTRY or EXIT.");
        }

        action = action.trim().toUpperCase();
        // remove spaces & normalize
        System.out.println(action);

        if (!action.equals("ENTRY") && !action.equals("EXIT")) {
            throw new RuntimeException("Invalid action. Use ENTRY or EXIT."+action);
        }

        BusLog log = new BusLog();
        log.setBus(bus);
        log.setAction(action);
        log.setScanTime(LocalDateTime.now());

        return busLogRepository.save(log);
    }

}