package finance.defi.service.mapper;

import finance.defi.domain.AccountBalance;
import finance.defi.service.dto.AccountBalanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AccountBalance} and its DTO {@link AccountBalanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {AssetMapper.class, UserMapper.class})
public interface AccountBalanceMapper extends EntityMapper<AccountBalanceDTO, AccountBalance> {

    @Mapping(source = "asset.id", target = "assetId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    AccountBalanceDTO toDto(AccountBalance accountBalance);

    @Mapping(source = "assetId", target = "asset")
    @Mapping(source = "userId", target = "user")
    AccountBalance toEntity(AccountBalanceDTO accountBalanceDTO);

    default AccountBalance fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setId(id);
        return accountBalance;
    }
}
