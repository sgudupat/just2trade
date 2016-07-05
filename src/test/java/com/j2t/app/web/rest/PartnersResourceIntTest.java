package com.j2t.app.web.rest;

import com.j2t.app.Application;
import com.j2t.app.domain.Partners;
import com.j2t.app.repository.PartnersRepository;
import com.j2t.app.service.PartnersService;
import com.j2t.app.web.rest.dto.PartnersDTO;
import com.j2t.app.web.rest.mapper.PartnersMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PartnersResource REST controller.
 *
 * @see PartnersResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PartnersResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBB";
    private static final String DEFAULT_MOBILE = "AAAAA";
    private static final String UPDATED_MOBILE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    @Inject
    private PartnersRepository partnersRepository;

    @Inject
    private PartnersMapper partnersMapper;

    @Inject
    private PartnersService partnersService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPartnersMockMvc;

    private Partners partners;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartnersResource partnersResource = new PartnersResource();
        ReflectionTestUtils.setField(partnersResource, "partnersService", partnersService);
        ReflectionTestUtils.setField(partnersResource, "partnersMapper", partnersMapper);
        this.restPartnersMockMvc = MockMvcBuilders.standaloneSetup(partnersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        partners = new Partners();
        partners.setFullName(DEFAULT_FULL_NAME);
        partners.setMobile(DEFAULT_MOBILE);
        partners.setEmail(DEFAULT_EMAIL);
        partners.setComments(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createPartners() throws Exception {
        int databaseSizeBeforeCreate = partnersRepository.findAll().size();

        // Create the Partners
        PartnersDTO partnersDTO = partnersMapper.partnersToPartnersDTO(partners);

        restPartnersMockMvc.perform(post("/api/partnerss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partnersDTO)))
                .andExpect(status().isCreated());

        // Validate the Partners in the database
        List<Partners> partnerss = partnersRepository.findAll();
        assertThat(partnerss).hasSize(databaseSizeBeforeCreate + 1);
        Partners testPartners = partnerss.get(partnerss.size() - 1);
        assertThat(testPartners.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testPartners.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testPartners.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPartners.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPartnerss() throws Exception {
        // Initialize the database
        partnersRepository.saveAndFlush(partners);

        // Get all the partnerss
        restPartnersMockMvc.perform(get("/api/partnerss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(partners.getId().intValue())))
                .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getPartners() throws Exception {
        // Initialize the database
        partnersRepository.saveAndFlush(partners);

        // Get the partners
        restPartnersMockMvc.perform(get("/api/partnerss/{id}", partners.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(partners.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartners() throws Exception {
        // Get the partners
        restPartnersMockMvc.perform(get("/api/partnerss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartners() throws Exception {
        // Initialize the database
        partnersRepository.saveAndFlush(partners);

		int databaseSizeBeforeUpdate = partnersRepository.findAll().size();

        // Update the partners
        partners.setFullName(UPDATED_FULL_NAME);
        partners.setMobile(UPDATED_MOBILE);
        partners.setEmail(UPDATED_EMAIL);
        partners.setComments(UPDATED_COMMENTS);
        PartnersDTO partnersDTO = partnersMapper.partnersToPartnersDTO(partners);

        restPartnersMockMvc.perform(put("/api/partnerss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partnersDTO)))
                .andExpect(status().isOk());

        // Validate the Partners in the database
        List<Partners> partnerss = partnersRepository.findAll();
        assertThat(partnerss).hasSize(databaseSizeBeforeUpdate);
        Partners testPartners = partnerss.get(partnerss.size() - 1);
        assertThat(testPartners.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testPartners.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPartners.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPartners.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void deletePartners() throws Exception {
        // Initialize the database
        partnersRepository.saveAndFlush(partners);

		int databaseSizeBeforeDelete = partnersRepository.findAll().size();

        // Get the partners
        restPartnersMockMvc.perform(delete("/api/partnerss/{id}", partners.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Partners> partnerss = partnersRepository.findAll();
        assertThat(partnerss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
