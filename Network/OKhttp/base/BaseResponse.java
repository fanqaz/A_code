package cn.com.geekplus.app.rf.api.base;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import cn.com.geekplus.app.rf.BuildConfig;
import cn.com.geekplus.app.rf.GKApplication;
import cn.com.geekplus.app.rf.module.login.LoginActivity;
import okhttp3.Call;
import okhttp3.Callback;

public abstract class BaseResponse<T> extends Handler implements Callback {

    public static final int SUCCESS_MESSAGE = 0;
    public static final int FAILURE_MESSAGE = 1;

    public Class<T> t;
    protected Result<T> resp;
    protected Handler handler;
    protected String method;
    protected INetCallback<T> callback;

    public BaseResponse() {
        this(null);
    }
    public BaseResponse(String method) {
        super(Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper());
        this.method = method;
    }

    public BaseResponse setCallback(INetCallback<T> callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onFailure(Call var1, IOException var2) {
        Message message = Message.obtain();
        message.what = FAILURE_MESSAGE;
        message.obj = var2.getMessage();
        sendMessage(message);
    }

    @Override
    public void onResponse(Call var1, okhttp3.Response resp) throws IOException {
        String responseBody = resp.body().string();

        if (BuildConfig.DEBUG) {
            Log.i("http response", method + " == " + responseBody);
        }

        try {
            this.resp = parseData(responseBody);
            if (this.resp.code == 0) {
                sendMessage(Message.obtain(null, SUCCESS_MESSAGE, this.resp));
            } else {
                sendMessage(Message.obtain(null, FAILURE_MESSAGE, this.resp.msg));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(Message.obtain(null, FAILURE_MESSAGE, "数据解析发生错误"));
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch(msg.what) {
            case SUCCESS_MESSAGE:
                onSuccess((Result<T>) msg.obj);
                break;
            default:
                onFailure(msg.obj);
                break;
        }
    }

    public void onSuccess(Result<T> content) {
        if (callback != null) {
            callback.onSuccess(method, content);
        }
    }

    public void onFailure(Object message) {
        if (callback != null) {
            String msg = null;
            if (resp != null) {
                msg = resp.msg;
            } else if (message != null) {
                msg = message.toString();
            }
            String code = this.resp != null ? resp.code + "" : "-1";
            callback.onFail(code, msg);
        }

        if (this.resp != null && this.resp.code == 2) {
            //TODO 此时需要重新登录，至于是否需要清空其他的界面需要商讨
            Intent intent = new Intent(GKApplication.getInst(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            GKApplication.getInst().startActivity(intent);
        }

    }

    public abstract Result<T> parseData(String json);

}
