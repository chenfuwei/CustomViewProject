package com.customview.porject;

import android.graphics.drawable.RippleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.customview.porject.widget.AttachmentFrameLayout;
import com.customview.porject.widget.BottomItemSelectorView;
import com.customview.porject.widget.MessageVScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CustomViewProject";
    private BottomItemSelectorView bottomItemSelectorView;
    private MessageVScrollView vScrollView;
    private AttachmentFrameLayout attachmentFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomItemSelectorViewTest();
        msgVScrollViewTest();
        attachmentFrameLayoutTest();

    }

    private void bottomItemSelectorViewTest()
    {
        List<String> bottomItems = new ArrayList<String>();
        bottomItemSelectorView = findViewById(R.id.bottomItemSelectorView);
        bottomItems = new ArrayList<>();
        bottomItems.add("拍照");
        bottomItems.add("相册");
        bottomItemSelectorView.setItems(bottomItems);
        bottomItemSelectorView.setOnBottomItemSelectorListener(new BottomItemSelectorView.OnBottomItemSelectorListener() {
            @Override
            public void onItemSelect(int index, String value) {
                Log.i(TAG, "onItemSelect index = " + index + " value = " + value);
            }
        });
        findViewById(R.id.btnBottomItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomItemSelectorView.show(true);
            }
        });
    }

    private void msgVScrollViewTest()
    {
        vScrollView = (MessageVScrollView)findViewById(R.id.messageVScrollView);
        vScrollView.setOnSelectPositionListener(new MessageVScrollView.OnSelectPositionListener() {
            @Override
            public void onSelectPosition(int position) {
                Log.i(TAG, "onSelectPosition position = " + position);
            }
        });
        findViewById(R.id.btnMsgVScroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vScrollView.getVisibility() != View.VISIBLE) {
                    vScrollView.setVisibility(View.VISIBLE);
                    vScrollView.stopScroll();
                    List<String> msgs = new ArrayList<>();
                    msgs.add("好消息，大减价");
                    msgs.add("好消息，大加价格");
                    vScrollView.setList(msgs);
                    vScrollView.startScroll();
                }else
                {
                    vScrollView.stopScroll();
                    vScrollView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void attachmentFrameLayoutTest()
    {
        attachmentFrameLayout = findViewById(R.id.attachmentFrameLayout);
        findViewById(R.id.btnAddAttachment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attachmentFrameLayout.getVisibility() != View.VISIBLE)
                {
                    attachmentFrameLayout.setVisibility(View.VISIBLE);
                }else
                {
                    attachmentFrameLayout.setVisibility(View.GONE);
                }
            }
        });
    }
}
