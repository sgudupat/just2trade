package com.j2t.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.j2t.app.domain.Mma;
import com.j2t.app.service.MmaService;
import com.j2t.app.web.rest.util.HeaderUtil;

import io.swagger.annotations.ApiOperation;

import com.j2t.app.web.rest.dto.MmaDTO;
import com.j2t.app.web.rest.mapper.MmaMapper;
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
 * REST controller for managing Mma.
 */
@RestController
@RequestMapping("/api")
public class MmaResource {

    private final Logger log = LoggerFactory.getLogger(MmaResource.class);
        
    @Inject
    private MmaService mmaService;
    
    @Inject
    private MmaMapper mmaMapper;
    
    /**
     * POST  /mmas -> Create a new mma.
     */
    @RequestMapping(value = "/mmas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MmaDTO> createMma(@RequestBody MmaDTO mmaDTO) throws URISyntaxException {
        log.debug("REST request to save Mma : {}", mmaDTO);
        if (mmaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("mma", "idexists", "A new mma cannot already have an ID")).body(null);
        }
        MmaDTO result = mmaService.save(mmaDTO);
        return ResponseEntity.created(new URI("/api/mmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mma", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mmas -> Updates an existing mma.
     */
    @ApiOperation(hidden = true, value = "")
    @RequestMapping(value = "/mmas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MmaDTO> updateMma(@RequestBody MmaDTO mmaDTO) throws URISyntaxException {
        log.debug("REST request to update Mma : {}", mmaDTO);
        if (mmaDTO.getId() == null) {
            return createMma(mmaDTO);
        }
        MmaDTO result = mmaService.save(mmaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mma", mmaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mmas -> get all the mmas.
     */
    @RequestMapping(value = "/mmas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<MmaDTO> getAllMmas() {
        log.debug("REST request to get all Mmas");
        return mmaService.findAll();
            }

    /**
     * GET  /mmas/:id -> get the "id" mma.
     */
    @ApiOperation(hidden = true, value = "")
    @RequestMapping(value = "/mmas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MmaDTO> getMma(@PathVariable Long id) {
        log.debug("REST request to get Mma : {}", id);
        MmaDTO mmaDTO = mmaService.findOne(id);
        return Optional.ofNullable(mmaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mmas/:id -> delete the "id" mma.
     */
    @ApiOperation(hidden = true, value = "")
    @RequestMapping(value = "/mmas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMma(@PathVariable Long id) {
        log.debug("REST request to delete Mma : {}", id);
        mmaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mma", id.toString())).build();
    }
}
