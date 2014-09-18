package com.test.vue.vuetest.domain;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
public class AisleBase {


    /** Primary key */
    @Getter @Setter @Id
    Long id;
    /** Parent information */
    @Index @Getter @Setter Long ownerUserId;
    /** Aisle fields */
    @Getter @Setter @Index String category;

    public AisleStateEnum getCurrentAisleState() {
        return currentAisleState;
    }

    public void setCurrentAisleState(AisleStateEnum currentAisleState) {
        this.currentAisleState = currentAisleState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccassion() {
        return occassion;
    }

    public void setOccassion(String occassion) {
        this.occassion = occassion;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(int bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    @Getter @Setter @Index String lookingFor;
    @Getter @Setter @Index String name;
    @Getter @Setter @Index String occassion;
    @Getter @Setter @Index int bookmarkCount;
    @Getter @Setter @Index String description;
    public enum AisleStateEnum {
        CRAWLED,
        CURATED,
        CURATED_AND_VERIFIED,
        NEEDS_RECURATION,
        ARCHIVED,
        USER_CREATED,
        CLONED,
        DELETED}
    @Index @Getter @Setter AisleStateEnum currentAisleState;
}
