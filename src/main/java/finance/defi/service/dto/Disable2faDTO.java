package finance.defi.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link finance.defi.domain.Asset} entity.
 */
public class Disable2faDTO implements Serializable {

    @NotNull
    private String password;

    @NotNull
    private String code;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        Disable2faDTO that = (Disable2faDTO) o;
        return Objects.equals(password, that.password) &&
            Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, code);
    }

    @Override
    public String toString() {
        return "Disable2faDTO{" +
            "password='" + password + '\'' +
            ", code='" + code + '\'' +
            '}';
    }
}
