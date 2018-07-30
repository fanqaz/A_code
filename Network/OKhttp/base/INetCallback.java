package cn.com.geekplus.app.rf.api.base;

public interface INetCallback<T> {
	void onSuccess(String sign, Result<T> resp);

	void onFail(String code, String msg);
}
