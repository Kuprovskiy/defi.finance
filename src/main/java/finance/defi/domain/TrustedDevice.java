package finance.defi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TrustedDevice.
 */
@Entity
@Table(name = "trusted_device")
public class TrustedDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "device", nullable = false)
    private String device;

    @NotNull
    @Column(name = "device_version", nullable = false)
    private String deviceVersion;

    @NotNull
    @Column(name = "device_os", nullable = false)
    private String deviceOs;

    @NotNull
    @Column(name = "device_details", nullable = false)
    private String deviceDetails;

    @Column(name = "location")
    private String location;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @NotNull
    @Column(name = "trusted", nullable = false)
    private Boolean trusted;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("trustedDevices")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public TrustedDevice device(String device) {
        this.device = device;
        return this;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public TrustedDevice deviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
        return this;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceOs() {
        return deviceOs;
    }

    public TrustedDevice deviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
        return this;
    }

    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }

    public String getDeviceDetails() {
        return deviceDetails;
    }

    public TrustedDevice deviceDetails(String deviceDetails) {
        this.deviceDetails = deviceDetails;
        return this;
    }

    public void setDeviceDetails(String deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

    public String getLocation() {
        return location;
    }

    public TrustedDevice location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public TrustedDevice createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public TrustedDevice ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean isTrusted() {
        return trusted;
    }

    public TrustedDevice trusted(Boolean trusted) {
        this.trusted = trusted;
        return this;
    }

    public void setTrusted(Boolean trusted) {
        this.trusted = trusted;
    }

    public User getUser() {
        return user;
    }

    public TrustedDevice user(User user) {
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
        if (!(o instanceof TrustedDevice)) {
            return false;
        }
        return id != null && id.equals(((TrustedDevice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TrustedDevice{" +
            "id=" + getId() +
            ", device='" + getDevice() + "'" +
            ", deviceVersion='" + getDeviceVersion() + "'" +
            ", deviceOs='" + getDeviceOs() + "'" +
            ", deviceDetails='" + getDeviceDetails() + "'" +
            ", location='" + getLocation() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", trusted='" + isTrusted() + "'" +
            "}";
    }
}
