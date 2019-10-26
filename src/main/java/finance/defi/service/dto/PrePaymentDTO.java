package finance.defi.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class PrePaymentDTO implements Serializable {

    private String code;

    public PrePaymentDTO(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PrePaymentDTO{" +
            "code='" + code + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrePaymentDTO that = (PrePaymentDTO) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
