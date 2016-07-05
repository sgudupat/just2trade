package com.j2t.app.web.rest;

import com.j2t.app.Application;
import com.j2t.app.domain.Mma;
import com.j2t.app.repository.MmaRepository;
import com.j2t.app.service.MmaService;
import com.j2t.app.web.rest.dto.MmaDTO;
import com.j2t.app.web.rest.mapper.MmaMapper;

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
 * Test class for the MmaResource REST controller.
 *
 * @see MmaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MmaResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_MIDDLE_NAME = "AAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBB";
    private static final String DEFAULT_MOBILE = "AAAAA";
    private static final String UPDATED_MOBILE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    @Inject
    private MmaRepository mmaRepository;

    @Inject
    private MmaMapper mmaMapper;

    @Inject
    private MmaService mmaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMmaMockMvc;

    private Mma mma;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MmaResource mmaResource = new MmaResource();
        ReflectionTestUtils.setField(mmaResource, "mmaService", mmaService);
        ReflectionTestUtils.setField(mmaResource, "mmaMapper", mmaMapper);
        this.restMmaMockMvc = MockMvcBuilders.standaloneSetup(mmaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mma = new Mma();
        mma.setFirstName(DEFAULT_FIRST_NAME);
        mma.setLastName(DEFAULT_LAST_NAME);
        mma.setMiddleName(DEFAULT_MIDDLE_NAME);
        mma.setMobile(DEFAULT_MOBILE);
        mma.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createMma() throws Exception {
        int databaseSizeBeforeCreate = mmaRepository.findAll().size();

        // Create the Mma
        MmaDTO mmaDTO = mmaMapper.mmaToMmaDTO(mma);

        restMmaMockMvc.perform(post("/api/mmas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mmaDTO)))
                .andExpect(status().isCreated());

        // Validate the Mma in the database
        List<Mma> mmas = mmaRepository.findAll();
        assertThat(mmas).hasSize(databaseSizeBeforeCreate + 1);
        Mma testMma = mmas.get(mmas.size() - 1);
        assertThat(testMma.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMma.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMma.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testMma.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testMma.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllMmas() throws Exception {
        // Initialize the database
        mmaRepository.saveAndFlush(mma);

        // Get all the mmas
        restMmaMockMvc.perform(get("/api/mmas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mma.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getMma() throws Exception {
        // Initialize the database
        mmaRepository.saveAndFlush(mma);

        // Get the mma
        restMmaMockMvc.perform(get("/api/mmas/{id}", mma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mma.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMma() throws Exception {
        // Get the mma
        restMmaMockMvc.perform(get("/api/mmas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMma() throws Exception {
        // Initialize the database
        mmaRepository.saveAndFlush(mma);

		int databaseSizeBeforeUpdate = mmaRepository.findAll().size();

        // Update the mma
        mma.setFirstName(UPDATED_FIRST_NAME);
        mma.setLastName(UPDATED_LAST_NAME);
        mma.setMiddleName(UPDATED_MIDDLE_NAME);
        mma.setMobile(UPDATED_MOBILE);
        mma.setEmail(UPDATED_EMAIL);
        MmaDTO mmaDTO = mmaMapper.mmaToMmaDTO(mma);

        restMmaMockMvc.perform(put("/api/mmas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mmaDTO)))
                .andExpect(status().isOk());

        // Validate the Mma in the database
        List<Mma> mmas = mmaRepository.findAll();
        assertThat(mmas).hasSize(databaseSizeBeforeUpdate);
        Mma testMma = mmas.get(mmas.size() - 1);
        assertThat(testMma.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMma.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMma.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testMma.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testMma.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteMma() throws Exception {
        // Initialize the database
        mmaRepository.saveAndFlush(mma);

		int databaseSizeBeforeDelete = mmaRepository.findAll().size();

        // Get the mma
        restMmaMockMvc.perform(delete("/api/mmas/{id}", mma.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Mma> mmas = mmaRepository.findAll();
        assertThat(mmas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
