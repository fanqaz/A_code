package cn.com.geekplus.app.rf.api.base;

public class Result<T> {
	public int code;
	public String msg;
	public boolean succ;
	public T data;

	public Result() {
	}

	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
