package finance.defi.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link finance.defi.domain.AddressBook} entity.
 */
public class AddressBookDTO implements Serializable {

    private Long id;

    @NotNull
    private String phoneNumber;

    private String email;

    private String fullName;

    @NotNull
    private Instant invitedDate;

    @NotNull
    private Instant createdDate;


    private Long userId;

    private String userLogin;

    private Long friendId;

    private String friendLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Instant getInvitedDate() {
        return invitedDate;
    }

    public void setInvitedDate(Instant invitedDate) {
        this.invitedDate = invitedDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long userId) {
        this.friendId = userId;
    }

    public String getFriendLogin() {
        return friendLogin;
    }

    public void setFriendLogin(String userLogin) {
        this.friendLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressBookDTO addressBookDTO = (AddressBookDTO) o;
        if (addressBookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressBookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressBookDTO{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", invitedDate='" + getInvitedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", friend=" + getFriendId() +
            ", friend='" + getFriendLogin() + "'" +
            "}";
    }
}
