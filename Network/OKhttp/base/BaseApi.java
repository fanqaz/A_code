package cn.com.geekplus.app.rf.api.base;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.com.geekplus.app.rf.BuildConfig;
import cn.com.geekplus.app.rf.Constants;
import cn.com.geekplus.app.rf.GKApplication;
import cn.com.geekplus.app.rf.modle.PickFetch;
import cn.com.geekplus.app.rf.utils.PreferenceUtil;
import cn.com.geekplus.app.rf.utils.type.TypeBuilder;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class BaseApi<T> {

	public static final int GET = 1;
	public static final int POST_JSON = 2;
	public static final int POST_FORM = 3;


	public static MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");

	protected static OkHttpClient sClient;
	protected static Gson GSON = new Gson();
	static {
		sClient = new OkHttpClient().newBuilder()
				.connectTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(30, TimeUnit.SECONDS)
				.cookieJar(new CookieStore())
				.build();
	}

	protected String method;
	protected Call call;
	protected BaseResponse response;

	@SuppressLint("HandlerLeak")
	public BaseApi(String method) {
		this.method = method;
		response = new BaseResponse(method){

			@Override
			public Result<T> parseData(String json) {
				return GSON.fromJson(json, getResultType());
			}
		};
	}

	public void setCallback(INetCallback<T> callback) {
		if (response != null ) {
			response.setCallback(callback);
		}
	}

	public void setResponse(BaseResponse response) {
		this.response = response;
	}

	public LinkedHashMap<String, String> getRequestParams() {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("sessionId", PreferenceUtil.getSessionId(GKApplication.getInst()));
		params.put("rfCode", Constants.ID);
		return params;
	}

	/**
	 * 如果需要上传图片，则需要把图片路径放在此方法中
	 */
	public HashMap<String, String> getFileParams() {
		return new HashMap<String, String>();
	}

	/**
	 * 可以上传二进制数据
 	 */
	public HashMap<String, byte[]> getFileBytesParams() {
		return new HashMap<String, byte[]>();
	}

	public void execute() {
		RequestBody body;
		HashMap<String, String> files = getFileParams();
		HashMap<String, byte[]> bytes = getFileBytesParams();

		if (requestType() == GET) {
			HashMap<String, String> params = getRequestParams();
			get(method, params);
		} else if(requestType() == POST_JSON){

			HashMap<String, String> params = getRequestParams();

			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			String json = gson.toJson(params);
			//MediaType  设置Content-Type 标头中包含的媒体类型值
			body = FormBody.create(MediaType.parse("application/json")
					, json);
			post(method, body);
		} else if (!files.isEmpty() || !bytes.isEmpty()) {
			MultipartBody.Builder builder = new MultipartBody.Builder();
			for (String key : files.keySet()) {
				if (!TextUtils.isEmpty(files.get(key))) {
					File file = new File(files.get(key));
					builder.addFormDataPart(key, file.getName(), RequestBody.create(MEDIA_TYPE, file));
				}
			}
			for (String key : bytes.keySet()) {
				if (bytes.get(key) != null) {
					builder.addFormDataPart(key, "", RequestBody.create(MEDIA_TYPE, bytes.get(key)));
				}
			}

			HashMap<String, String> params = getRequestParams();
			for (String key : params.keySet()) {
				String value = params.get(key);
				value = TextUtils.isEmpty(value) ? "": value;
				builder.addFormDataPart(key, value);
			}
			builder.setType(MultipartBody.FORM);
			body = builder.build();
			post(method, body);
		} else {
			HashMap<String, String> params = getRequestParams();
			FormBody.Builder builder = new FormBody.Builder();
			for (String key : params.keySet()) {
				String value = params.get(key);
				value = TextUtils.isEmpty(value) ? "": value;
				builder.add(key, value);
			}
			body = builder.build();
			post(method, body);
		}

	}
	/**
	 *  发送请求
	 * @param method
	 */
	protected void get(String method, HashMap<String, String> queryParams) {

		//String url = Constants.Host;
		StringBuilder url = new StringBuilder(Constants.Host);
		if (!TextUtils.isEmpty(method) && !method.startsWith("http")) {
			url.append(method);
		}else {
			url.setLength(0);
			url.append(method);
		}

		for (String key : queryParams.keySet()) {
			String value = queryParams.get(key);
			value = TextUtils.isEmpty(value) ? "": value;
			if (url.indexOf("?") == -1) {
				url.append("?");
			} else {
				url.append("&");
			}
			url.append(key).append("=").append(value);
		}

		Request request = new Request.Builder()
				.url(url.toString())
				.get()
				.build();
		call = sClient.newCall(request);
		call.enqueue(response);

		if (BuildConfig.DEBUG) {
			Log.i("http params", getRequestParams().toString());
			Log.i("http url", url.toString());
		}
	}
	/**
	 *  发送请求
	 * @param method
	 * @param body
	 */
	protected void post(String method, RequestBody body) {

		String url = Constants.Host;
		if (!TextUtils.isEmpty(method) && !method.startsWith("http")) {
			url += method;
		}else {
			url = method;
		}

		Request request = new Request.Builder()
				.url(url)
//				.addHeader("Accept", "application/json")
//				.addHeader("Content-Type", "application/json")
				.post(body)
				.build();
		call = sClient.newCall(request);
		call.enqueue(response);

		if (BuildConfig.DEBUG) {
			Log.i("http params", getRequestParams().toString());
			Log.i("http url", url);
		}
	}

	/**
	 * 取消请求
	 */
	public void cancelRequest() {
		if (call != null && !call.isCanceled() ) {
			call.cancel();
		}
	}

	/**
	 * 修改请求方式，子类需要覆盖此方法，默认 {@link #POST_JSON}
	 * @see  {@link #GET} {@link #POST_FORM}
	 * @return
	 */
	public int requestType() {
		return POST_JSON;
	}

	public abstract Type getResultType();

	/**
	 * @param clazz
	 * @return Result<List<T>>
	 */
	public Type fromJsonArray(Class clazz) {
		Type type = TypeBuilder
				.newInstance(Result.class)
				.beginSubType(List.class)
				.addTypeParam(clazz)
				.endSubType()
				.build();
		return type;
	}

	/**
	 * @param clazz
	 * @return Result<T>
	 */
	public Type fromJsonObject(Class<T> clazz) {
		Type type = TypeBuilder
				.newInstance(Result.class)
				.addTypeParam(clazz)
				.build();
		return type;
	}

	public static class Builder {
		Class<?> clz;
		String method;
		int type = POST_JSON;
		int formJson;
		INetCallback<?> callback;
		Map<String, String> params;


		public Builder() {
		}
		public Builder method(String method) {
			this.method = method;
			return this;
		}

		public Builder formJsonArray(Class<?> cls) {
			this.clz= cls;
			formJson = 2;
			return this;
		}
		public Builder formJsonObject(Class<?> cls) {
			this.clz= cls;
			formJson = 1;
			return this;
		}

		public Builder postJson() {
			type = POST_JSON;
			return this;
		}
		public Builder get() {
			type = GET;
			return this;
		}

		public Builder postForm() {
			type = POST_FORM;
			return this;
		}

		public Builder addParams(String key, String value) {
			if (params == null) {
				params = new HashMap<>();
			}
			params.put(key, value);
			return this;
		}

		public Builder callback(INetCallback<?> callback) {
			this.callback = callback;
			return this;
		}

		public <T> BaseApi<T> build() {
			BaseApi api = new BaseApi<T>(method) {
				@Override
				public LinkedHashMap<String, String> getRequestParams() {
					LinkedHashMap<String, String> requestParams = super.getRequestParams();
					if (params != null && !params.isEmpty()) {
						requestParams.putAll(params);
					}
					return requestParams;
				}

				@Override
				public Type getResultType() {
					return formJson == 1 ? fromJsonObject((Class<T>) clz) : fromJsonArray(clz);
				}
				@Override
				public int requestType() {
					return type;
				}
			};
			api.setCallback(callback);
			return api;
		}
	}
}
