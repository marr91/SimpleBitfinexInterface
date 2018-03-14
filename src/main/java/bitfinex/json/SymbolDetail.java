package bitfinex.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;


public class SymbolDetail {

    @JsonProperty("pair")
    private String pair;

    @JsonProperty("price_precision")
    private Integer pricePrecision;

    @JsonProperty("initial_margin")
    private Double initialMargin;

    @JsonProperty("minimum_margin")
    private Double minimumMargin;

    @JsonProperty("maximum_order_size")
    private Double maximumOrderSize;

    @JsonProperty("minimum_order_size")
    private Double minimumOrderSize;

    @JsonProperty("expiration")
    private String expiration;

    @JsonProperty("margin")
    private Boolean margin;

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public Integer getPricePrecision() {
        return pricePrecision;
    }

    public void setPricePrecision(Integer pricePrecision) {
        this.pricePrecision = pricePrecision;
    }

    public Double getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(Double initialMargin) {
        this.initialMargin = initialMargin;
    }

    public Double getMinimumMargin() {
        return minimumMargin;
    }

    public void setMinimumMargin(Double minimumMargin) {
        this.minimumMargin = minimumMargin;
    }

    public Double getMaximumOrderSize() {
        return maximumOrderSize;
    }

    public void setMaximumOrderSize(Double maximumOrderSize) {
        this.maximumOrderSize = maximumOrderSize;
    }

    public Double getMinimumOrderSize() {
        return minimumOrderSize;
    }

    public void setMinimumOrderSize(Double minimumOrderSize) {
        this.minimumOrderSize = minimumOrderSize;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public Boolean getMargin() {
        return margin;
    }

    public void setMargin(Boolean margin) {
        this.margin = margin;
    }

    /**
     * Convert this SymbolDetail to string, which cointains only information about
     * pair, pricePrecision, minimumOrderSize, maximumOrderSize
     * @return SymbolDetail as string
     */
    public String toSimplifiedString() {
        return pair + "," +
                pricePrecision + "," +
                minimumOrderSize + "," +
                maximumOrderSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolDetail that = (SymbolDetail) o;
        return Objects.equal(pair, that.pair) &&
                Objects.equal(pricePrecision, that.pricePrecision) &&
                Objects.equal(initialMargin, that.initialMargin) &&
                Objects.equal(minimumMargin, that.minimumMargin) &&
                Objects.equal(maximumOrderSize, that.maximumOrderSize) &&
                Objects.equal(minimumOrderSize, that.minimumOrderSize) &&
                Objects.equal(expiration, that.expiration) &&
                Objects.equal(margin, that.margin);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pair, pricePrecision, initialMargin, minimumMargin, maximumOrderSize, minimumOrderSize, expiration, margin);
    }
}
