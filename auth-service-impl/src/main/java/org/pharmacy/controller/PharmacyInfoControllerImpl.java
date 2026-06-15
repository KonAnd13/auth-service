package org.pharmacy.controller;

import org.pharmacy.dto.PharmacyInfoDto;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PharmacyInfoControllerImpl implements PharmacyInfoController {

    @Override
    public PharmacyInfoDto getPharmacyInfo(UUID pharmacyId, String token) {
            return PharmacyInfoDto.builder()
                    .director("Иванов Иван Иванович")
                    .address("г. Москва, ул. Большая красная, 12")
                    .inn("111111111111")
                    .build();
    }
}
