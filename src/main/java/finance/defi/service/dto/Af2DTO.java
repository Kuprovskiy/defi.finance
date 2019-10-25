package finance.defi.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link finance.defi.domain.Asset} entity.
 */
public class Af2DTO implements Serializable {

    private String message;

    private String code;

    private String error;


    public Af2DTO(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Af2DTO af2DTO = (Af2DTO) o;
        return Objects.equals(message, af2DTO.message) &&
            Objects.equals(code, af2DTO.code) &&
            Objects.equals(error, af2DTO.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, code, error);
    }

    @Override
    public String toString() {
        return "Af2DTO{" +
            "message='" + message + '\'' +
            ", code='" + code + '\'' +
            ", error='" + error + '\'' +
            '}';
    }
}
