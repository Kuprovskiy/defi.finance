package finance.defi.web.rest;

import finance.defi.service.TrustedDeviceService;
import finance.defi.service.dto.TrustedDeviceDTO;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * REST controller for managing {@link finance.defi.domain.TrustedDevice}.
 */
@RestController
@RequestMapping("/api")
public class TrustedDeviceResource {

    private final Logger log = LoggerFactory.getLogger(TrustedDeviceResource.class);

    private static final String ENTITY_NAME = "trustedDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrustedDeviceService trustedDeviceService;

    public TrustedDeviceResource(TrustedDeviceService trustedDeviceService) {
        this.trustedDeviceService = trustedDeviceService;
    }

    /**
     * {@code GET  /trusted-devices} : get all the trustedDevices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trustedDevices in body.
     */
    @GetMapping("/trusted-devices")
    public ResponseEntity<List<TrustedDeviceDTO>> getAllTrustedDevices(Pageable pageable) {
        log.debug("REST request to get a page of TrustedDevices");
        Page<TrustedDeviceDTO> page = trustedDeviceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code DELETE  /trusted-devices/:id} : delete the "id" trustedDevice.
     *
     * @param id the id of the trustedDeviceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trusted-devices/{id}")
    public ResponseEntity<Void> deleteTrustedDevice(@PathVariable Long id) {
        log.debug("REST request to delete TrustedDevice : {}", id);
        trustedDeviceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
