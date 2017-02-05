package com.yesing.simplelongpicuturelayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.IOException;
import java.io.InputStream;

/**
 * 设置长图片,自动缩放
 * Created by WosLovesLIfe on 2016/9/7.
 */
public class LongImageView extends ScrollView {
    private Context mContext;
    private LinearLayout mLinearLayout;

    private InputStream mPictureStream;
    private boolean mDelayedDispose;

    private int mWidth;
    private int mHeight;

    public LongImageView(Context context) {
        this(context, null);
    }

    public LongImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LongImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        addView(mLinearLayout);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        if (mDelayedDispose && mPictureStream != null) {
            dispose(mPictureStream);
            mDelayedDispose = false;
        }
    }

    private void dispose(InputStream pictureStream) {
        try {

        /* 设置只解析边框大小 */
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            Bitmap bitmap = BitmapFactory.decodeStream(pictureStream, null, options);
            int outWidth = options.outWidth;
            int outHeight = options.outHeight;

            /* 计算缩放的比例 根据这个值调整控件的大小 */
            float ratio = (mWidth + 0f) / outWidth;

            /* 用于图片压缩,当图片尺寸大于屏幕尺寸时记录 */
            int sampleSize = 1;
            if (outWidth > mWidth) {
                sampleSize = Math.round(outWidth / mWidth);
            }

            /* 配置解析模式, 压缩比例, 不只解析边框, 用RGB色彩(节省内存开支) */
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            /* 计算出控件总高度 = 图片的高度 * 缩放的比例 */
            int totalHeight = (int) (outHeight * ratio);

            /* 计算出图片切割的个数, 因为系统的单个控件对尺寸有限制 */
            int count = totalHeight / mHeight;

            /* 计算出单个控件的高度 */
            int destHeight = outHeight / count;

            /*
             * 通过 BitmapRegionDecoder 将原始的图片分割成小份
             * 然后使用 BitmapUtils.bitmapScale() 工具 对图片进行缩放
             * 最后产生一个新的ImageView盛放图片, 在将其添加到布局控件中
             */
            for (int i = 0; i < count; i++) {
                BitmapRegionDecoder regionDecoder = BitmapRegionDecoder.newInstance(pictureStream, false);
                Rect rect = new Rect(0, destHeight * i, outWidth, destHeight * (i + 1));
                bitmap = regionDecoder.decodeRegion(rect, options);

                Bitmap newImage = bitmap;
                if (ratio != 1) {
                    newImage = BitmapUtils.bitmapScale(bitmap, ratio, ratio);
                }

                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setImageBitmap(newImage);
                mLinearLayout.addView(imageView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(pictureStream);
        }
    }

    public void setImageAssets(Context context, String assetsName) throws IOException {
        setImageInputStream(context.getAssets().open(assetsName));
    }

    public void setImageInputStream(InputStream pictureStream) {
        if (mWidth > 0) {
            dispose(pictureStream);
        } else {
            mPictureStream = pictureStream;
            mDelayedDispose = true;
        }
    }
}