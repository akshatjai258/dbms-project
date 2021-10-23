package com.project.elearningwebapp.models;

import java.beans.Transient;


public class Category {

    private int categoryId;
    private String categoryName;

    public Category(int categoryId, String categoryName, String logo) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.setLogo(logo);
    }

    private String logo;

    public Category() {

    }

    @Transient
    public String geLogoImagePath(){
        if(logo == null || categoryId == 0) return null;
        return "category-logos/" + categoryId +  "/" + logo;
    }
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", logo='" + getLogo() + '\'' +
                '}';
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
