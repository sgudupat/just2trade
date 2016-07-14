package com.j2t.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.j2t.app.domain.Webinars;
import com.j2t.app.service.WebinarsService;
import com.j2t.app.web.rest.util.HeaderUtil;

import io.swagger.annotations.ApiOperation;

import com.j2t.app.web.rest.dto.WebinarsDTO;
import com.j2t.app.web.rest.mapper.WebinarsMapper;
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
 * REST controller for managing Webinars.
 */
@RestController
@RequestMapping("/api")
public class WebinarsResource {

    private final Logger log = LoggerFactory.getLogger(WebinarsResource.class);

    @Inject
    private WebinarsService webinarsService;

    @Inject
    private WebinarsMapper webinarsMapper;

    /**
     * POST  /webinarss -> Create a new webinars.
     */
    @RequestMapping(value = "/webinars",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WebinarsDTO> createWebinars(@RequestBody WebinarsDTO webinarsDTO) throws URISyntaxException {
        log.debug("REST request to save Webinars : {}", webinarsDTO);
        if (webinarsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("webinars", "idexists", "A new webinars cannot already have an ID")).body(null);
        }
        WebinarsDTO result = webinarsService.save(webinarsDTO);
        return ResponseEntity.created(new URI("/api/webinars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("webinars", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /webinarss -> Updates an existing webinars.
     */
    @ApiOperation(hidden = true, value = "")
    @RequestMapping(value = "/webinarss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WebinarsDTO> updateWebinars(@RequestBody WebinarsDTO webinarsDTO) throws URISyntaxException {
        log.debug("REST request to update Webinars : {}", webinarsDTO);
        if (webinarsDTO.getId() == null) {
            return createWebinars(webinarsDTO);
        }
        WebinarsDTO result = webinarsService.save(webinarsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("webinars", webinarsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /webinarss -> get all the webinarss.
     */
    @RequestMapping(value = "/webinars",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<WebinarsDTO> getAllWebinarss() {
        log.debug("REST request to get all Webinarss");
        return webinarsService.findAll();
            }

    /**
     * GET  /webinarss/:id -> get the "id" webinars.
     */
    @ApiOperation(hidden = true, value = "")
    @RequestMapping(value = "/webinarss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WebinarsDTO> getWebinars(@PathVariable Long id) {
        log.debug("REST request to get Webinars : {}", id);
        WebinarsDTO webinarsDTO = webinarsService.findOne(id);
        return Optional.ofNullable(webinarsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /webinarss/:id -> delete the "id" webinars.
     */
    @ApiOperation(hidden = true, value = "")
    @RequestMapping(value = "/webinarss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWebinars(@PathVariable Long id) {
        log.debug("REST request to delete Webinars : {}", id);
        webinarsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("webinars", id.toString())).build();
    }
}
