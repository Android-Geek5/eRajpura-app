package com.erajpura.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by erginus_android on 31/3/17.
 */

public class ShopDetail implements Serializable{
    int shop_id;
    int categories_id;
    String shop_name;
    String shop_slug;
    int shop_view_count;
    String shop_description;
    String shop_address;
    String shop_city_name;
    String shop_state_name;
    String shop_zipcode;
    String shop_contact_1;
    String shop_contact_2;
    String shop_email;
    String shop_facebook_url;
    int shop_rating;
    String  shop_image;
    String shop_meta_title;
    String shop_meta_description;
    String shop_meta_keywords;
    int shop_status;
    String shop_timing;
    String shop_closed_days;
    String shop_created;
    String shop_modified;
    String shop_review;
    String shop_review_name;
    String shop_meta_search;

    public ArrayList<String> getShop_services_name_array() {
        return shop_services_name_array;
    }

    ArrayList<String> shop_services_name_array;

    public int getShop_id() {
        return shop_id;
    }

    public int getCategories_id() {
        return categories_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getShop_slug() {
        return shop_slug;
    }

    public int getShop_view_count() {
        return shop_view_count;
    }

    public String getShop_description() {
        return shop_description;
    }

    public String getShop_address() {
        return shop_address;
    }

    public String getShop_city_name() {
        return shop_city_name;
    }

    public String getShop_state_name() {
        return shop_state_name;
    }

    public String getShop_zipcode() {
        return shop_zipcode;
    }

    public String getShop_contact_1() {
        return shop_contact_1;
    }

    public String getShop_contact_2() {
        return shop_contact_2;
    }

    public String getShop_email() {
        return shop_email;
    }

    public String getShop_facebook_url() {
        return shop_facebook_url;
    }

    public int getShop_rating() {
        return shop_rating;
    }

    public String getShop_image() {
        return shop_image;
    }

    public String getShop_meta_title() {
        return shop_meta_title;
    }

    public String getShop_meta_description() {
        return shop_meta_description;
    }

    public String getShop_meta_keywords() {
        return shop_meta_keywords;
    }

    public int getShop_status() {
        return shop_status;
    }

    public String getShop_timing() {
        return shop_timing;
    }

    public String getShop_closed_days() {
        return shop_closed_days;
    }

    public String getShop_created() {
        return shop_created;
    }

    public String getShop_modified() {
        return shop_modified;
    }

    public String getShop_review() {
        return shop_review;
    }

    public String getShop_review_name() {
        return shop_review_name;
    }

    public String getShop_meta_search() {
        return shop_meta_search;
    }



}
