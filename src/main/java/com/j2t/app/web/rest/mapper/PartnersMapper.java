package com.j2t.app.web.rest.mapper;

import com.j2t.app.domain.*;
import com.j2t.app.web.rest.dto.PartnersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Partners and its DTO PartnersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PartnersMapper {

    PartnersDTO partnersToPartnersDTO(Partners partners);

    Partners partnersDTOToPartners(PartnersDTO partnersDTO);
}
