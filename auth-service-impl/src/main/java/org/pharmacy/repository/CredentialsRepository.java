package org.pharmacy.repository;

import org.pharmacy.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface CredentialsRepository extends JpaRepository<Credentials, UUID> {

    Optional<Credentials> findByPharmacyId(UUID pharmacyId);

    @Modifying
    @Query(value = "UPDATE Credentials c SET c.active = false WHERE c.expiredDate <= :date AND c.active = true")
    int revokeExpiredTokens(@Param("date") LocalDate date);
}
