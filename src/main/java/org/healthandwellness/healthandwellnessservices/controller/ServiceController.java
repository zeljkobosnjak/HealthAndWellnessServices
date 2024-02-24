package org.healthandwellness.healthandwellnessservices.controller;

import org.healthandwellness.healthandwellnessservices.model.ServiceModel;
import org.healthandwellness.healthandwellnessservices.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceModel>> getAllServices() {
        List<ServiceModel> services = serviceService.findAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceModel> getServiceById(@PathVariable Integer id) {
        ServiceModel service = serviceService.findById(id);
        return ResponseEntity.ok(service);
    }

    @PostMapping
    public ResponseEntity<ServiceModel> createOrUpdateService(@RequestBody ServiceModel serviceModel) {
        ServiceModel savedService = serviceService.createOrUpdateService(serviceModel);
        return ResponseEntity.ok(savedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer id) {
        serviceService.deleteService(id);
        // if successful, respond with HTTP 204
        return ResponseEntity.noContent().build();
    }
}
