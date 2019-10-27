package finance.defi.service.mapper;

import finance.defi.domain.*;
import finance.defi.service.dto.AddressBookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AddressBook} and its DTO {@link AddressBookDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AddressBookMapper extends EntityMapper<AddressBookDTO, AddressBook> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "friend.id", target = "friendId")
    @Mapping(source = "friend.login", target = "friendLogin")
    AddressBookDTO toDto(AddressBook addressBook);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "friendId", target = "friend")
    AddressBook toEntity(AddressBookDTO addressBookDTO);

    default AddressBook fromId(Long id) {
        if (id == null) {
            return null;
        }
        AddressBook addressBook = new AddressBook();
        addressBook.setId(id);
        return addressBook;
    }
}
