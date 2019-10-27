package finance.defi.web.rest;

import finance.defi.service.AddressBookService;
import finance.defi.service.dto.AddressBookFindDTO;
import finance.defi.service.dto.AddressBookListDTO;
import finance.defi.web.rest.errors.BadRequestAlertException;
import finance.defi.service.dto.AddressBookDTO;

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
 * REST controller for managing {@link finance.defi.domain.AddressBook}.
 */
@RestController
@RequestMapping("/api")
public class AddressBookResource {

    private final Logger log = LoggerFactory.getLogger(AddressBookResource.class);

    private static final String ENTITY_NAME = "addressBook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressBookService addressBookService;

    public AddressBookResource(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @PostMapping("/address-books")
    public ResponseEntity<AddressBookDTO> createAddressBook(@Valid @RequestBody AddressBookListDTO addressBookListDTO) {
        log.debug("REST request to save AddressBookList : {}", addressBookListDTO);

        addressBookService.saveAll(addressBookListDTO.getAddressBookList());
        return ResponseEntity.created(null).headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, null)).build();
    }

    /**
     * {@code GET  /address-books/:phoneNumber} : get the "id" addressBook.
     *
     * @param addressBookFindDTO the phoneNumber of the addressBookDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressBookDTO, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/address-books/find")
    public ResponseEntity<AddressBookDTO> getAddressBook(@Valid @RequestBody AddressBookFindDTO addressBookFindDTO) {
        log.debug("REST request to get AddressBook : {}", addressBookFindDTO);
        Optional<AddressBookDTO> addressBookDTO = addressBookService.findOneByPhone(addressBookFindDTO);
        return ResponseUtil.wrapOrNotFound(addressBookDTO);
    }
}
