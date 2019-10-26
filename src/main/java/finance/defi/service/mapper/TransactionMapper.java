package finance.defi.service.mapper;

import finance.defi.domain.Transaction;
import finance.defi.service.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {AssetMapper.class, UserMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(source = "asset.id", target = "assetId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "recipient.id", target = "recipientId")
    @Mapping(source = "recipient.login", target = "recipientLogin")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(source = "assetId", target = "asset")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "recipientId", target = "recipient")
    Transaction toEntity(TransactionDTO transactionDTO);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
