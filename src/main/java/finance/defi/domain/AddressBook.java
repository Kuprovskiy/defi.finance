package finance.defi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A AddressBook.
 */
@Entity
@Table(name = "address_book")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @Column(name = "invited_date", nullable = false)
    private Instant invitedDate;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("addressBooks")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("addressBooks")
    private User friend;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AddressBook phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public AddressBook email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public AddressBook fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Instant getInvitedDate() {
        return invitedDate;
    }

    public AddressBook invitedDate(Instant invitedDate) {
        this.invitedDate = invitedDate;
        return this;
    }

    public void setInvitedDate(Instant invitedDate) {
        this.invitedDate = invitedDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public AddressBook createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public AddressBook user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public AddressBook friend(User user) {
        this.friend = user;
        return this;
    }

    public void setFriend(User user) {
        this.friend = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressBook)) {
            return false;
        }
        return id != null && id.equals(((AddressBook) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AddressBook{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", invitedDate='" + getInvitedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
