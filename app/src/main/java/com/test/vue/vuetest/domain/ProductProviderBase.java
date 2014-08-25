package com.test.vue.vuetest.domain;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
public class ProductProviderBase
{
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public long getSaleExpirtyDate() {
        return saleExpirtyDate;
    }

    public void setSaleExpirtyDate(long saleExpirtyDate) {
        this.saleExpirtyDate = saleExpirtyDate;
    }

    public List<String> getAvailableColors() {
        return availableColors;
    }

    public void setAvailableColors(List<String> availableColors) {
        this.availableColors = availableColors;
    }

    public List<String> getAvailableSizes() {
        return availableSizes;
    }

    public void setAvailableSizes(List<String> availableSizes) {
        this.availableSizes = availableSizes;
    }

    public List<Long> getAvailableAtZipCodes() {
        return availableAtZipCodes;
    }

    public void setAvailableAtZipCodes(List<Long> availableAtZipCodes) {
        this.availableAtZipCodes = availableAtZipCodes;
    }

    /** Primary key */
    @Getter @Setter @Id
    Long id;

    public String getExternalURL() {
        return externalURL;
    }

    public void setExternalURL(String externalURL) {
        this.externalURL = externalURL;
    }

    public ProductAvailabilityEnum getAvailability() {
        return availability;
    }

    public void setAvailability(ProductAvailabilityEnum availability) {
        this.availability = availability;
    }

    public Long getOwnerProductId() {
        return ownerProductId;
    }

    public void setOwnerProductId(Long ownerProductId) {
        this.ownerProductId = ownerProductId;
    }

    /** Parent information */
    @Getter @Setter Long ownerProductId;

    public enum ProductAvailabilityEnum {
        EXCESS_INVENTORY,
        IN_STOCK,
        RUNNING_LOW,
        TEMPORARY_UNAVAILABLE,
        PERMANENTLY_UNAVAILABLE,
        OUT_OF_STOCK
    };

    @Getter @Setter String externalURL;
    @Getter @Setter int quantity;

    /**
     * Indexed search-able fields
     */
    @Getter @Setter @Index String currencyCode;
    @Getter @Setter @Index String store;
    @Getter @Setter @Index ProductAvailabilityEnum availability;
    @Getter @Setter @Index double price;
    @Getter @Setter @Index boolean onSale;
    @Getter @Setter @Index float salePrice;
    @Getter @Setter @Index long saleExpirtyDate;

    /** TODO: Still thinking if these should be modelled as sizes or searchable constant strings */
    @Getter @Setter List<String> availableColors;
    @Getter @Setter List<String> availableSizes;
    @Getter @Setter List<Long> availableAtZipCodes;
}
