package org.healthandwellness.healthandwellnessservices.service;

import org.healthandwellness.healthandwellnessservices.model.ServiceModel;
import org.healthandwellness.healthandwellnessservices.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Transactional(readOnly = true)
    public List<ServiceModel> findAllServices() {
        return serviceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ServiceModel findById(Integer id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + id + " not found"));
    }

    @Transactional
    public ServiceModel createOrUpdateService(ServiceModel serviceModel) {
        return serviceRepository.save(serviceModel);
    }

    @Transactional
    public void deleteService(Integer id) {
        try {
            serviceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException("Service with ID " + id + " not found and could not be deleted");
        }
    }
}
