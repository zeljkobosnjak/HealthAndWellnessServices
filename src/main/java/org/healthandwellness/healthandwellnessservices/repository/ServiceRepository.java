package org.healthandwellness.healthandwellnessservices.repository;

import org.healthandwellness.healthandwellnessservices.model.ServiceModel;
import org.healthandwellness.healthandwellnessservices.model.projection.ServiceDescriptionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceModel, Integer> {

    // TODO: try projections
    @Query("select s.id as id, s.serviceDescription as serviceDescription from ServiceModel s")
    List<ServiceDescriptionProjection> findAllServiceDescriptions();
}
