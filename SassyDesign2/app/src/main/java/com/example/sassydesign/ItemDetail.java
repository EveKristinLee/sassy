package com.example.sassydesign;

import android.widget.Spinner;

public class ItemDetail {
    String productName;
    String productCost;
    String category;
    String quantity;


    public ItemDetail(String productName, String productCost, String quantity, String category) {
        this.productName = productName;
        this.productCost = productCost;
        this.quantity = quantity;
        this.category = category;

    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCost() {
        return productCost;
    }

    public void setProductCost(String productCost) {
        this.productCost = productCost;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
