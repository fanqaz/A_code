package cn.com.geekplus.app.rf.api;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;

import cn.com.geekplus.app.rf.api.base.BaseApi;
import cn.com.geekplus.app.rf.api.base.CookieStore;
import cn.com.geekplus.app.rf.api.base.INetCallback;
import cn.com.geekplus.app.rf.modle.PickSheet;
import cn.com.geekplus.app.rf.modle.Warehouse;
import okhttp3.CookieJar;

public class ApiUtils {

    /**
     * get
     * @param callback
     */
    public static void warehouse(INetCallback<List<Warehouse>> callback) {
        BaseApi api = new BaseApi<List<Warehouse>>(ApiPaths.WAREHOUSE) {
            @Override
            public int requestType() {
                return GET;
            }

            @Override
            public Type getResultType() {
                return fromJsonArray(Warehouse.class);
            }
        };
        api.setCallback(callback);
        api.execute();
    }

    /**
     * form
     * @param userName
     * @param password
     * @param rfId
     * @param callback
     */
    public static void login(final String userName, final String password,final String rfId, INetCallback<UserResultData> callback) {
        BaseApi api = new BaseApi<UserResultData>(ApiPaths.LOGIN) {
            @Override
            public int requestType() {
                CookieJar cookieJar = sClient.cookieJar();
                if (cookieJar != null && cookieJar instanceof CookieStore) {
                    ((CookieStore) cookieJar).clear();
                }
                return POST_FORM;
            }

            @Override
            public LinkedHashMap<String, String> getRequestParams() {
                LinkedHashMap<String, String> map =  super.getRequestParams();
                map.put("username", userName);
                map.put("password", password);
                map.put("rfId", rfId);

                return map;
            }

            @Override
            public Type getResultType() {
                return fromJsonObject(UserResultData.class);
            }
        };
        api.setCallback(callback);
        api.execute();
    }
    public static class UserResultData {
        public String sessionId;
    }

    public static void sysparamQuery(INetCallback<Object> callback) {
        BaseApi api = new BaseApi<Object>(ApiPaths.SYSPARAMQUERY) {

            @Override
            public int requestType() {
                return GET;
            }

            @Override
            public Type getResultType() {
                return fromJsonObject(Object.class);
            }
        };
        api.setCallback(callback);
        api.execute();
    }


    /**
     * post
     * @param orderNo
     * @param pickWay
     * @param callback
     */
    public static void getPickTask(final String orderNo,
                                   final String pickWay,
                                   INetCallback<List<PickSheet>> callback) {
        BaseApi api = new BaseApi<List<PickSheet>>(ApiPaths.GET_PICK_TASK) {

            @Override
            public LinkedHashMap<String, String> getRequestParams() {
                LinkedHashMap<String, String> params =  super.getRequestParams();
                params.put("orderNo", orderNo);
                params.put("type", pickWay);
                return params;
            }

            @Override
            public Type getResultType() {
                return fromJsonArray(PickSheet.class);
            }

        };
        api.setCallback(callback);
        api.execute();
    }

}
