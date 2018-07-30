package cn.com.geekplus.app.rf.widgets;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.com.geekplus.app.rf.R;


public class DialogWrapper extends Dialog {

    private TextView mShowtext;
    private ProgressBar mProgressBar;

    public DialogWrapper(Context context) {
        super(context, R.style.DialogStyle);
        setContentView(R.layout.layout_dialog_wrapper);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mShowtext = (TextView) findViewById(R.id.show_text);
        mShowtext.setText(R.string.loading);

        Window win = getWindow();
        win.setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void setMessage(int id) {
        mShowtext.setText(id);
    }
    public void setMessage(String message) {
        mShowtext.setText(message);
    }


    public static class Builder {
        Context context;
        String content;
        boolean showProgress = false;
        boolean cancelable = true;
        int contentResId;
        int drawableTop;
        int drawableLeft;
        int drawableRight;
        int drawableBottom;
        int drawablePadding;
        long duration;

        public Builder(Context context) {
            this.context = context;
        }
        public Builder showProgress(boolean showProgress) {
            this.showProgress = showProgress;
            return this;
        }

        public Builder setText(String text) {
            this.content = text;
            return this;
        }
        public Builder setText(int resId) {
            this.contentResId = resId;
            return this;
        }

        public Builder drawableLeft(int drawable) {
            this.drawableLeft = drawable;
            return this;
        }
        public Builder drawableRight(int drawable) {
            this.drawableRight = drawable;
            return this;
        }
        public Builder drawableTop(int drawable) {
            this.drawableTop = drawable;
            return this;
        }
        public Builder drawableBottom(int drawable) {
            this.drawableBottom = drawable;
            return this;
        }

        public Builder drawablePadding(int drawablePadding) {
            this.drawablePadding = drawablePadding;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public DialogWrapper build() {
            final DialogWrapper dialog = new DialogWrapper(context);
            dialog.mProgressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
            if (contentResId != 0) {
                dialog.mShowtext.setText(contentResId);
            }
            if (!TextUtils.isEmpty(content)) {
                dialog.mShowtext.setText(content);
            }

            dialog.mShowtext.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(cancelable);
            if (duration != 0) {
                new Handler(context.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }, duration);
            }

            return dialog;
        }

    }
}
