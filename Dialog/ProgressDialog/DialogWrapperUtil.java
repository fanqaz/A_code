package cn.com.geekplus.app.rf.widgets;

import android.app.Dialog;
import android.content.Context;

import cn.com.geekplus.app.rf.R;

public class DialogWrapperUtil {

    public static DialogWrapper load(Context context){
        return load(context, null);
    }

    public static DialogWrapper load(Context context, String content){
        DialogWrapper dialog = new DialogWrapper.Builder(context)
                .cancelable(true)
                .showProgress(true)
                .setText(content)
                .build();
        dialog.show();
        return dialog;
    }
    public static DialogWrapper show(Context context, String content, long duration) {
        DialogWrapper dialog = new DialogWrapper.Builder(context)
                .showProgress(false)
                .setText(content)
                .drawableTop(R.drawable.icon_dialog_ok)
                .duration(duration)
                .build();
        dialog.show();
        return dialog;
    }

    public static void error(Context context, String content) {
        DialogWrapper dialog = new DialogWrapper.Builder(context)
                .showProgress(false)
                .setText(content)
                .drawableTop(R.drawable.icon_dialog_error)
                .duration(1500)
                .build();
        dialog.show();
    }

    public static void alert(Context context, String content) {
        DialogWrapper dialog = new DialogWrapper.Builder(context)
                .showProgress(false)
                .setText(content)
                .drawableTop(R.drawable.icon_dialog_alert)
                .duration(1500)
                .build();
        dialog.show();
    }

    public static void ok(Context context, String content) {
        DialogWrapper dialog = new DialogWrapper.Builder(context)
                .showProgress(false)
                .setText(content)
                .drawableTop(R.drawable.icon_dialog_ok)
                .duration(1500)
                .build();
        dialog.show();
    }

}
