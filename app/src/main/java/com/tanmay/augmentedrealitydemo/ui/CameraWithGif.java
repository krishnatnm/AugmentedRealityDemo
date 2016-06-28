package com.tanmay.augmentedrealitydemo.ui;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tanmay.augmentedrealitydemo.R;

import java.util.List;

public class CameraWithGif extends AppCompatActivity implements SurfaceHolder.Callback {

    public static String TAG = "Camera_Video ==>";

    Context context;

    Toolbar toolbar;
    FloatingActionButton play;
    SurfaceView surfaceView;
    SimpleDraweeView draweeView;

    Camera camera;
    SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_camera_with_gif);

        context = this;
        initView();
        setSupportActionBar(toolbar);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        surfaceView = (SurfaceView) findViewById(R.id.ccv_surface_camera);
        play = (FloatingActionButton) findViewById(R.id.acg_play);
        draweeView = (SimpleDraweeView) findViewById(R.id.ccg_gif);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
        } catch (RuntimeException e) {
            System.err.println(e);
            return;
        }
        Camera.Parameters mParameters = camera.getParameters();
        Camera.Size bestSize = null;
        List<Camera.Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
        bestSize = sizeList.get(0);
        for (int i = 1; i < sizeList.size(); i++) {
            if ((sizeList.get(i).width * sizeList.get(i).height) >
                    (bestSize.width * bestSize.height)) {
                bestSize = sizeList.get(i);
            }
        }
        mParameters.setPreviewSize(bestSize.width, bestSize.height);
        camera.setParameters(mParameters);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
        showGif();
    }

    public void showGif() {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.raw.ic_giphy).build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(imageRequest.getSourceUri())
                        .setAutoPlayAnimations(true)
                        .build();
                draweeView.setController(controller);
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview();
        } catch (Exception e) {
        }
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
        }
    }
}
