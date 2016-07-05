package com.j2t.app.web.rest.mapper;

import com.j2t.app.domain.*;
import com.j2t.app.web.rest.dto.MmaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mma and its DTO MmaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MmaMapper {

    MmaDTO mmaToMmaDTO(Mma mma);

    Mma mmaDTOToMma(MmaDTO mmaDTO);
}
