/*
 * +---------------------------------------------------------
 * | Author Jared.Yan<yanhuaiwen@163.com>
 * +---------------------------------------------------------
 * | Copyright (c) http://cmsen.com All rights reserved.
 * +---------------------------------------------------------
 */
package cn.databytes.client;

import java.net.URL;
import java.util.List;
import java.util.Map;

public interface ResponseContext {
    boolean isSuccess();

    boolean isSuccess(int status);

    int getStatus();

    String getMessage();

    StringBuilder getContent();

    Map<String, String> getHeaders();

    String getHeaders(String name);

    String getHeaders(String name, String defaultValue);

    List<String> getCookies();

    URL getRequestUrl();

    long getResponseTime();

    void debugMessage();
}
