package finance.defi.web.rest;

import finance.defi.DefiApp;
import finance.defi.domain.TrustedDevice;
import finance.defi.domain.User;
import finance.defi.repository.TrustedDeviceRepository;
import finance.defi.service.TrustedDeviceService;
import finance.defi.service.dto.TrustedDeviceDTO;
import finance.defi.service.mapper.TrustedDeviceMapper;
import finance.defi.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static finance.defi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TrustedDeviceResource} REST controller.
 */
@SpringBootTest(classes = DefiApp.class)
public class TrustedDeviceResourceIT {

    private static final String DEFAULT_DEVICE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_OS = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_OS = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_AT = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TRUSTED = false;
    private static final Boolean UPDATED_TRUSTED = true;

    @Autowired
    private TrustedDeviceRepository trustedDeviceRepository;

    @Autowired
    private TrustedDeviceMapper trustedDeviceMapper;

    @Autowired
    private TrustedDeviceService trustedDeviceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTrustedDeviceMockMvc;

    private TrustedDevice trustedDevice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrustedDeviceResource trustedDeviceResource = new TrustedDeviceResource(trustedDeviceService);
        this.restTrustedDeviceMockMvc = MockMvcBuilders.standaloneSetup(trustedDeviceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrustedDevice createEntity(EntityManager em) {
        TrustedDevice trustedDevice = new TrustedDevice()
            .device(DEFAULT_DEVICE)
            .deviceVersion(DEFAULT_DEVICE_VERSION)
            .deviceOs(DEFAULT_DEVICE_OS)
            .deviceDetails(DEFAULT_DEVICE_DETAILS)
            .location(DEFAULT_LOCATION)
            .createdAt(DEFAULT_CREATED_AT)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .trusted(DEFAULT_TRUSTED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        trustedDevice.setUser(user);
        return trustedDevice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrustedDevice createUpdatedEntity(EntityManager em) {
        TrustedDevice trustedDevice = new TrustedDevice()
            .device(UPDATED_DEVICE)
            .deviceVersion(UPDATED_DEVICE_VERSION)
            .deviceOs(UPDATED_DEVICE_OS)
            .deviceDetails(UPDATED_DEVICE_DETAILS)
            .location(UPDATED_LOCATION)
            .createdAt(UPDATED_CREATED_AT)
            .ipAddress(UPDATED_IP_ADDRESS)
            .trusted(UPDATED_TRUSTED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        trustedDevice.setUser(user);
        return trustedDevice;
    }

    @BeforeEach
    public void initTest() {
        trustedDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllTrustedDevices() throws Exception {
        // Initialize the database
        trustedDeviceRepository.saveAndFlush(trustedDevice);

        // Get all the trustedDeviceList
        restTrustedDeviceMockMvc.perform(get("/api/trusted-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trustedDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].device").value(hasItem(DEFAULT_DEVICE.toString())))
            .andExpect(jsonPath("$.[*].deviceVersion").value(hasItem(DEFAULT_DEVICE_VERSION.toString())))
            .andExpect(jsonPath("$.[*].deviceOs").value(hasItem(DEFAULT_DEVICE_OS.toString())))
            .andExpect(jsonPath("$.[*].deviceDetails").value(hasItem(DEFAULT_DEVICE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].trusted").value(hasItem(DEFAULT_TRUSTED.booleanValue())));
    }

    @Test
    @Transactional
    public void getNonExistingTrustedDevice() throws Exception {
        // Get the trustedDevice
        restTrustedDeviceMockMvc.perform(get("/api/trusted-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteTrustedDevice() throws Exception {
        // Initialize the database
        trustedDeviceRepository.saveAndFlush(trustedDevice);

        int databaseSizeBeforeDelete = trustedDeviceRepository.findAll().size();

        // Delete the trustedDevice
        restTrustedDeviceMockMvc.perform(delete("/api/trusted-devices/{id}", trustedDevice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrustedDevice> trustedDeviceList = trustedDeviceRepository.findAll();
        assertThat(trustedDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrustedDevice.class);
        TrustedDevice trustedDevice1 = new TrustedDevice();
        trustedDevice1.setId(1L);
        TrustedDevice trustedDevice2 = new TrustedDevice();
        trustedDevice2.setId(trustedDevice1.getId());
        assertThat(trustedDevice1).isEqualTo(trustedDevice2);
        trustedDevice2.setId(2L);
        assertThat(trustedDevice1).isNotEqualTo(trustedDevice2);
        trustedDevice1.setId(null);
        assertThat(trustedDevice1).isNotEqualTo(trustedDevice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrustedDeviceDTO.class);
        TrustedDeviceDTO trustedDeviceDTO1 = new TrustedDeviceDTO();
        trustedDeviceDTO1.setId(1L);
        TrustedDeviceDTO trustedDeviceDTO2 = new TrustedDeviceDTO();
        assertThat(trustedDeviceDTO1).isNotEqualTo(trustedDeviceDTO2);
        trustedDeviceDTO2.setId(trustedDeviceDTO1.getId());
        assertThat(trustedDeviceDTO1).isEqualTo(trustedDeviceDTO2);
        trustedDeviceDTO2.setId(2L);
        assertThat(trustedDeviceDTO1).isNotEqualTo(trustedDeviceDTO2);
        trustedDeviceDTO1.setId(null);
        assertThat(trustedDeviceDTO1).isNotEqualTo(trustedDeviceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trustedDeviceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trustedDeviceMapper.fromId(null)).isNull();
    }
}
