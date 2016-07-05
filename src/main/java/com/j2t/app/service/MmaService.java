package com.j2t.app.service;

import com.j2t.app.domain.Mma;
import com.j2t.app.web.rest.dto.MmaDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Mma.
 */
public interface MmaService {

    /**
     * Save a mma.
     * @return the persisted entity
     */
    public MmaDTO save(MmaDTO mmaDTO);

    /**
     *  get all the mmas.
     *  @return the list of entities
     */
    public List<MmaDTO> findAll();

    /**
     *  get the "id" mma.
     *  @return the entity
     */
    public MmaDTO findOne(Long id);

    /**
     *  delete the "id" mma.
     */
    public void delete(Long id);
}
