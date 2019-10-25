package finance.defi.repository;

import finance.defi.domain.TrustedDevice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the TrustedDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrustedDeviceRepository extends JpaRepository<TrustedDevice, Long> {

    @Query("select trustedDevice from TrustedDevice trustedDevice where trustedDevice.user.login = ?#{principal.username}")
    List<TrustedDevice> findByUserIsCurrentUser();

}
