package finance.defi.service.dto;

import finance.defi.domain.Asset;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class AccountTotalBalanceDTO implements Serializable {

    private BigDecimal totalBalance;

    private Asset asset;

    private BigDecimal earned;

    private List<BalanceDTO> balances;

    private List<BalanceDTO> supplyBalances;

    private List<BalanceDTO> borrowBalances;


    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public BigDecimal getEarned() {
        return earned;
    }

    public void setEarned(BigDecimal earned) {
        this.earned = earned;
    }

    public List<BalanceDTO> getBalances() {
        return balances;
    }

    public void setBalances(List<BalanceDTO> balances) {
        this.balances = balances;
    }

    public List<BalanceDTO> getSupplyBalances() {
        return supplyBalances;
    }

    public void setSupplyBalances(List<BalanceDTO> supplyBalances) {
        this.supplyBalances = supplyBalances;
    }

    public List<BalanceDTO> getBorrowBalances() {
        return borrowBalances;
    }

    public void setBorrowBalances(List<BalanceDTO> borrowBalances) {
        this.borrowBalances = borrowBalances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountTotalBalanceDTO that = (AccountTotalBalanceDTO) o;
        return Objects.equals(totalBalance, that.totalBalance) &&
            Objects.equals(asset, that.asset) &&
            Objects.equals(earned, that.earned) &&
            Objects.equals(balances, that.balances) &&
            Objects.equals(supplyBalances, that.supplyBalances) &&
            Objects.equals(borrowBalances, that.borrowBalances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalBalance, asset, earned, balances, supplyBalances, borrowBalances);
    }

    @Override
    public String toString() {
        return "AccountTotalBalanceDTO{" +
            "totalBalance=" + totalBalance +
            ", asset='" + asset + '\'' +
            ", earned=" + earned +
            ", balances=" + balances +
            ", supplyBalances=" + supplyBalances +
            ", borrowBalances=" + borrowBalances +
            '}';
    }
}
