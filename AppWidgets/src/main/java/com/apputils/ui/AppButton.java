package com.apputils.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apputils.R;

/**
 * Created by volodymyrmordas on 10/13/16.
 */
public class AppButton extends LinearLayout implements View.OnClickListener,
        View.OnFocusChangeListener, View.OnTouchListener {

    private static String TAG = AppButton.class.getSimpleName();

    private LinearLayout appBtn;
    private LinearLayout appBtnIconLayout;
    private LinearLayout appBtnTextLayout;

    private ImageView btnIcon;

    private int btnIconRes;

    private int btnIconWrapColor;
    private int btnTextWrapColor;

    private TextView btnText;
    private String btnTxt;

    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.app_button, this);

        appBtn = (LinearLayout) findViewById(R.id.app_btn);
        appBtnIconLayout = (LinearLayout) findViewById(R.id.app_btn_icon_wrapper);
        appBtnTextLayout = (LinearLayout) findViewById(R.id.app_btn_text_wrapper);

        btnIcon = (ImageView) findViewById(R.id.app_btn_icon);
        btnText = (TextView) findViewById(R.id.app_btn_text);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AppButton, 0, 0);
        try {
            btnTxt = ta.getString(R.styleable.AppButton_btn_txt);
            if(btnTxt != null){
                btnText.setText(btnTxt);
            }

            btnIconRes = ta.getResourceId(R.styleable.AppButton_btn_icon_src, 0);
            btnIcon.setImageResource(btnIconRes);

            btnIconWrapColor = ta.getColor(R.styleable.AppButton_btn_icon_wrp_color, 0);
            appBtnIconLayout.setBackgroundColor(btnIconWrapColor);

            btnTextWrapColor = ta.getColor(R.styleable.AppButton_btn_txt_wrp_color,0);
            appBtnTextLayout.setBackgroundColor(btnTextWrapColor);

        } finally {
            ta.recycle();
        }

        appBtn.setOnTouchListener(this);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        appBtn.setOnClickListener(l);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                appBtnTextLayout.setBackgroundColor(btnIconWrapColor);
                break;
            case MotionEvent.ACTION_UP :
                appBtnTextLayout.setBackgroundColor(btnTextWrapColor);
                break;
        }

        return false;
    }
}
