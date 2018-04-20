package com.customview.porject.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.customview.porject.R;

import java.util.ArrayList;
import java.util.List;

public class BottomItemSelectorView extends LinearLayout{
    private List<String> items;
    private LinearLayout topView;
    private LinearLayout bottomView;

    private Drawable topBackground;
    private Drawable bottomBackground;
    private int itemHeight;
    private int itemTextColor;
    private int itemTextSize;
    private int itemTextGravity;
    private int itemLineColor;

    private OnBottomItemSelectorListener onBottomItemSelectorListener;

    public void setOnBottomItemSelectorListener(OnBottomItemSelectorListener onBottomItemSelectorListener) {
        this.onBottomItemSelectorListener = onBottomItemSelectorListener;
    }

    public BottomItemSelectorView(Context context) {
        this(context, null);
    }

    public BottomItemSelectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomItemSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BottomItemSelectorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs)
    {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.BottomItemSelectorView);
        itemHeight = ta.getDimensionPixelSize(R.styleable.BottomItemSelectorView_itemHeight, 50);
        itemTextColor = ta.getColor(R.styleable.BottomItemSelectorView_itemTextColor, Color.WHITE);
        itemTextSize = ta.getDimensionPixelSize(R.styleable.BottomItemSelectorView_itemTextSize, 20);
        itemTextGravity = ta.getInt(R.styleable.BottomItemSelectorView_itemTextGravity, 2);
        topBackground = ta.getDrawable(R.styleable.BottomItemSelectorView_topBackground);
        bottomBackground = ta.getDrawable(R.styleable.BottomItemSelectorView_bottomBackground);
        itemLineColor = ta.getColor(R.styleable.BottomItemSelectorView_itemTextColor, Color.BLACK);
        ta.recycle();
    }

    private void init()
    {
        items = new ArrayList<>();

        setOrientation(VERTICAL);
        topView = new LinearLayout(getContext());
        LayoutParams topLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
        addView(topView, topLp);
        topView.setBackground(topBackground);
        topView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show(false);
            }
        });


        bottomView = new LinearLayout(getContext());
        bottomView.setOrientation(VERTICAL);
        LayoutParams bottomLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(bottomView, bottomLp);
        bottomView.setBackground(bottomBackground);
    }

    public void setItems(List<String> items)
    {
        this.items.clear();
        this.items.addAll(items);

        processItemsView();
    }

    private void processItemsView()
    {
        bottomView.removeAllViews();
        int index = 0;
        for(final String item : items)
        {
            if(index > 0)
            {
                ImageView imageView = new ImageView(getContext());
                LayoutParams ivLp = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
                imageView.setLayoutParams(ivLp);
                imageView.setBackgroundColor(itemLineColor);
                bottomView.addView(imageView);
            }

            TextView textView = new TextView(getContext());
            if(itemHeight <= 0)
            {
                itemHeight = LayoutParams.WRAP_CONTENT;
            }
            LayoutParams tvLp = new LayoutParams(LayoutParams.MATCH_PARENT, itemHeight);
            bottomView.addView(textView, tvLp);
            textView.setText(item);
            textView.setTextColor(itemTextColor);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemTextSize);
            if(itemTextGravity == ItemTextGravity.LEFTCENTER.getValue()) {
                textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            }else if(itemTextGravity == ItemTextGravity.CENTERCENTER.getValue())
            {
                textView.setGravity(Gravity.CENTER);
            }else if(itemTextGravity == ItemTextGravity.RIGHTCENTER.getValue())
            {
                textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            }
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != onBottomItemSelectorListener)
                    {
                        onBottomItemSelectorListener.onItemSelect(items.indexOf(item), item);
                    }
                    show(false);
                }
            });

            index++;
        }
    }

    public void show(boolean bVisible)
    {
        if(bVisible)
        {
            if(getVisibility() != View.VISIBLE)
            {
                setVisibility(View.VISIBLE);
            }
        }else
        {
            if(getVisibility() != View.GONE)
            {
                setVisibility(View.GONE);
            }
        }
    }

    enum ItemTextGravity
    {
        LEFTCENTER(1), CENTERCENTER(2), RIGHTCENTER(3);
        private int value;

        ItemTextGravity(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public interface OnBottomItemSelectorListener{
        void onItemSelect(int index, String value);
    }
}
