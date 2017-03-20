package com.e_rajpura_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e_rajpura_android.Model.Category;
import com.e_rajpura_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erginus on 3/14/2017.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {
    List<Category> categoryArrayList =new ArrayList<>();
    Context context;

    public CategoryListAdapter(Context context, List<Category> categoryArrayList)
    {
        this.context=context;
        this.categoryArrayList = categoryArrayList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category categoryObject=categoryArrayList.get(position);
        holder.text_name.setText(categoryObject.getName());

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public void addTopSearchArrayList(List<Category> categoryArrayList)
    {
        this.categoryArrayList=categoryArrayList;
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
