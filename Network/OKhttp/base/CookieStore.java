package cn.com.geekplus.app.rf.api.base;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieStore implements CookieJar {
    List<Cookie> cookieList = new ArrayList<>();
    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        cookieList.addAll(list);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        return cookieList;
    }

    public void clear() {
        cookieList.clear();
    }
}
