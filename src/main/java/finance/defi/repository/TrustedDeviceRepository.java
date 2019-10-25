package finance.defi.repository;

import finance.defi.domain.TrustedDevice;
import finance.defi.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the TrustedDevice entity.
 */
@Repository
public interface TrustedDeviceRepository extends JpaRepository<TrustedDevice, Long> {

    @Query("select trustedDevice from TrustedDevice trustedDevice where trustedDevice.user.login = ?#{principal.username}")
    List<TrustedDevice> findByUserIsCurrentUser();

    List<TrustedDevice> findByUserAndDeviceAndDeviceOsAndTrustedAndAndCreatedAtAfter(
        User user,
        String device,
        String deviceOs,
        Boolean trusted,
        Instant date
    );
}
