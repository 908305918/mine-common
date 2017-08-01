package com.lucy.common.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.binaryfork.spanny.Spanny;
import com.lucy.common.R;
import com.lucy.common.util.ViewHelper;

public class TextViewActivity extends BaseActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_text_view);
        mTextView = ViewHelper.getView(this,R.id.tv_content);
initText();
    }

    private void initText(){
        Spanny spanny = new Spanny("Underline text", new UnderlineSpan())
                .append("\nRed text", new ForegroundColorSpan(Color.RED))
                .append("\nPlain text");
        mTextView.setText(spanny);
    }

}
