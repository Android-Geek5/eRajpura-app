package com.erajpura.Model;

/**
 * Created by erginus_android on 31/3/17.
 */

public class TopCategoryAndShops {



    public int getCategories_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getCategory_icon() {
        return category_icon;
    }

    public String getShop_count() {
        return shop_count;
    }

    public String getCategory_created() {
        return category_created;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setCategory_icon(String category_icon) {
        this.category_icon = category_icon;
    }

    public void setShop_count(String shop_count) {
        this.shop_count = shop_count;
    }

    public void setCategory_created(String category_created) {
        this.category_created = category_created;
    }

    int category_id;
    String category_name;
    String category_icon;
    String shop_count;
    String category_created;


}
