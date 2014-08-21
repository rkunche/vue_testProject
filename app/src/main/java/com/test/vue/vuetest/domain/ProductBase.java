package com.test.vue.vuetest.domain;

import com.googlecode.objectify.annotation.Index;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
public class ProductBase {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerAisleId() {
        return ownerAisleId;
    }

    public void setOwnerAisleId(Long ownerAisleId) {
        this.ownerAisleId = ownerAisleId;
    }

    public Long getOwnerProductListId() {
        return ownerProductListId;
    }

    public void setOwnerProductListId(Long ownerProductListId) {
        this.ownerProductListId = ownerProductListId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductStateEnum getCurrentProductState() {
        return currentProductState;
    }

    public void setCurrentProductState(ProductStateEnum currentProductState) {
        this.currentProductState = currentProductState;
    }

    public List<Long> getRelatedProductIds() {
        return relatedProductIds;
    }

    public void setRelatedProductIds(List<Long> relatedProductIds) {
        this.relatedProductIds = relatedProductIds;
    }

    /** Primary key */
    @Getter
    @Setter
    Long id;
    
    /** Parent information */
    @Getter
    @Setter
    Long ownerAisleId;
    @Getter
    @Setter
    Long ownerProductListId;

    public Long getCreatorId() {
        return creatorId;
    }

    public Long getCuratorId() {
        return curatorId;
    }

    /** Creator and curator information */
    @Getter @Setter
    Long creatorId;
    @Getter @Setter
    Long curatorId;
    @Getter
    @Setter
    @Index
    String title;
    @Getter
    @Setter
    @Index
    String description;
    
    public enum ProductStateEnum {
        USER_CREATED,
        CLONED,
        CURATED}
    @Getter
    @Setter
    ProductStateEnum currentProductState;

    @Getter
    @Setter
    List<Long> relatedProductIds;
    
}