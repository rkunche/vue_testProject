package com.test.vue.vuetest.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
public class ProductImageBase {

    /** Primary key */
    @Getter
    @Setter
    Long id;
    
    /** Parent information */
    @Getter
    @Setter
    Long ownerProductId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerProductId() {
        return ownerProductId;
    }

    public void setOwnerProductId(Long ownerProductId) {
        this.ownerProductId = ownerProductId;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public void setExternalURL(String externalURL) {
        this.externalURL = externalURL;
    }

    public String getInternalURL() {
        return internalURL;
    }

    public void setInternalURL(String internalURL) {
        this.internalURL = internalURL;
    }

    public Float getOrignalHeight() {
        return orignalHeight;
    }

    public void setOrignalHeight(Float orignalHeight) {
        this.orignalHeight = orignalHeight;
    }

    public Float getOrignalWidth() {
        return orignalWidth;
    }

    public void setOrignalWidth(Float orignalWidth) {
        this.orignalWidth = orignalWidth;
    }

    public Float getModifiedHeight() {
        return modifiedHeight;
    }

    public void setModifiedHeight(Float modifiedHeight) {
        this.modifiedHeight = modifiedHeight;
    }

    public Float getModifiedWidth() {
        return modifiedWidth;
    }

    public void setModifiedWidth(Float modifiedWidth) {
        this.modifiedWidth = modifiedWidth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Getter
    @Setter
    String externalURL;
    @Getter
    @Setter
    String internalURL;
    @Getter
    @Setter
    Float orignalHeight;
    @Getter
    @Setter
    Float orignalWidth;
    @Getter
    @Setter
    Float modifiedHeight;
    @Getter
    @Setter
    Float modifiedWidth;
    @Getter
    @Setter
    String description;

}