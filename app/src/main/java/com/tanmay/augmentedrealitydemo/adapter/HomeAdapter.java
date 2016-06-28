package com.tanmay.augmentedrealitydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.tanmay.augmentedrealitydemo.R;
import com.tanmay.augmentedrealitydemo.interfaces.OnListItemClickListener;

import java.util.ArrayList;

/**
 * Created by TaNMay on 19/06/16.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    ArrayList<String> items;
    public static OnListItemClickListener onClick;

    public HomeAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(view);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        holder.listItem.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView listItem;

        public ViewHolder(View itemView) {
            super(itemView);
            listItem = (TextView) itemView.findViewById(R.id.dli_item);
        }

    }
}
