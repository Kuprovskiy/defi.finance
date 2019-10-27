package finance.defi.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class DefaultAssetsDTO implements Serializable {

    @NotNull
    private Long assetId;


    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAssetsDTO that = (DefaultAssetsDTO) o;
        return Objects.equals(assetId, that.assetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetId);
    }

    @Override
    public String toString() {
        return "DefaultAssetsDTO{" +
            "assetId=" + assetId +
            '}';
    }
}
