package com.project.elearningwebapp.models;

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
