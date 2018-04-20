package com.customview.porject.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.customview.porject.R;

public class AttachmentFrameLayout extends FrameLayout{
    private static final String TAG = "AttachmentLinearLayout";
    private final int LINE_MAX_COUNT = 5;
    private int mTotalCount = 5;
    private int mViewWidth;
    private int mItemLeftMargin;
    private int mItemTotalWidth;
    private int mItemTopMargin;
    private int mItemOneWidth;

    public void setTotalCount(int totalCount) {
        if(totalCount > LINE_MAX_COUNT * 2)
        {
            totalCount = LINE_MAX_COUNT * 2;
        }
       this.mTotalCount = totalCount;
    }

    private OnPhotoProcessListener onPhotoProcessListener;

    public AttachmentFrameLayout(Context context) {
        this(context, null);
    }

    public AttachmentFrameLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AttachmentFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mItemOneWidth = getContext().getResources().getDimensionPixelSize(R.dimen.employee_attach_child_item_width);
        mItemTotalWidth = LINE_MAX_COUNT * mItemOneWidth;
        mItemTopMargin = getContext().getResources().getDimensionPixelSize(R.dimen.employee_attach_child_image_top);
        addAttachmentItem();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mViewWidth = specSize;
            mItemLeftMargin = (mViewWidth - mItemTotalWidth) / (LINE_MAX_COUNT - 1);
        }else
        {
            Log.e(TAG, "onMeasure error!");
        }
    }

    private void addAttachmentItem()
    {
        if(getChildCount()>= mTotalCount || getChildCount() >= LINE_MAX_COUNT * 2)
        {
            return;
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.attachment_child_item_layout, null);
        addView(view);

        view.findViewById(R.id.attachments_add_iv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onPhotoProcessListener && null == v.getTag())
                {
                    onPhotoProcessListener.onAddPhoto();
                }
                addAttachmentItem();
            }
        });

        view.findViewById(R.id.attachments_clear_iv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onPhotoProcessListener)
                {
                    onPhotoProcessListener.onDelPhoto(indexOfChild((View)(v.getParent())));
                }
                deleteAttachItem(v);
            }
        });

        int nChildCount = getChildCount();
        if(nChildCount >= 1 && nChildCount <= LINE_MAX_COUNT)
        {
            View lastView = getChildAt(getChildCount() - 1);
            LayoutParams lp = (LayoutParams)lastView.getLayoutParams();
            lp.topMargin = 0;
            lp.leftMargin = (nChildCount -1) * (mItemLeftMargin + mItemOneWidth);
            lastView.setLayoutParams(lp);
        }else if(nChildCount > LINE_MAX_COUNT)
        {
            View lastView = getChildAt(getChildCount() - 1);
            LayoutParams lp = (LayoutParams)lastView.getLayoutParams();

            lp.leftMargin = (nChildCount -LINE_MAX_COUNT -1) * (mItemLeftMargin + mItemOneWidth);
            lp.topMargin = mItemTopMargin + mItemOneWidth;

            lastView.setLayoutParams(lp);
        }
    }

    private void deleteAttachItem(View view)
    {
        View clearView = view;
        if(clearView.getVisibility() == View.VISIBLE) {
            clearView.setVisibility(View.GONE);
            boolean bAddDefault = false;
            if(getChildCount() >= mTotalCount)
            {
                View lastView = getChildAt(getChildCount() - 1);
                View child = lastView.findViewById(R.id.attachments_add_iv);
                //最后已经有空的就不需要添加空的
                bAddDefault = null != child.getTag();
            }

            View parentView = ((View)(view.getParent()));
            removeView(parentView);

            for(int index = 0; index < getChildCount(); index++)
            {
                View childView = getChildAt(index);
                LayoutParams lp = (LayoutParams)childView.getLayoutParams();
                if(index < LINE_MAX_COUNT)
                {
                    lp.topMargin = 0;
                    lp.leftMargin = index * (mItemLeftMargin + mItemOneWidth);
                }else
                {
                    lp.topMargin = mItemTopMargin;
                    lp.leftMargin = (index -LINE_MAX_COUNT) * (mItemLeftMargin + mItemOneWidth);
                }
                childView.setLayoutParams(lp);
            }


            if(bAddDefault)
            {
                addAttachmentItem();
            }
        }
    }

    public void addBitmap(Bitmap mBitmap)
    {
        int nCount = getChildCount();
        if(nCount > 0)
        {
            View view = getChildAt(nCount - 1);
            ImageView imageView = (ImageView)view.findViewById(R.id.attachments_add_iv);
            imageView.setImageBitmap(mBitmap);
            imageView.setTag(nCount-1); //用于区分设置了图片
            view.findViewById(R.id.attachments_clear_iv).setVisibility(View.VISIBLE);

            addAttachmentItem();
        }
    }

    private void displayImage(ImageView imageView, String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getContext(), "failed to get image", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void setOnPhotoProcessListener(OnPhotoProcessListener onPhotoProcessListener) {
        this.onPhotoProcessListener = onPhotoProcessListener;
    }

    public interface OnPhotoProcessListener
    {
        void onAddPhoto();
        void onDelPhoto(int viewIndex);
    }
}
