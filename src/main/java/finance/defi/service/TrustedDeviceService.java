package finance.defi.service;

import finance.defi.domain.User;
import finance.defi.service.dto.TrustedDeviceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Service Interface for managing {@link finance.defi.domain.TrustedDevice}.
 */
public interface TrustedDeviceService {

    /**
     * Save a trustedDevice.
     *
     * @param trustedDeviceDTO the entity to save.
     * @return the persisted entity.
     */
    TrustedDeviceDTO save(TrustedDeviceDTO trustedDeviceDTO);

    /**
     * Get all the trustedDevices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrustedDeviceDTO> findAll(Pageable pageable);

    /**
     * Delete the "id" trustedDevice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void validateTrustDevice(HttpServletRequest request, User currentUser);
}
