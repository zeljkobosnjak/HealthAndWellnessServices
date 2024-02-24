package org.healthandwellness.healthandwellnessservices.specification;

import org.healthandwellness.healthandwellnessservices.model.ServiceModel;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

/**
 * specifications for querying ServiceModel entities
 * construct dynamic queries based on specific conditions
 */
public class ServiceSpecification {

    public static Specification<ServiceModel> descriptionContains(String description) {
        return (root, query, criteriaBuilder) -> {
            if (description == null || description.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            // apply a LIKE condition
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("serviceDescription")), "%" + description.toLowerCase() + "%");
        };
    }

    // TODO:: additional specification to filter by a boolean attribute (e.g., isActive)
    public static Specification<ServiceModel> isActive(boolean isActive) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);
    }
}
