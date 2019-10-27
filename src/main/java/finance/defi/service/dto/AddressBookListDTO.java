package finance.defi.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class AddressBookListDTO implements Serializable {

    private List<AddressBookDTO> addressBookList;

    public List<AddressBookDTO> getAddressBookList() {
        return addressBookList;
    }

    public void setAddressBookList(List<AddressBookDTO> addressBookList) {
        this.addressBookList = addressBookList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBookListDTO that = (AddressBookListDTO) o;
        return Objects.equals(addressBookList, that.addressBookList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressBookList);
    }

    @Override
    public String toString() {
        return "AddressBookListDTO{" +
            "addressBookList=" + addressBookList +
            '}';
    }
}
