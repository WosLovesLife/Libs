package com.wosloveslife.intentkit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.FileNotFoundException;

/**
 * 通过系统意图从相册或相机获取图片 以及图片剪裁
 * Created by WosLovesLife on 2016/7/14.
 */
public class GetPhoto {

    /**
     * 用于从相册获取剪切后的自定义尺寸和缩放比的图片
     *
     * @param aspectX         宽度比
     * @param aspectY         高度比
     * @param outputX         宽度尺寸(px)
     * @param outputY         高度尺寸(px)
     * @param imageUri        返回结果的形式 如果为null 返回Bitmap 否则将图片存储在Uri位置. 如果是大图片, 请用Uri存储.
     * @param noFaceDetection 是否启用人脸识别 true: 取消; false: 不取消
     * @return 跳转系统相册的Intent
     * 如果Uri不为null 请在onActivityResult()中调用
     * disposePhoto(Context context, Uri imageUri)方法完成处理.
     * 否则调用 disposePhoto(Intent data);
     */
    public static Intent fromAlbumAndCrop(int aspectX, int aspectY, int outputX, int outputY, boolean scale, Uri imageUri, boolean noFaceDetection) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", scale);

        /* 如果传了Uri进来,就不返回Bitmap,而是将图片保存在Uri位置 */
        intent.putExtra("return-data", imageUri == null);
        if (imageUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", noFaceDetection); // 是否不启用人脸识别
        return intent;
    }

    /**
     * 用于从相册获取剪切后的自定义尺寸和缩放比的图片,适用于小图
     *
     * @param aspectX 宽度比
     * @param aspectY 高度比
     * @param outputX 宽度尺寸(px)
     * @param outputY 高度尺寸(px)
     * @return 跳转系统相册的Intent, 请在onActivityResult()中调用 disposePhoto(Intent data)方法完成处理
     */
    public static Intent fromAlbumAndCrop(int aspectX, int aspectY, int outputX, int outputY) {
        return fromAlbumAndCrop(aspectX, aspectY, outputX, outputY, true, null, true);
    }

    /**
     * 用于从相册获取剪切后的小图(尺寸200*200),如需自定义,请调用fromAlbumAndClip();方法
     *
     * @return 跳转系统相册的Intent, 请在onActivityResult()中调用 disposePhoto(Intent data)方法完成处理
     */
    public static Intent getSmallPhotoFromAlbumWithCrop() {
        return fromAlbumAndCrop(1, 1, 200, 200, true, null, true);
    }

    /**
     * 用于从相册获取剪切后的大图
     *
     * @param imageUri 用于保存照片的Uri
     * @return 跳转系统相册的Intent, 请在onActivityResult()中调用 disposePhoto(Context context, Uri imageUri)方法完成处理
     */
    public static Intent getBigPhotoFromAlbumWithCrop(Uri imageUri) {
        if (imageUri == null) {
            return null;
        }
        return fromAlbumAndCrop(1, 1, 600, 600, true, imageUri, true);
    }

    /**
     * 用于通过相机拍摄并剪裁图片,
     *
     * @param imageUri
     * @return
     */
    public static Intent fromCameraAndCrop(Uri imageUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        return intent;
    }

    /**
     * 在onActivityResult()方法中调用该方法
     * 返回一个单独的裁剪页面Intent, 使用 startActivityForResult()启动该Intent
     * 并在onActivityResult()中调用disposePhoto(Context context, Uri imageUri)方法完成最终解析
     *
     * @param uri 相机拍摄的图片都很大,因此需要用该uri来指定存储位置
     * @param outputX 裁剪的宽度
     * @param outputY 裁剪的高度
     * @return 用于启动裁剪器的Intent对象
     */
    public static Intent disposeCameraPhoto(Uri uri, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }

    /**
     * 在onActivityResult()方法中调用该方法
     * 从系统返回的Intent中获取Bitmap
     *
     * @param data 系统返回的Intent
     * @return Bitmap对象. 结果可能为 null
     */
    public static Bitmap disposePhoto(Intent data) {
        if (data != null) {
            return data.getParcelableExtra("data");
        }
        return null;
    }

    /**
     * 通过图片的Uri解析为Bitmap对象
     *
     * @param context  上下文,用于获取本地内容
     * @param imageUri 图片的位置Uri
     * @return Bitmap对象, 结果可能为 null
     */
    public static Bitmap disposePhoto(Context context, Uri imageUri) {
        try {
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
