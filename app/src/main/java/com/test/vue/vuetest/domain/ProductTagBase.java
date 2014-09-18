package com.test.vue.vuetest.domain;

import com.googlecode.objectify.annotation.Index;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
public class ProductTagBase {

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public String getTagCategory() {
        return tagCategory;
    }

    public void setTagCategory(String tagCategory) {
        this.tagCategory = tagCategory;
    }

    public String getTagSubCategory() {
        return tagSubCategory;
    }

    public void setTagSubCategory(String tagSubCategory) {
        this.tagSubCategory = tagSubCategory;
    }

    /** Primary key */
    @Getter
    @Setter
    String tagString;
    
    @Getter
    @Setter
    @Index
    String tagCategory;
    @Getter
    @Setter
    @Index
    String tagSubCategory;
}
