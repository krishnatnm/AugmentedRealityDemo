package com.tanmay.augmentedrealitydemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.tanmay.augmentedrealitydemo.R;
import com.tanmay.augmentedrealitydemo.adapter.HomeAdapter;
import com.tanmay.augmentedrealitydemo.interfaces.OnListItemClickListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements OnListItemClickListener {

    public static String TAG = "Home ==>";

    Context context;

    Toolbar toolbar;
    RecyclerView mRecyclerView;

    RecyclerView.Adapter mAdapter;

    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<String> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;
        initView();
        setSupportActionBar(toolbar);

        itemList = new ArrayList<>();
        itemList.add("Video on Camera View");
        itemList.add("GIF on Camera View");
        itemList.add("Test Activity");

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HomeAdapter(context, itemList);
        mRecyclerView.setAdapter(mAdapter);
        HomeAdapter.onClick = this;
    }

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.ch_recycler_view);
    }

    @Override
    public void onItemClick(View view) {
        int pos = mRecyclerView.getChildAdapterPosition(view);
        switch (pos) {
            case 0:
                startActivity(new Intent(context, CameraWIthVideo.class));
                break;
            case 1:
                startActivity(new Intent(context, CameraWithGif.class));
                break;
            case 2:
                startActivity(new Intent(context, TestActivity.class));
                break;
            default:
                Toast.makeText(context, "To be done!", Toast.LENGTH_SHORT).show();
        }
    }
}
