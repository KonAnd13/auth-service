package org.pharmacy.mapper;

import org.mapstruct.Mapper;
import org.pharmacy.dto.TokenInfoDto;
import org.pharmacy.entity.Credentials;

@Mapper(componentModel = "spring")
public interface TokenInfoMapper {

    TokenInfoDto toTokenInfoDto(Credentials credentials);
}
