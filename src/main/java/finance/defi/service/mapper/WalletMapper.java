package finance.defi.service.mapper;

import finance.defi.domain.*;
import finance.defi.service.dto.WalletDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wallet} and its DTO {@link WalletDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface WalletMapper extends EntityMapper<WalletDTO, Wallet> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    WalletDTO toDto(Wallet wallet);

    @Mapping(source = "userId", target = "user")
    Wallet toEntity(WalletDTO walletDTO);

    default Wallet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wallet wallet = new Wallet();
        wallet.setId(id);
        return wallet;
    }
}
