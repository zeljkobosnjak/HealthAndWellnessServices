package org.healthandwellness.healthandwellnessservices.controller;

import org.healthandwellness.healthandwellnessservices.model.ServiceProviderModel;
import org.healthandwellness.healthandwellnessservices.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ServiceProviderController {

    private final ServiceProviderService providerService;

    @Autowired
    public ServiceProviderController(ServiceProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceProviderModel>> getAllProviders() {
        List<ServiceProviderModel> providers = providerService.findAllProviders();
        return ResponseEntity.ok(providers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceProviderModel> getProviderById(@PathVariable Integer id) {
        ServiceProviderModel provider = providerService.findProviderById(id);
        return ResponseEntity.ok(provider);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ServiceProviderModel> createOrUpdateProvider(@RequestBody ServiceProviderModel serviceProviderModel) {
        ServiceProviderModel savedProvider = providerService.saveOrUpdateServiceProvider(serviceProviderModel);
        return ResponseEntity.ok(savedProvider);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Integer id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}
