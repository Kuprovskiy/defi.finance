package finance.defi.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link finance.defi.domain.TrustedDevice} entity.
 */
public class TrustedDeviceDTO implements Serializable {

    private Long id;

    @NotNull
    private String device;

    @NotNull
    private String deviceVersion;

    @NotNull
    private String deviceOs;

    @NotNull
    private String deviceDetails;

    private String location;

    @NotNull
    private Instant createdAt;

    @NotNull
    private String ipAddress;

    @NotNull
    private Boolean trusted;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceOs() {
        return deviceOs;
    }

    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }

    public String getDeviceDetails() {
        return deviceDetails;
    }

    public void setDeviceDetails(String deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(Boolean trusted) {
        this.trusted = trusted;
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

        TrustedDeviceDTO trustedDeviceDTO = (TrustedDeviceDTO) o;
        if (trustedDeviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trustedDeviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrustedDeviceDTO{" +
            "id=" + getId() +
            ", device='" + getDevice() + "'" +
            ", deviceVersion='" + getDeviceVersion() + "'" +
            ", deviceOs='" + getDeviceOs() + "'" +
            ", deviceDetails='" + getDeviceDetails() + "'" +
            ", location='" + getLocation() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", trusted='" + isTrusted() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
