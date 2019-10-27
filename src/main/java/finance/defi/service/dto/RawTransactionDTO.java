package finance.defi.service.dto;

import finance.defi.domain.enumeration.TransactionType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class RawTransactionDTO implements Serializable {

    @NotNull
    private String tx;

    @NotNull
    private TransactionType type;

    @NotNull
    private String asset;

    private String code;


    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawTransactionDTO that = (RawTransactionDTO) o;
        return Objects.equals(tx, that.tx) &&
            type == that.type &&
            Objects.equals(asset, that.asset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tx, type, asset);
    }

    @Override
    public String toString() {
        return "RawTransactionDTO{" +
            "tx='" + tx + '\'' +
            ", type=" + type +
            ", asset='" + asset + '\'' +
            '}';
    }
}
