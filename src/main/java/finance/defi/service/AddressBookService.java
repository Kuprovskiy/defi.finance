package finance.defi.service;

import finance.defi.service.dto.AddressBookDTO;
import finance.defi.service.dto.AddressBookFindDTO;

import java.util.List;
import java.util.Optional;

public interface AddressBookService {

    void saveAll(List<AddressBookDTO> addressBookList);

    Optional<AddressBookDTO> findOneByPhone(AddressBookFindDTO addressBookFindDTO);
}
