package com.j2t.app.service.impl;

import com.j2t.app.service.MailService;
import com.j2t.app.service.PartnersService;
import com.j2t.app.domain.Partners;
import com.j2t.app.repository.PartnersRepository;
import com.j2t.app.web.rest.dto.PartnersDTO;
import com.j2t.app.web.rest.mapper.PartnersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Partners.
 */
@Service
@Transactional
public class PartnersServiceImpl implements PartnersService{

    private final Logger log = LoggerFactory.getLogger(PartnersServiceImpl.class);
    
    @Inject
    private PartnersRepository partnersRepository;
    
    @Inject
    private PartnersMapper partnersMapper;
    
    @Inject
    private MailService mailService;
    
    /**
     * Save a partners.
     * @return the persisted entity
     */
    public PartnersDTO save(PartnersDTO partnersDTO) {
        log.debug("Request to save Partners : {}", partnersDTO);
        Partners partners = partnersMapper.partnersDTOToPartners(partnersDTO);
        partners = partnersRepository.save(partners);
        String subject = "A Partner Requested";
        mailService.sendEmail(subject, partners.mailContent());
        PartnersDTO result = partnersMapper.partnersToPartnersDTO(partners);
        return result;
    }

    /**
     *  get all the partnerss.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PartnersDTO> findAll() {
        log.debug("Request to get all Partnerss");
        List<PartnersDTO> result = partnersRepository.findAll().stream()
            .map(partnersMapper::partnersToPartnersDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one partners by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PartnersDTO findOne(Long id) {
        log.debug("Request to get Partners : {}", id);
        Partners partners = partnersRepository.findOne(id);
        PartnersDTO partnersDTO = partnersMapper.partnersToPartnersDTO(partners);
        return partnersDTO;
    }

    /**
     *  delete the  partners by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Partners : {}", id);
        partnersRepository.delete(id);
    }
}
