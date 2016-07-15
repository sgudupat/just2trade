package com.j2t.app.service.impl;

import com.j2t.app.domain.Mma;
import com.j2t.app.repository.MmaRepository;
import com.j2t.app.service.MailService;
import com.j2t.app.service.MmaService;
import com.j2t.app.web.rest.dto.MmaDTO;
import com.j2t.app.web.rest.mapper.MmaMapper;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Mma.
 */
@Service
@Transactional
public class MmaServiceImpl implements MmaService {

    private final Logger log = LoggerFactory.getLogger(MmaServiceImpl.class);

    @Inject
    private MmaRepository mmaRepository;

    @Inject
    private MmaMapper mmaMapper;

    @Inject
    private MailService mailService;

    /**
     * Save a mma.
     *
     * @return the persisted entity
     */
    public MmaDTO save(MmaDTO mmaDTO, HttpServletRequest httpServletRequest) {
        log.debug("Request to save Mma : {}", mmaDTO);
        Mma mma = mmaMapper.mmaDTOToMma(mmaDTO);
        mma = mmaRepository.save(mma);
        String subject = "A MMA Requested";
        mailService.sendEmail(subject, mma.mailContent());
        mmaServiceRequest(mma, httpServletRequest);
        MmaDTO result = mmaMapper.mmaToMmaDTO(mma);
        return result;
    }

    /**
     * get all the mmas.
     *
     * @return the list of entities
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
     * get one mma by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MmaDTO findOne(Long id) {
        log.debug("Request to get Mma : {}", id);
        Mma mma = mmaRepository.findOne(id);
        MmaDTO mmaDTO = mmaMapper.mmaToMmaDTO(mma);
        return mmaDTO;
    }

    /**
     * delete the  mma by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Mma : {}", id);
        mmaRepository.delete(id);
    }

    public void mmaServiceRequest(Mma mma, HttpServletRequest httpRequest) {
        try {
            ResteasyClient client = new ResteasyClientBuilder().build();
            WebTarget webTarget = client.target("http://whotrades.tst.whotrades.net/api/internal/systems/");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            String params = buildRequest(mma, httpRequest);
            log.info("params:::" + params.toString());
            log.info("params:::" + Entity.json(params));
            Response response = invocationBuilder.post(Entity.json(params));
            log.info("Response :::" + response.getStatus());
        } catch (Exception e) {
            log.error("Exception:", e);
        }
    }

    private String buildRequest(Mma mma, HttpServletRequest httpRequest) {
        String ipAddress = null;
        String gateway = httpRequest.getHeader("VIA"); //Gateway
        ipAddress = httpRequest.getHeader("X-FORWARDED-FOR");   // proxy
        if (ipAddress == null) {
            ipAddress = httpRequest.getRemoteAddr();
        }
        String userAgent = httpRequest.getHeader("User-Agent");
        System.out.println("GateWay::" + gateway);
        System.out.println("Ip address::" + ipAddress);

        StringBuilder request = new StringBuilder();
        request.append("  { ");
        request.append("      \"id\": 1, ");
        request.append("      \"jsonrpc\" : \"2.0\", ");
        request.append("      \"method\": \"getInterestsSystem.getApi.interestAdvertisement\", ");
        request.append("      \"params\": [ ");
        request.append("            { ");
        request.append("              \"title\": \"J2T India\", ");
        request.append("              \"account_type\": 2, ");
        request.append("              \"market\": \"mct\", ");
        request.append("              \"firm\": \"J2T\", ");
        request.append("              \"source_id\": \"\" ");
        request.append("           }, ");
        request.append("            { ");
        request.append("              \"identification\": { ");
        request.append("                  \"person_id\": \"\", ");
        request.append("                  \"email\": \"" + mma.getEmail() + "\", ");
        request.append("                  \"phone\": \"" + "+91" + mma.getMobile() + "\", ");
        request.append("                  \"guid\": \"\", ");
        request.append("                  \"finam_id\": \"\" ");
        request.append("              }, ");
        request.append("              \"utm\": { ");
        request.append("                  \"utm_source\": \"JUST2TRADE.CO.IN\", ");
        request.append("                  \"xtm_content\": 229 ");
        request.append("              }, ");
        request.append("              \"url\": { ");
        request.append("                  \"url\": \"http://www.just2trade.co.in/index.html\", ");
        request.append("                  \"page_name\": \"index.html\" ");
        request.append("              }, ");
        request.append("              \"date\": " + Calendar.getInstance().getTime().getTime() + ", ");
        request.append("              \"source\": \"just2trade.co.in\", ");
        request.append("              \"ip\": \"" + ipAddress + "\", ");
        request.append("              \"user-agent\": \"" + userAgent + "\", ");
        request.append("              \"finam_office_id\": \"\" ");
        request.append("          }, ");
        request.append("          { ");
        request.append("              \"name\":\"" + mma.getFirstName() + "\", ");
        request.append("              \"lastname\":\"" + mma.getLastName() + "\", ");
        request.append("              \"middlename\":\"" + mma.getMiddleName() + "\", ");
        request.append("              \"birthdate\":\"1989-10-25\", ");
        request.append("              \"is_male\":true, ");
        request.append("              \"language\":\"en\" ");
        request.append("          } ");
        request.append("      ] ");
        request.append("  } ");
        System.out.println("Request:::" + request);
        return request.toString();
    }
}
