package com.wosloveslife.libs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wosloveslife.intentkit.GetPhoto;
import com.wosloveslife.intentkit.IntentKit;

import java.io.File;

/**
 * Created by YesingBeijing on 2016/7/14.
 */
public class GetAndClipPhotoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GetAndClipPhotoActivity";

    private static final int REQUEST_SMALL_PHOTO = 0;
    private static final int REQUEST_BIG_PHOTO = 1;
    private static final int REQUEST_CUSTOM_PHOTO = 2;
    private static final int REQUEST_PHOTO_CAMERA = 3;
    private static final int REQUEST_PHOTO_CAMERA2 = 4;

    private Button mGetPhotoFromAlbum;
    private ImageView mImageView;
    private Button mGetBigPhotoFromAlbum;
    private Uri mBitPhotoUri;
    private Button mGetCustomPhotoFromAlbum;
    private Button mGetPhotoFromCamera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_and_clip_photo);

        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.id_iv_show_photo);

        mGetPhotoFromAlbum = (Button) findViewById(R.id.id_btn_get_photo_from_album);
        mGetPhotoFromAlbum.setOnClickListener(this);

        mGetBigPhotoFromAlbum = (Button) findViewById(R.id.id_btn_get_big_photo_from_album);
        mGetBigPhotoFromAlbum.setOnClickListener(this);

        mGetCustomPhotoFromAlbum = (Button) findViewById(R.id.id_btn_get_custom_photo_from_album);
        mGetCustomPhotoFromAlbum.setOnClickListener(this);

        mGetPhotoFromCamera = (Button) findViewById(R.id.id_btn_get_photo_from_camera);
        mGetPhotoFromCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_get_photo_from_album:
                Intent smallPhotoFromAlbumWithClip = GetPhoto.getSmallPhotoFromAlbumWithCrop();
                startActivityForResult(smallPhotoFromAlbumWithClip, REQUEST_SMALL_PHOTO);
                break;
            case R.id.id_btn_get_big_photo_from_album:
                mBitPhotoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tempBigPhoto.jpg"));
                Intent bigPhotoFromAlbumWithClip = GetPhoto.getBigPhotoFromAlbumWithCrop(mBitPhotoUri);
                startActivityForResult(bigPhotoFromAlbumWithClip, REQUEST_BIG_PHOTO);
                break;
            case R.id.id_btn_get_custom_photo_from_album:
                Intent customPhotoFromAlbumWithClip = GetPhoto.fromAlbumAndCrop(2, 2, 50, 50, true, null, true);
                startActivityForResult(customPhotoFromAlbumWithClip, REQUEST_CUSTOM_PHOTO);
                break;
            case R.id.id_btn_get_photo_from_camera:
                Intent fromCameraAndClip = GetPhoto.fromCameraAndCrop(mBitPhotoUri);
                try {
                    IntentKit.usableIntent(this, fromCameraAndClip);
                    startActivityForResult(fromCameraAndClip, REQUEST_PHOTO_CAMERA);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(GetAndClipPhotoActivity.this, "找不到可用相机", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        switch (requestCode) {
            case REQUEST_SMALL_PHOTO:
                bitmap = GetPhoto.disposePhoto(data);
                break;
            case REQUEST_BIG_PHOTO:
                bitmap = GetPhoto.disposePhoto(this, mBitPhotoUri);
                break;
            case REQUEST_CUSTOM_PHOTO:
                bitmap = GetPhoto.disposePhoto(data);
                break;
            case REQUEST_PHOTO_CAMERA:
                Intent intent = GetPhoto.disposeCameraPhoto(mBitPhotoUri, 400, 400);
                try {
                    IntentKit.usableIntent(this, intent);
                    startActivityForResult(intent, REQUEST_PHOTO_CAMERA2);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(GetAndClipPhotoActivity.this, "找不到系统剪裁器", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_PHOTO_CAMERA2:
                bitmap = GetPhoto.disposePhoto(data);
                break;
        }
        mImageView.setImageBitmap(bitmap);
    }
}
