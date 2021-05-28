package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import java.util.Objects;

public class Tag {

    private String tagName;

    public Tag(String tagName){
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object tag) {
        if (tag == null) {
            return false;
        }

        if (this == tag) {
            return true;
        }

        if ((tag instanceof Tag) && (((Tag) tag).getTagName().toLowerCase().compareTo(this.tagName.toLowerCase()) == 0)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName);
    }
}
