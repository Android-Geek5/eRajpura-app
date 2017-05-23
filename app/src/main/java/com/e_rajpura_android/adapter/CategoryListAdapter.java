package com.erajpura.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erajpura.Model.TopCategoryAndShops;
import com.erajpura.R;
import com.erajpura.ShopListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erginus on 3/14/2017.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {
    List<TopCategoryAndShops> categoryArrayList =new ArrayList<>();
    Context context;
    Fragment fragment;

    public CategoryListAdapter(Context context, List<TopCategoryAndShops> categoryArrayList, Fragment fragment)
    {
        this.context=context;
        this.categoryArrayList = categoryArrayList;
        this.fragment=fragment;
    }
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        final TopCategoryAndShops categoryObject=categoryArrayList.get(position);
        holder.text_name.setText(categoryObject.getCategory_name());
        Glide.with(context).load(categoryObject.getCategory_icon()).into( holder.image_icon);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categoryObject.getCategories_id()==123){
                    BottomNavigationView  bottomNavigation = (BottomNavigationView)((AppCompatActivity)context).findViewById(R.id.bottom_navigation);
                    ViewPager viewPager=(ViewPager)((AppCompatActivity)context).findViewById(R.id.mainviewpager);
                    viewPager.setCurrentItem(1);
                    bottomNavigation.getMenu().getItem(1).setChecked(true);
                }
                else{
                    Intent intent=new Intent(context, ShopListActivity.class);
                    intent.putExtra("catId",categoryObject.getCategories_id());
                    intent.putExtra("catName",categoryObject.getCategory_name());
                    context.startActivity(intent);
                }

            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView text_name;
        ImageView image_icon;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            text_name=(TextView) itemView.findViewById(R.id.category_name);
            image_icon=(ImageView) itemView.findViewById(R.id.category_icon);
        }
    }
}
