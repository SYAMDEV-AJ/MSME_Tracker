package com.manappuram.msmetracker.utility;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;
import com.manappuram.msmetracker.R;


public class CustomLoader extends Dialog {

    public CustomLoader(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_loader);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(false);
    }

}
