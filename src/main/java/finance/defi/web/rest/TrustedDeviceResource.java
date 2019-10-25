package finance.defi.web.rest;

import finance.defi.service.TrustedDeviceService;
import finance.defi.web.rest.errors.BadRequestAlertException;
import finance.defi.service.dto.TrustedDeviceDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

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

//    @PostMapping("/trusted-devices")
//    public ResponseEntity<TrustedDeviceDTO> createTrustedDevice(@Valid @RequestBody TrustedDeviceDTO trustedDeviceDTO) throws URISyntaxException {
//        log.debug("REST request to save TrustedDevice : {}", trustedDeviceDTO);
//        if (trustedDeviceDTO.getId() != null) {
//            throw new BadRequestAlertException("A new trustedDevice cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        TrustedDeviceDTO result = trustedDeviceService.save(trustedDeviceDTO);
//        return ResponseEntity.created(new URI("/api/trusted-devices/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }

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
