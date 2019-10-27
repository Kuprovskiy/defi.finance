package finance.defi.service.impl;

import finance.defi.domain.AddressBook;
import finance.defi.domain.User;
import finance.defi.repository.AddressBookRepository;
import finance.defi.service.AddressBookService;
import finance.defi.service.UserService;
import finance.defi.service.dto.AddressBookDTO;
import finance.defi.service.dto.AddressBookFindDTO;
import finance.defi.service.mapper.AddressBookMapper;
import finance.defi.web.rest.errors.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AddressBook}.
 */
@Service
@Transactional
public class AddressBookServiceImpl implements AddressBookService {

    private final Logger log = LoggerFactory.getLogger(AddressBookServiceImpl.class);

    private final AddressBookRepository addressBookRepository;

    private final AddressBookMapper addressBookMapper;

    private final UserService userService;

    public AddressBookServiceImpl(AddressBookRepository addressBookRepository,
                                  AddressBookMapper addressBookMapper,
                                  UserService userService) {
        this.addressBookRepository = addressBookRepository;
        this.addressBookMapper = addressBookMapper;
        this.userService = userService;
    }

    @Override
    public void saveAll(List<AddressBookDTO> addressBookList) {

        // find current user
        User currentUser = userService.getUserWithAuthorities().orElseThrow(
            () -> new EntityNotFoundException("User not found")
        );

        ListIterator<AddressBookDTO> listIterator = addressBookList.listIterator();
        while(listIterator.hasNext()) {
            AddressBookDTO addressBook = listIterator.next();
            if (addressBook.getId() == null) {
                save(addressBook, currentUser);
            }
        }
    }

    @Transactional(readOnly = true)
    public Optional<AddressBookDTO> findOneByPhone(AddressBookFindDTO addressBookFindDTO) {
        log.debug("Request to get AddressBook : {}", addressBookFindDTO);

        // find current user
        User currentUser = userService.getUserWithAuthorities().orElseThrow(
            () -> new EntityNotFoundException("User no found")
        );

        return addressBookRepository.findOneByUserAndPhoneNumber(
            currentUser, addressBookFindDTO.getPhoneNumber()).map(addressBookMapper::toDto);
    }

    private void save(AddressBookDTO addressBookDTO, User currentUser) {
        log.debug("Request to save AddressBook : {}", addressBookDTO);

        // check if already exists in db
        Optional<AddressBook> existingAddressBook = addressBookRepository.findOneByUserAndPhoneNumber(
            currentUser, addressBookDTO.getPhoneNumber());
        if (existingAddressBook.isPresent()) {
            return;
        }

        // find friend user
        Optional<User> friendUser = userService.getUserByEmailOrPhone(
            addressBookDTO.getEmail(), addressBookDTO.getPhoneNumber());
        AddressBook addressBook = addressBookMapper.toEntity(addressBookDTO);
        addressBook.setUser(currentUser);
        if (friendUser.isPresent() && friendUser != null) {
            if (!friendUser.get().getId().equals(currentUser.getId())) {
                addressBook.setFriend(friendUser.get());
            }
        }
        addressBookRepository.save(addressBook);
    }
}
