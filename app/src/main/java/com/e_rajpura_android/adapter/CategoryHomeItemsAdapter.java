package com.erajpura.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erajpura.FullDetailActivity;
import com.erajpura.Model.ShopShortDetail;
import com.erajpura.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erginus on 3/16/2017.
 */

public class CategoryHomeItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<ShopShortDetail> categoryHomeItemsList=new ArrayList<>();
    Context context;


    public CategoryHomeItemsAdapter(Context context,List<ShopShortDetail> categoryHomeItemsList)
    {
        this.context=context;
        this.categoryHomeItemsList=categoryHomeItemsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_home_item, parent, false);
            return new CategoryHomeItemsViewHolder(view);
            //else we have a header/footer
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       final ShopShortDetail categoryHomeItem=categoryHomeItemsList.get(position);
            ((CategoryHomeItemsViewHolder) holder).item_name.setText(categoryHomeItem.getShop_name());
        ((CategoryHomeItemsViewHolder) holder).text_description.setText(categoryHomeItem.getShop_description());
        Glide.with(context).load(categoryHomeItem.getShop_image()).into(((CategoryHomeItemsViewHolder) holder).image_item);

        ((CategoryHomeItemsViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FullDetailActivity.class);
                intent.putExtra("shopId",categoryHomeItem.getShop_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryHomeItemsList.size();
    }


    public void addCategoryHomeItemArrayList(List<ShopShortDetail> categoryHomeItemsList)
    {
        this.categoryHomeItemsList=categoryHomeItemsList;
    }

    public class CategoryHomeItemsViewHolder extends RecyclerView.ViewHolder {
        TextView item_name,text_description;
        ImageView image_item;

        public CategoryHomeItemsViewHolder(View itemView) {
            super(itemView);
            item_name=(TextView) itemView.findViewById(R.id.item_name);
            text_description=(TextView) itemView.findViewById(R.id.item_description);
            image_item=(ImageView) itemView.findViewById(R.id.item_image);
        }
    }

}

