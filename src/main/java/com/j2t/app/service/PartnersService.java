package com.j2t.app.service;

import com.j2t.app.domain.Partners;
import com.j2t.app.web.rest.dto.PartnersDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Partners.
 */
public interface PartnersService {

    /**
     * Save a partners.
     * @return the persisted entity
     */
    public PartnersDTO save(PartnersDTO partnersDTO);

    /**
     *  get all the partnerss.
     *  @return the list of entities
     */
    public List<PartnersDTO> findAll();

    /**
     *  get the "id" partners.
     *  @return the entity
     */
    public PartnersDTO findOne(Long id);

    /**
     *  delete the "id" partners.
     */
    public void delete(Long id);
}
