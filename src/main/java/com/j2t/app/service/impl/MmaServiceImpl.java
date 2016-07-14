package com.j2t.app.service.impl;

import com.j2t.app.service.MailService;
import com.j2t.app.service.MmaService;
import com.j2t.app.domain.Mma;
import com.j2t.app.repository.MmaRepository;
import com.j2t.app.web.rest.dto.MmaDTO;
import com.j2t.app.web.rest.mapper.MmaMapper;
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
 * Service Implementation for managing Mma.
 */
@Service
@Transactional
public class MmaServiceImpl implements MmaService{

    private final Logger log = LoggerFactory.getLogger(MmaServiceImpl.class);
    
    @Inject
    private MmaRepository mmaRepository;
    
    @Inject
    private MmaMapper mmaMapper;
    
    @Inject
    private MailService mailService;
    
    /**
     * Save a mma.
     * @return the persisted entity
     */
    public MmaDTO save(MmaDTO mmaDTO) {
        log.debug("Request to save Mma : {}", mmaDTO);
        Mma mma = mmaMapper.mmaDTOToMma(mmaDTO);
        mma = mmaRepository.save(mma);
        String subject = "A MMA Requested";
        mailService.sendEmail(subject, mma.mailContent());
        MmaDTO result = mmaMapper.mmaToMmaDTO(mma);
        return result;
    }

    /**
     *  get all the mmas.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<MmaDTO> findAll() {
        log.debug("Request to get all Mmas");
        List<MmaDTO> result = mmaRepository.findAll().stream()
            .map(mmaMapper::mmaToMmaDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one mma by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MmaDTO findOne(Long id) {
        log.debug("Request to get Mma : {}", id);
        Mma mma = mmaRepository.findOne(id);
        MmaDTO mmaDTO = mmaMapper.mmaToMmaDTO(mma);
        return mmaDTO;
    }

    /**
     *  delete the  mma by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Mma : {}", id);
        mmaRepository.delete(id);
    }
}
