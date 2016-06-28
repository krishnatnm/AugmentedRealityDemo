package com.tanmay.augmentedrealitydemo.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;

import com.sprylab.android.widget.TextureVideoView;
import com.tanmay.augmentedrealitydemo.R;

import java.util.List;

public class CameraWIthVideo extends AppCompatActivity implements SurfaceHolder.Callback {

    public static String TAG = "Camera_Video ==>";

    Context context;

    Toolbar toolbar;
    FloatingActionButton play;
    SurfaceView surfaceView;
    //    VideoView videoView;
    TextureVideoView texVideoView;

    Camera camera;
    SurfaceHolder surfaceHolder;

    ProgressDialog progressDialog;
    MediaController mediaControls;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_with_video);

        context = this;
        initView();
        setSupportActionBar(toolbar);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        surfaceView.setZOrderMediaOverlay(true);

    }

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        surfaceView = (SurfaceView) findViewById(R.id.ccv_surface_camera);
//        videoView = (VideoView) findViewById(R.id.ccv_video_view);
        play = (FloatingActionButton) findViewById(R.id.acv_play);
        texVideoView = (TextureVideoView) findViewById(R.id.ccv_texture_video_view);
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
//        setVideoView();
        setTextureVideoView();
    }

    public void setTextureVideoView() {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texVideoView.setAlpha(0.7f);
                texVideoView.setVideoPath(getVideoPath());
                texVideoView.setMediaController(new MediaController(context));
                texVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(final MediaPlayer mp) {
                        startVideoPlayback();
//                startVideoAnimation();
                    }
                });
            }
        });
    }

    private void startVideoPlayback() {
        // "forces" anti-aliasing - but increases time for taking frames - so keep it disabled
        // mVideoView.setScaleX(1.00001f);
        texVideoView.start();
        play.setVisibility(View.INVISIBLE);
    }

    private void startVideoAnimation() {
        texVideoView.animate().rotationBy(360.0f).setDuration(texVideoView.getDuration()).start();
    }

    private String getVideoPath() {
        return "android.resource://" + getPackageName() + "/" + R.raw.sample_video;
    }

//    public void setVideoView() {
//        videoView.setZOrderOnTop(true);
//        videoView.setAlpha(0.5f);
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                videoView.setVisibility(View.VISIBLE);
//                if (mediaControls == null) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        mediaControls = new MediaController(context);
//                    }
//                }
//                progressDialog = new ProgressDialog(context);
//                progressDialog.setTitle("Tom and Jerry");
//                progressDialog.setMessage("Loading...");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//                try {
//                    videoView.setMediaController(mediaControls);
//                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample_video));
//                } catch (Exception e) {
//                    Log.e("Error", e.getMessage());
//                    e.printStackTrace();
//                }
//                videoView.requestFocus();
//                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    public void onPrepared(MediaPlayer mediaPlayer) {
//                        progressDialog.dismiss();
//                        videoView.seekTo(position);
//                        if (position == 0) {
//                            videoView.start();
//                        } else {
//                            videoView.pause();
//                        }
//                    }
//                });
//            }
//        });
//    }

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
