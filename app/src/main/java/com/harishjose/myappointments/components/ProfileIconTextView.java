package com.harishjose.myappointments.components;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.utils.GeneralUtil;

/**
 * Created by harish.jose on 15-09-2018.
 */
public class ProfileIconTextView extends AppCompatTextView {
    public ProfileIconTextView(Context context) {
        super(context);
        init(context);
    }

    public ProfileIconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProfileIconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackground(GeneralUtil.getDrawable(context, R.drawable.profile_icon_circle));
        setTextSize(18);
        setTypeface(getTypeface(), Typeface.BOLD);
        setGravity(Gravity.CENTER);
    }
}
