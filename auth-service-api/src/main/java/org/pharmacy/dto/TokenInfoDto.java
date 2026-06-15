package org.pharmacy.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class TokenInfoDto {
    String token;
    LocalDate expiredDate;
}
