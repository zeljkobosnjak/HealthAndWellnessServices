package org.healthandwellness.healthandwellnessservices.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "service_provider")
public class ServiceProviderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "provider_name", nullable = false)
    private String providerName;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "provider_service",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    @JsonManagedReference
    private Set<ServiceModel> services = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        if (providerName == null || providerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Provider name cannot be empty");
        }
        this.providerName = providerName;
    }

    public Set<ServiceModel> getServices() {
        return services;
    }

    public void setServices(Set<ServiceModel> services) {
        this.services = services;
    }

    // helper for adding a service
    public void addService(ServiceModel service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        this.services.add(service);
        service.getProviders().add(this);
    }

    // helper for removing a service
    public void removeService(ServiceModel service) {
        if (!this.services.contains(service)) {
            throw new IllegalArgumentException("Service does not exist in this provider");
        }
        this.services.remove(service);
        service.getProviders().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceProviderModel)) return false;
        ServiceProviderModel that = (ServiceProviderModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
