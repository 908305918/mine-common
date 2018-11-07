package com.lucy.bigimage;


import java.io.IOException;
import java.io.InputStream;

import com.lucy.common.R;

import android.app.Activity;
import android.os.Bundle;

public class LargeImageActivity extends Activity {
    private LargeImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_largeimage);
        imageView = findViewById(R.id.large_image);
        try {
            InputStream is = getAssets().open("qm.jpg");
            imageView.setInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
