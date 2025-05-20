package org.example.choihyukwithclass.repository;

import org.example.choihyukwithclass.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long>, BusinessRepositoryQueryDsl {
}
