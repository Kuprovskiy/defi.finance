package finance.defi.service.mapper;

import finance.defi.domain.TrustedDevice;
import finance.defi.service.dto.TrustedDeviceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link TrustedDevice} and its DTO {@link TrustedDeviceDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TrustedDeviceMapper extends EntityMapper<TrustedDeviceDTO, TrustedDevice> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    TrustedDeviceDTO toDto(TrustedDevice trustedDevice);

    @Mapping(source = "userId", target = "user")
    TrustedDevice toEntity(TrustedDeviceDTO trustedDeviceDTO);

    default TrustedDevice fromId(Long id) {
        if (id == null) {
            return null;
        }
        TrustedDevice trustedDevice = new TrustedDevice();
        trustedDevice.setId(id);
        return trustedDevice;
    }
}
