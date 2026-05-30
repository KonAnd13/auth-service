package org.pharmacy.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class CredentialsDto {

    UUID id;
    UUID pharmacyId;
    String token;
    Boolean active;
    LocalDate expiredDate;
    LocalDate updatedDate;
}
