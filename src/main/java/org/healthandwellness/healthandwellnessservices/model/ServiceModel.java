package org.healthandwellness.healthandwellnessservices.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "service")
public class ServiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "service_description", nullable = false)
    private String serviceDescription;

    @ManyToMany(mappedBy = "services")
    @JsonBackReference
    private Set<ServiceProviderModel> providers = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        this.id = id;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        if (serviceDescription == null || serviceDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Service description cannot be empty");
        }
        this.serviceDescription = serviceDescription;
    }

    public Set<ServiceProviderModel> getProviders() {
        return providers;
    }

    // helper for bidirectional relationship
    public void addProvider(ServiceProviderModel provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Provider cannot be null");
        }
        this.providers.add(provider);
        provider.getServices().add(this);
    }

    public void removeProvider(ServiceProviderModel provider) {
        if (!this.providers.contains(provider)) {
            throw new IllegalArgumentException("Provider does not exist in this service");
        }
        this.providers.remove(provider);
        provider.getServices().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceModel)) return false;
        ServiceModel that = (ServiceModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
