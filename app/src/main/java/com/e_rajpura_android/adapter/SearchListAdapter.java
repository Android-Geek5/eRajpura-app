package com.e_rajpura_android.adapter;

import android.content.Context;
import android.graphics.LinearGradient;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.e_rajpura_android.Model.SearchResults;
import com.e_rajpura_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erginus on 3/21/2017.
 */

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<SearchResults> searchResultsArrayList =new ArrayList<>();
    Context context;
    public final int TYPE_LOAD = 1;
    public final int TYPE_ITEM = 0;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public SearchListAdapter(Context context, List<SearchResults> searchResultsArrayList,RecyclerView recyclerView)
    {
        this.context=context;
        this.searchResultsArrayList =searchResultsArrayList;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType==TYPE_ITEM){
            return new SearchViewHolder(inflater.inflate(R.layout.search_item,parent,false));
        }else{
            return new LoadHolder(inflater.inflate(R.layout.load_layout,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchViewHolder) {


        } else {
            ((LoadHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return searchResultsArrayList.get(position) != null ? TYPE_ITEM : TYPE_LOAD;
    }

    @Override
    public int getItemCount() {
        return searchResultsArrayList.size();
    }

    public void addTopSearchArrayList(List<SearchResults> searchResultsArrayList)
    {
        this.searchResultsArrayList=searchResultsArrayList;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lowerLayout;
        TextView text_name,text_info,text_location,text_offers;
        ImageView image_icon,image_offer;

        public SearchViewHolder(View itemView) {
            super(itemView);
            lowerLayout=(LinearLayout) itemView.findViewById(R.id.lowerLayout);
            text_name=(TextView) itemView.findViewById(R.id.name);
            text_info=(TextView) itemView.findViewById(R.id.info);
            text_location=(TextView)itemView.findViewById(R.id.location);
            text_offers=(TextView) itemView.findViewById(R.id.offers);
            image_icon=(ImageView) itemView.findViewById(R.id.imageView);
            image_offer=(ImageView) itemView.findViewById(R.id.image_offer);
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;
        public LoadHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }


    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


}
