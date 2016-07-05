package com.j2t.app.web.rest.mapper;

import com.j2t.app.domain.*;
import com.j2t.app.web.rest.dto.WebinarsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Webinars and its DTO WebinarsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WebinarsMapper {

    WebinarsDTO webinarsToWebinarsDTO(Webinars webinars);

    Webinars webinarsDTOToWebinars(WebinarsDTO webinarsDTO);
}
