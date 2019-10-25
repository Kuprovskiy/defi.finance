package finance.defi.service.impl;

import finance.defi.service.TrustedDeviceService;
import finance.defi.domain.TrustedDevice;
import finance.defi.repository.TrustedDeviceRepository;
import finance.defi.service.dto.TrustedDeviceDTO;
import finance.defi.service.mapper.TrustedDeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TrustedDevice}.
 */
@Service
@Transactional
public class TrustedDeviceServiceImpl implements TrustedDeviceService {

    private final Logger log = LoggerFactory.getLogger(TrustedDeviceServiceImpl.class);

    private final TrustedDeviceRepository trustedDeviceRepository;

    private final TrustedDeviceMapper trustedDeviceMapper;

    public TrustedDeviceServiceImpl(TrustedDeviceRepository trustedDeviceRepository, TrustedDeviceMapper trustedDeviceMapper) {
        this.trustedDeviceRepository = trustedDeviceRepository;
        this.trustedDeviceMapper = trustedDeviceMapper;
    }

    /**
     * Save a trustedDevice.
     *
     * @param trustedDeviceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TrustedDeviceDTO save(TrustedDeviceDTO trustedDeviceDTO) {
        log.debug("Request to save TrustedDevice : {}", trustedDeviceDTO);
        TrustedDevice trustedDevice = trustedDeviceMapper.toEntity(trustedDeviceDTO);
        trustedDevice = trustedDeviceRepository.save(trustedDevice);
        return trustedDeviceMapper.toDto(trustedDevice);
    }

    /**
     * Get all the trustedDevices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TrustedDeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrustedDevices");
        return trustedDeviceRepository.findAll(pageable)
            .map(trustedDeviceMapper::toDto);
    }

    /**
     * Delete the trustedDevice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrustedDevice : {}", id);
        trustedDeviceRepository.deleteById(id);
    }
}
