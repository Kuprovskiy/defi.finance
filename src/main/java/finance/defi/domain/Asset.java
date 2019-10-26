package finance.defi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Asset.
 */
@Entity
@Table(name = "asset")
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "long_name", nullable = false)
    private String longName;

    @Column(name = "market_cap")
    private Long marketCap;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "supply")
    private Long supply;

    @NotNull
    @Column(name = "is_visible", nullable = false)
    @JsonIgnore
    private Boolean isVisible;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Asset name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongName() {
        return longName;
    }

    public Asset longName(String longName) {
        this.longName = longName;
        return this;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public Long getMarketCap() {
        return marketCap;
    }

    public Asset marketCap(Long marketCap) {
        this.marketCap = marketCap;
        return this;
    }

    public void setMarketCap(Long marketCap) {
        this.marketCap = marketCap;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Asset price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSupply() {
        return supply;
    }

    public Asset supply(Long supply) {
        this.supply = supply;
        return this;
    }

    public void setSupply(Long supply) {
        this.supply = supply;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }

    public Asset isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asset)) {
            return false;
        }
        return id != null && id.equals(((Asset) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Asset{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", longName='" + getLongName() + "'" +
            ", marketCap=" + getMarketCap() +
            ", price=" + getPrice() +
            ", supply=" + getSupply() +
            ", isVisible='" + isIsVisible() + "'" +
            "}";
    }
}
