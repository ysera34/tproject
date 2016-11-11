package com.tacademy.sample.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCameraButton;
    private Button mAnotherButton;
    private ImageView mCapturedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        mCameraButton = (Button) findViewById(R.id.camera_button);
        mCameraButton.setTypeface(font);
        mCameraButton.setOnClickListener(this);
        mAnotherButton = (Button) findViewById(R.id.another_button);
        mAnotherButton.setOnClickListener(this);

        mCapturedImageView = (ImageView) findViewById(R.id.captured_image_view);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mCapturedImageView.setImageBitmap(bitmap);
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.camera_button) {
            openCamera();
        } else if (view.getId() == R.id.another_button) {
            startActivity(new Intent(this, CameraVideoActivity.class));
        }
    }
}
