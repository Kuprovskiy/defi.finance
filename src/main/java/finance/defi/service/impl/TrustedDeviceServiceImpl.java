package finance.defi.service.impl;

import finance.defi.domain.User;
import finance.defi.security.SecurityUtils;
import finance.defi.service.MailService;
import finance.defi.service.TrustedDeviceService;
import finance.defi.domain.TrustedDevice;
import finance.defi.repository.TrustedDeviceRepository;
import finance.defi.service.UserService;
import finance.defi.service.dto.TrustedDeviceDTO;
import finance.defi.service.mapper.TrustedDeviceMapper;
import finance.defi.web.rest.errors.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
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

    private final UserService userService;

    private final MailService mailService;

    public TrustedDeviceServiceImpl(TrustedDeviceRepository trustedDeviceRepository,
                                    TrustedDeviceMapper trustedDeviceMapper,
                                    UserService userService,
                                    MailService mailService) {
        this.trustedDeviceRepository = trustedDeviceRepository;
        this.trustedDeviceMapper = trustedDeviceMapper;
        this.userService = userService;
        this.mailService = mailService;
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

    @Override
    public void validateTrustDevice(HttpServletRequest request, User currentUser) {

        TrustedDeviceDTO newTrustedDevice = SecurityUtils.getCurrentDevice(request, currentUser, false);
        newTrustedDevice.setCreatedAt(Instant.now().minusSeconds(2592000)); // 2592000 = 1 month

        List<TrustedDevice> trustedDevices =
            this.findByUserIsCurrentUserAndDeviceAndDeviceOsAndTrustedAndDateAfter(
                newTrustedDevice,
                currentUser);
        if (trustedDevices.size() == 0) {

            currentUser = userService.requestTrustDeviceApprove(currentUser);

            newTrustedDevice.setCreatedAt(Instant.now());
            TrustedDeviceDTO trustedDevice =
                this.save(newTrustedDevice);
            this.mailService.sendAuthorizeDeviceEmail(currentUser, trustedDevice);

            throw new EntityNotFoundException("Unauthorized device");
        }
    }

    @Transactional(readOnly = true)
    public List<TrustedDevice> findByUserIsCurrentUserAndDeviceAndDeviceOsAndTrustedAndDateAfter(
        TrustedDeviceDTO trustedDeviceDTO,
        User user
    ) {
        log.debug("Request to get TrustedDevice : {}, location: {}, ipAddress: {}, trusted: {}, date after: {}",
            trustedDeviceDTO.getDevice(), trustedDeviceDTO.getLocation(), trustedDeviceDTO.getId(), true, trustedDeviceDTO.getCreatedAt());
        return trustedDeviceRepository.findByUserAndDeviceAndDeviceOsAndTrustedAndAndCreatedAtAfter(
            user,
            trustedDeviceDTO.getDevice(),
            trustedDeviceDTO.getDeviceOs(),
            true,
            trustedDeviceDTO.getCreatedAt()
        );
    }
}
