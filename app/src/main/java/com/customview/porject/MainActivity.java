package com.customview.porject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.customview.porject.widget.BottomItemSelectorView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CustomViewProject";
    private BottomItemSelectorView bottomItemSelectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomItemSelectorViewTest();

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
}
