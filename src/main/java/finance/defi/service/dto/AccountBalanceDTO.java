package finance.defi.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import finance.defi.domain.enumeration.BalanceType;

/**
 * A DTO for the {@link finance.defi.domain.AccountBalance} entity.
 */
public class AccountBalanceDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal balanceAmount;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    @NotNull
    private BalanceType balanceType;


    private Long assetId;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountBalanceDTO accountBalanceDTO = (AccountBalanceDTO) o;
        if (accountBalanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountBalanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountBalanceDTO{" +
            "id=" + getId() +
            ", balanceAmount=" + getBalanceAmount() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", balanceType='" + getBalanceType() + "'" +
            ", asset=" + getAssetId() +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
