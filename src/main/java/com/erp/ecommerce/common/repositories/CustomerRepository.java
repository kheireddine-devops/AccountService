package com.erp.ecommerce.common.repositories;

import com.erp.ecommerce.common.models.entities.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @EntityGraph(attributePaths = {"account"})
    List<Customer> findAll();
}
