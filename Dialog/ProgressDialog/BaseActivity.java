package cn.com.geekplus.app.rf.module.basic;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import cn.com.geekplus.app.rf.modle.TitleBarClickCallback;
import cn.com.geekplus.app.rf.widgets.DialogWrapper;
import cn.com.geekplus.app.rf.widgets.DialogWrapperUtil;

public class BaseActivity<VM extends ViewModel, B extends ViewDataBinding> extends AppCompatActivity implements TitleBarClickCallback{

    protected static final int REQUEST_CODE_PERMISSION = 10000;

    VM viewModel;
    B dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class<VM> vmClass = initViewModelClass();
        if (vmClass != null){
            viewModel = ViewModelProviders.of(this).get(vmClass);
        }

        int layoutID = getLayoutId();
        if (layoutID != 0) {
            dataBinding = DataBindingUtil.setContentView(this, layoutID);
            initBinding(dataBinding);
        }
    }

    protected Class<VM> initViewModelClass() {
        return null;
    }

    protected int getLayoutId() {
        return 0;
    }
    protected void initBinding(B dataBinding) {}

    public VM getViewModel() {
        return viewModel;
    }

    public B getDataBinding() {
        return dataBinding;
    }

    public boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(String permission) {
        if (!hasPermission(permission)) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_CODE_PERMISSION);
        } else {
            permissionGranted(permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted(permissions[i]);
            } else {
                permissionDenied(permissions[i]);
            }
        }
    }

    protected void permissionGranted(String permission) {
    }
    protected void permissionDenied(String permission) {
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
    public void runDelay(Runnable runnable, long delay) {
        getWindow().getDecorView().postDelayed(runnable,delay);
    }


    public void clearCurrentFocus() {
        if (getCurrentFocus()!= null) getCurrentFocus().clearFocus();
    }

    public void requestFocus(final View v) {
        runDelay(new Runnable() {
            @Override
            public void run() {
                v.requestFocus();
            }
        }, 50);
    }

    DialogWrapper loading;

    public void showLoading() {
        showLoading(null);
    }
    public void showLoading(String message) {
        if (loading == null) {
            loading = DialogWrapperUtil.load(this, message);
        } else {
            if (TextUtils.isEmpty(message)) {
                loading.setMessage(message);
            }
            loading.show();
        }
    }

    public void dismissLoading() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }


}
