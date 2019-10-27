package finance.defi.repository;

import finance.defi.domain.AddressBook;
import finance.defi.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AddressBook entity.
 */
@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {

    @Query("select addressBook from AddressBook addressBook where addressBook.user.login = ?#{principal.username}")
    List<AddressBook> findByUserIsCurrentUser();

    @Query("select addressBook from AddressBook addressBook where addressBook.friend.login = ?#{principal.username}")
    List<AddressBook> findByFriendIsCurrentUser();

    Optional<AddressBook> findOneByUserAndPhoneNumber(User user, String phone);

}
