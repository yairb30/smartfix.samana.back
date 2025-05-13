package com.smartfixsamana.models.dto;

public class PartDTO {

    private Long phoneId;
    private Long partCatalogId;

    public Long getPhoneId() {

        return phoneId;
    }

    public void setPhoneId(Long phone) {
        this.phoneId = phone;
    }

    public Long getPartCatalogId() {
        return partCatalogId;
    }

    public void setPartCatalogId(Long partCatalog) {
        this.partCatalogId = partCatalog;
    }

}
