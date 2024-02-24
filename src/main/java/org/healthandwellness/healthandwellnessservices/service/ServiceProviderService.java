package org.healthandwellness.healthandwellnessservices.service;

import org.healthandwellness.healthandwellnessservices.model.ServiceModel;
import org.healthandwellness.healthandwellnessservices.model.ServiceProviderModel;
import org.healthandwellness.healthandwellnessservices.repository.ServiceProviderRepository;
import org.healthandwellness.healthandwellnessservices.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ServiceProviderService {

    private final ServiceProviderRepository providerRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceProviderService(ServiceProviderRepository providerRepository, ServiceRepository serviceRepository) {
        this.providerRepository = providerRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional(readOnly = true)
    public List<ServiceProviderModel> findAllProviders() {
        return providerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ServiceProviderModel findProviderById(Integer id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Provider with ID " + id + " not found"));
    }

    //TODO: find solution for "detached entity passed to persist" (JPA is not actively managing it)
    @Transactional
    public ServiceProviderModel saveOrUpdateServiceProvider(ServiceProviderModel serviceProviderModel) {
        Set<ServiceModel> managedServices = new HashSet<>();
        for (ServiceModel service : serviceProviderModel.getServices()) {
            if (service.getId() != null) {
                ServiceModel managedService = serviceRepository.findById(service.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Service with ID " + service.getId() + " not found"));
                managedServices.add(managedService);
            } else {
                managedServices.add(serviceRepository.save(service));
            }
        }
        serviceProviderModel.setServices(managedServices);

        return providerRepository.save(serviceProviderModel);
    }

    @Transactional
    public void deleteProvider(Integer id) {
        if (!providerRepository.existsById(id)) {
            throw new EntityNotFoundException("Provider with ID " + id + " does not exist and cannot be deleted");
        }
        providerRepository.deleteById(id);
    }
}
