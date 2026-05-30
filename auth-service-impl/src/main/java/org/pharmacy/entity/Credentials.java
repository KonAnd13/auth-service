package org.pharmacy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "credentials")
@Getter
@Setter
@NoArgsConstructor
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pharmacy_id", nullable = false, unique = true)
    private UUID pharmacyId;

    @Column(nullable = false, length = 32)
    private String token;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDate updatedDate;
}
