package org.healthandwellness.healthandwellnessservices.repository;

import org.healthandwellness.healthandwellnessservices.model.ServiceProviderModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * repository interface for ServiceProviderModel
 * @EntityGraph for retrieval of services, addressing the N+1 issue
 */
@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProviderModel, Integer> {

    /**
     * overrides the default findAll to include an entity graph
     */
    @Override
    @EntityGraph(attributePaths = {"services"})
    List<ServiceProviderModel> findAll();
}
