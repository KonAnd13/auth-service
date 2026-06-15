package org.pharmacy.controller;

import org.pharmacy.dto.PharmacyInfoDto;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/info")
public interface PharmacyInfoController {

    @PostMapping("/{pharmacyId}")
    PharmacyInfoDto getPharmacyInfo(@PathVariable UUID pharmacyId,
                                    @RequestBody String token);
}
