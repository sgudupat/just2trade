package com.j2t.app.web.rest;

import com.j2t.app.Application;
import com.j2t.app.domain.Webinars;
import com.j2t.app.repository.WebinarsRepository;
import com.j2t.app.service.WebinarsService;
import com.j2t.app.web.rest.dto.WebinarsDTO;
import com.j2t.app.web.rest.mapper.WebinarsMapper;

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
 * Test class for the WebinarsResource REST controller.
 *
 * @see WebinarsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class WebinarsResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBB";
    private static final String DEFAULT_MOBILE = "AAAAA";
    private static final String UPDATED_MOBILE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    @Inject
    private WebinarsRepository webinarsRepository;

    @Inject
    private WebinarsMapper webinarsMapper;

    @Inject
    private WebinarsService webinarsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restWebinarsMockMvc;

    private Webinars webinars;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WebinarsResource webinarsResource = new WebinarsResource();
        ReflectionTestUtils.setField(webinarsResource, "webinarsService", webinarsService);
        ReflectionTestUtils.setField(webinarsResource, "webinarsMapper", webinarsMapper);
        this.restWebinarsMockMvc = MockMvcBuilders.standaloneSetup(webinarsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        webinars = new Webinars();
        webinars.setFullName(DEFAULT_FULL_NAME);
        webinars.setMobile(DEFAULT_MOBILE);
        webinars.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createWebinars() throws Exception {
        int databaseSizeBeforeCreate = webinarsRepository.findAll().size();

        // Create the Webinars
        WebinarsDTO webinarsDTO = webinarsMapper.webinarsToWebinarsDTO(webinars);

        restWebinarsMockMvc.perform(post("/api/webinarss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(webinarsDTO)))
                .andExpect(status().isCreated());

        // Validate the Webinars in the database
        List<Webinars> webinarss = webinarsRepository.findAll();
        assertThat(webinarss).hasSize(databaseSizeBeforeCreate + 1);
        Webinars testWebinars = webinarss.get(webinarss.size() - 1);
        assertThat(testWebinars.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testWebinars.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testWebinars.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllWebinarss() throws Exception {
        // Initialize the database
        webinarsRepository.saveAndFlush(webinars);

        // Get all the webinarss
        restWebinarsMockMvc.perform(get("/api/webinarss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(webinars.getId().intValue())))
                .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getWebinars() throws Exception {
        // Initialize the database
        webinarsRepository.saveAndFlush(webinars);

        // Get the webinars
        restWebinarsMockMvc.perform(get("/api/webinarss/{id}", webinars.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(webinars.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWebinars() throws Exception {
        // Get the webinars
        restWebinarsMockMvc.perform(get("/api/webinarss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebinars() throws Exception {
        // Initialize the database
        webinarsRepository.saveAndFlush(webinars);

		int databaseSizeBeforeUpdate = webinarsRepository.findAll().size();

        // Update the webinars
        webinars.setFullName(UPDATED_FULL_NAME);
        webinars.setMobile(UPDATED_MOBILE);
        webinars.setEmail(UPDATED_EMAIL);
        WebinarsDTO webinarsDTO = webinarsMapper.webinarsToWebinarsDTO(webinars);

        restWebinarsMockMvc.perform(put("/api/webinarss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(webinarsDTO)))
                .andExpect(status().isOk());

        // Validate the Webinars in the database
        List<Webinars> webinarss = webinarsRepository.findAll();
        assertThat(webinarss).hasSize(databaseSizeBeforeUpdate);
        Webinars testWebinars = webinarss.get(webinarss.size() - 1);
        assertThat(testWebinars.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testWebinars.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testWebinars.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteWebinars() throws Exception {
        // Initialize the database
        webinarsRepository.saveAndFlush(webinars);

		int databaseSizeBeforeDelete = webinarsRepository.findAll().size();

        // Get the webinars
        restWebinarsMockMvc.perform(delete("/api/webinarss/{id}", webinars.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Webinars> webinarss = webinarsRepository.findAll();
        assertThat(webinarss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
