package com.test.vue.vuetest.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.vue.vuetest.domain.ProductBase;

import java.util.List;

public class ClientProduct extends ProductBase {

    /** Read-only user fields, that are populated by the server.*/
    List<ClientProductProvider> productProviders;
    List<ClientProductImage> images;
    List<ClientProductTag> tags;
    List<ClientProductComment> comments;
    List<ClientProductRating> ratings;

    /** Read-only provenance fields */
    Long orignalCreatorId;
    Long orignalProductId;

    /**
     * Ignore serialization of this field
     * @return
     */
    @JsonIgnore
    public Long getOrignalCreatorId()
    {
        return orignalCreatorId;
    }

    /**
     * Mark as property for de-serialization
     * @return
     */
    @JsonProperty
    public void setOrignalCreatorId(Long orignalCreatorId)
    {
        this.orignalCreatorId = orignalCreatorId;
    }

    /**
     * Ignore serialization of this field
     * @return
     */
    @JsonIgnore
    public Long getOrignalProductId()
    {
        return orignalProductId;
    }

    /**
     * Mark as property for de-serialization
     * @return
     */
    @JsonProperty
    public void setOrignalProductId(Long orignalProductId)
    {
        this.orignalProductId = orignalProductId;
    }

    /**
     * Ignore serialization of this field
     * @return
     */
    @JsonIgnore
    public List<ClientProductProvider> getProductProviders() {
        return productProviders;
    }

    /**
     * Mark as property for de-serialization
     * @return
     */
    @JsonProperty
    public void setProductProviders(List<ClientProductProvider> productProviders) {
        this.productProviders = productProviders;
    }

    /**
     * Ignore serialization of this field
     * @return
     */
    @JsonIgnore
    public List<ClientProductTag> getProductTags() {
        return this.tags;
    }

    /**
     * Mark as property for de-serialization
     * @return
     */
    @JsonProperty
    public void setProductTags(List<ClientProductTag> tags) {
        this.tags = tags;
    }

    /**
     * Ignore serialization of this field
     * @return
     */
    @JsonIgnore
    public List<ClientProductImage> getProductImages() {
        return images;
    }

    /**
     * Mark as property for de-serialization
     * @return
     */
    @JsonProperty
    public void setProductImages(List<ClientProductImage> images) {
        this.images = images;
    }

    /**
     * Ignore serialization of this field
     * @return
     */
    @JsonIgnore
    public List<ClientProductComment> getComments() {
        return comments;
    }

    @JsonProperty
    public void setComments(List<ClientProductComment> comments) {
        this.comments = comments;
    }

    /**
     * Ignore serialization of this field
     * @return
     */
    @JsonIgnore
    public List<ClientProductRating> getRatings() {
        return ratings;
    }

    @JsonProperty
    public void setRatings(List<ClientProductRating> ratings) {
        this.ratings = ratings;
    }
}
