package finance.defi.repository;

import finance.defi.domain.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Asset entity.
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByNameAndIsVisible(String name, Boolean isAvailable);

    Page<Asset> findByIsVisibleTrue(Pageable pageable);
}
