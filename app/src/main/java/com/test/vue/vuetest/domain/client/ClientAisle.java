package com.test.vue.vuetest.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.vue.vuetest.domain.AisleBase;


import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientAisle extends AisleBase {
    
    List<ClientProduct> productList;

    /**
     * Ignore serialization of this field
     * @return
     */
    @JsonIgnore
    public List<ClientProduct> getProductList()
    {
        return productList;
    }

    /**
     * Mark as property for de-serialization
     * @return
     */
    @JsonProperty
    public void setProductList(List<ClientProduct> productList)
    {
        this.productList = productList;
    }
}
