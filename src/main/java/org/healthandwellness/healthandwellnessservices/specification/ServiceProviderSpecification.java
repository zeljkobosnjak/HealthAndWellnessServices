package org.healthandwellness.healthandwellnessservices.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.healthandwellness.healthandwellnessservices.model.ServiceModel;
import org.healthandwellness.healthandwellnessservices.model.ServiceProviderModel;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceProviderSpecification {

    /**
     * specification to filter service providers by name
     * case-insensitive search for providers whose names contain the given substring
     */
    public static Specification<ServiceProviderModel> providerNameContains(String providerName) {
        return (root, query, criteriaBuilder) -> {
            if (providerName == null || providerName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("providerName")), "%" + providerName.toLowerCase() + "%");
        };
    }

    /**
     * specification to filter service providers by services they offer
     * a search for providers offering a service with a description that contains the given substring
     */
    public static Specification<ServiceProviderModel> hasService(String serviceDescription) {
        return (root, query, criteriaBuilder) -> {
            if (serviceDescription == null || serviceDescription.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            query.distinct(true);

            Join<ServiceProviderModel, ServiceModel> servicesJoin = root.join("services");

            Subquery<ServiceModel> serviceSubquery = query.subquery(ServiceModel.class);
            Root<ServiceModel> serviceRoot = serviceSubquery.from(ServiceModel.class);
            serviceSubquery.select(serviceRoot)
                    .where(
                            criteriaBuilder.equal(serviceRoot, servicesJoin),
                            criteriaBuilder.like(serviceRoot.get("serviceDescription"), "%" + serviceDescription + "%")
                    );

            return criteriaBuilder.exists(serviceSubquery);
        };
    }

    /**
     * combines multiple search criteria into a single specification
     * filtering by both provider name and service description
     */
    public static Specification<ServiceProviderModel> byCriteria(String providerName, String serviceDescription) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (providerName != null && !providerName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("providerName")), "%" + providerName.toLowerCase() + "%"));
            }
            if (serviceDescription != null && !serviceDescription.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.join("services").get("serviceDescription"), "%" + serviceDescription + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
