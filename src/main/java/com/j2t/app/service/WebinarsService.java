package com.j2t.app.service;

import com.j2t.app.domain.Webinars;
import com.j2t.app.web.rest.dto.WebinarsDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Webinars.
 */
public interface WebinarsService {

    /**
     * Save a webinars.
     * @return the persisted entity
     */
    public WebinarsDTO save(WebinarsDTO webinarsDTO);

    /**
     *  get all the webinarss.
     *  @return the list of entities
     */
    public List<WebinarsDTO> findAll();

    /**
     *  get the "id" webinars.
     *  @return the entity
     */
    public WebinarsDTO findOne(Long id);

    /**
     *  delete the "id" webinars.
     */
    public void delete(Long id);
}
