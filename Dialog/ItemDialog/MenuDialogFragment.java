package cn.com.geekplus.app.rf.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import java.util.List;

import cn.com.geekplus.app.rf.R;
import cn.com.geekplus.app.rf.widgets.bubble.BTButton;

/**
 * Created by wangyunhui on 16/9/19.
 */
public class MenuDialogFragment extends DialogFragment implements View.OnClickListener{

    OnItemClickListener listener;
    List<ItemButton> itemList;
    LinearLayout itemLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogBottom);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        dialog.getWindow().setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menu_dialog, container, false);
        itemLayout = (LinearLayout) view.findViewById(R.id.item_layout);
        view.findViewById(R.id.ibtn_cancel).setOnClickListener(this);

        View layout = view.findViewById(R.id.layout);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) layout.getLayoutParams();
        layout.setMinimumWidth(getResources().getDisplayMetrics().widthPixels - lp.leftMargin - lp.rightMargin);

        if (itemList != null) {
            setItems(itemList, this.listener);
        }
        return view;
    }

    public void setItems(List<ItemButton> list, OnItemClickListener listener) {
        this.itemList = list;
        this.listener = listener;
        if (itemLayout == null) {
            return;
        }
        itemLayout.removeAllViews();

        int height = getContext().getResources().getDimensionPixelSize(R.dimen.dp_40);
        int topMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dp_15);
        int leftMargin=getContext().getResources().getDimensionPixelSize(R.dimen.dp_5);

        int index = 0;
        for (ItemButton item: list) {
            BTButton button = new BTButton(getContext());
            button.setSingleLine();
            if (item.background != null) {
                button.setBackground(item.background);
            } else {
                button.setBackgroundResource(R.drawable.shape_circle_orange);
            }
            button.setGravity(Gravity.CENTER);
            button.setText(item.text);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            if (item.textColor == 0) {
                button.setTextColor(getResources().getColor(R.color.color_yellow));
            } else {
                button.setTextColor(getContext().getResources().getColor(item.textColor));
            }
            button.setTag(index);
            button.setOnClickListener(this);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, height);
            lp.topMargin = topMargin;
            lp.leftMargin= leftMargin;
            lp.rightMargin=leftMargin;
            button.setLayoutParams(lp);
            itemLayout.addView(button);

            index++;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ibtn_cancel) {
            dismiss();
        } else {
            int position = (Integer) v.getTag();
            if (listener != null) {
                listener.onItemClick(v, position);
            }
            dismiss();
        }
    }


    public static class ItemButton {
        private String text;
        private int textColor;
        private Drawable background;
        private Context context;

        public ItemButton(Context context) {
            this.context = context;
        }

        public ItemButton setText(String text) {
            this.text = text;
            return this;
        }
        public ItemButton setTextColor(int colorResId) {
            textColor = colorResId;
            return this;
        }

        public ItemButton setBackground(int resid) {
            this.background = context.getResources().getDrawable(resid);
            return this;
        }

        public ItemButton setBackground(Drawable drawable) {
            this.background = drawable;
            return this;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
