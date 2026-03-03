package com.example.demo.Controller;

import com.example.demo.Model.Bus;
import com.example.demo.Model.BusLog;
import com.example.demo.service.BusService;
import com.example.demo.util.QRCodeGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bus")
public class BusController {

    private final BusService busService;

    // Replace with your deployed backend URL here
    private static final String BACKEND_URL = "https://bus-attendance.onrender.com";

    public BusController(BusService busService) {
        this.busService = busService;
    }

    // Create Bus + Generate QR
    @PostMapping("/create")
    public Map<String, Object> createBus(@RequestBody Map<String, String> req) throws Exception {
        Bus bus = busService.createBus(req.get("busNumber"), req.get("route"));

        // QR code points to deployed backend URL
        String qrValue = BACKEND_URL + "/api/bus/scan/manual/" + bus.getBusId() + "/ENTRY";
        byte[] qrImage = QRCodeGenerator.generateQRCode(qrValue, 250, 250);
        String qrBase64 = Base64.getEncoder().encodeToString(qrImage);

        Map<String, Object> resp = new HashMap<>();
        resp.put("busId", bus.getBusId());
        resp.put("qrValue", qrValue);
        resp.put("qrBase64", qrBase64);
        return resp;
    }

    // Manual scan with ENTRY/EXIT action
    @PostMapping("/scan/manual/{busId}/{action}")
    public BusLog scanManual(@PathVariable Long busId, @PathVariable String action) {
        return busService.scanBus(busId, action);
    }
}