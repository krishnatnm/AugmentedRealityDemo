package com.tanmay.augmentedrealitydemo.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tanmay.augmentedrealitydemo.R;

public class TestActivity extends AppCompatActivity {

    public static String TAG = "Test ==>";

    Context context;

    Toolbar toolbar;
    SimpleDraweeView draweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_test);

        context = this;
        initView();
        setSupportActionBar(toolbar);

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.raw.ic_giphy).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageRequest.getSourceUri())
                .setAutoPlayAnimations(true)
                .build();
        draweeView.setController(controller);
    }

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
    }
}
