package com.j2t.app.service.impl;

import com.j2t.app.domain.Webinars;
import com.j2t.app.repository.WebinarsRepository;
import com.j2t.app.service.MailService;
import com.j2t.app.service.WebinarsService;
import com.j2t.app.web.rest.dto.WebinarsDTO;
import com.j2t.app.web.rest.mapper.WebinarsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Webinars.
 */
@Service
@Transactional
public class WebinarsServiceImpl implements WebinarsService {

    private final Logger log = LoggerFactory.getLogger(WebinarsServiceImpl.class);

    @Inject
    private WebinarsRepository webinarsRepository;

    @Inject
    private WebinarsMapper webinarsMapper;

    @Inject
    private MailService mailService;

    /**
     * Save a webinars.
     *
     * @return the persisted entity
     */
    public WebinarsDTO save(WebinarsDTO webinarsDTO) {
        log.debug("Request to save Webinars : {}", webinarsDTO);
        Webinars webinars = webinarsMapper.webinarsDTOToWebinars(webinarsDTO);
        webinars = webinarsRepository.save(webinars);
        String subject = "Webinar requested!";
        mailService.sendEmail(subject, webinars.mailContent());
        WebinarsDTO result = webinarsMapper.webinarsToWebinarsDTO(webinars);
        return result;
    }

    /**
     * get all the webinarss.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WebinarsDTO> findAll() {
        log.debug("Request to get all Webinarss");
        List<WebinarsDTO> result = webinarsRepository.findAll().stream()
            .map(webinarsMapper::webinarsToWebinarsDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * get one webinars by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WebinarsDTO findOne(Long id) {
        log.debug("Request to get Webinars : {}", id);
        Webinars webinars = webinarsRepository.findOne(id);
        WebinarsDTO webinarsDTO = webinarsMapper.webinarsToWebinarsDTO(webinars);
        return webinarsDTO;
    }

    /**
     * delete the  webinars by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Webinars : {}", id);
        webinarsRepository.delete(id);
    }
}
