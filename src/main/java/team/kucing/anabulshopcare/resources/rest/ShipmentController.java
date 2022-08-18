package team.kucing.anabulshopcare.resources.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.ShipmentRequest;
import team.kucing.anabulshopcare.entity.Shipment;
import team.kucing.anabulshopcare.service.impl.ShipmentServiceImpl;

import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "10. Shipment Controller")
public class ShipmentController {

    private final ShipmentServiceImpl shipmentService;

    @GetMapping("/shipments")
    @Operation(summary = "Get ALL Shipmnent [SELLER | BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER') or hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> getAllShipment() {
        return this.shipmentService.findAllShipping();
    }

    @PostMapping("/shipments/add")
    @Operation(summary = "Add Shipment Price [SELLER]")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> addShipment(@RequestBody ShipmentRequest shipmentRequest) {
        var addShipment = this.shipmentService.createShipment(shipmentRequest);
        log.info("New Shipment Added");
        return addShipment;
    }
}





