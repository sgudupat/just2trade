package com.j2t.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.j2t.app.domain.Partners;
import com.j2t.app.service.PartnersService;
import com.j2t.app.web.rest.util.HeaderUtil;
import com.j2t.app.web.rest.dto.PartnersDTO;
import com.j2t.app.web.rest.mapper.PartnersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Partners.
 */
@RestController
@RequestMapping("/api")
public class PartnersResource {

    private final Logger log = LoggerFactory.getLogger(PartnersResource.class);

    @Inject
    private PartnersService partnersService;

    @Inject
    private PartnersMapper partnersMapper;

    /**
     * POST  /partnerss -> Create a new partners.
     */
    @RequestMapping(value = "/partners",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PartnersDTO> createPartners(@RequestBody PartnersDTO partnersDTO) throws URISyntaxException {
        log.debug("REST request to save Partners : {}", partnersDTO);
        if (partnersDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("partners", "idexists", "A new partners cannot already have an ID")).body(null);
        }
        PartnersDTO result = partnersService.save(partnersDTO);
        return ResponseEntity.created(new URI("/api/partnerss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("partners", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partnerss -> Updates an existing partners.
     */
    @RequestMapping(value = "/partnerss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PartnersDTO> updatePartners(@RequestBody PartnersDTO partnersDTO) throws URISyntaxException {
        log.debug("REST request to update Partners : {}", partnersDTO);
        if (partnersDTO.getId() == null) {
            return createPartners(partnersDTO);
        }
        PartnersDTO result = partnersService.save(partnersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("partners", partnersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partnerss -> get all the partnerss.
     */
    @RequestMapping(value = "/partnerss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<PartnersDTO> getAllPartnerss() {
        log.debug("REST request to get all Partnerss");
        return partnersService.findAll();
            }

    /**
     * GET  /partnerss/:id -> get the "id" partners.
     */
    @RequestMapping(value = "/partnerss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PartnersDTO> getPartners(@PathVariable Long id) {
        log.debug("REST request to get Partners : {}", id);
        PartnersDTO partnersDTO = partnersService.findOne(id);
        return Optional.ofNullable(partnersDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /partnerss/:id -> delete the "id" partners.
     */
    @RequestMapping(value = "/partnerss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePartners(@PathVariable Long id) {
        log.debug("REST request to delete Partners : {}", id);
        partnersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("partners", id.toString())).build();
    }
}
