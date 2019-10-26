package finance.defi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import finance.defi.domain.enumeration.BalanceType;

/**
 * A AccountBalance.
 */
@Entity
@Table(name = "account_balance")
public class AccountBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "balance_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal balanceAmount;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "balance_type", nullable = false)
    private BalanceType balanceType;

    @ManyToOne(optional = false)
    @NotNull
    private Asset asset;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("accountBalances")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public AccountBalance balanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
        return this;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public AccountBalance createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public AccountBalance updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public AccountBalance balanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
        return this;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public Asset getAsset() {
        return asset;
    }

    public AccountBalance asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public User getUser() {
        return user;
    }

    public AccountBalance user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountBalance)) {
            return false;
        }
        return id != null && id.equals(((AccountBalance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
            "id=" + getId() +
            ", balanceAmount=" + getBalanceAmount() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", balanceType='" + getBalanceType() + "'" +
            "}";
    }
}
