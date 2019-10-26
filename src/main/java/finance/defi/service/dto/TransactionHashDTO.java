package finance.defi.service.dto;

import java.util.Objects;

public class TransactionHashDTO {

    private String transactionHash;


    public TransactionHashDTO(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionHashDTO that = (TransactionHashDTO) o;
        return Objects.equals(transactionHash, that.transactionHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionHash);
    }

    @Override
    public String toString() {
        return "TransactionHashDTO{" +
            "transactionHash='" + transactionHash + '\'' +
            '}';
    }
}
