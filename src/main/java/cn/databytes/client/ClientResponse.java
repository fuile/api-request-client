/*
 * +---------------------------------------------------------
 * | Author Jared.Yan<yanhuaiwen@163.com>
 * +---------------------------------------------------------
 * | Copyright (c) http://cmsen.com All rights reserved.
 * +---------------------------------------------------------
 */
package cn.databytes.client;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientResponse implements ResponseContext {
    private int status;
    private String message;
    private StringBuilder content;
    private Map<String, String> headers;
    private List<String> cookies;
    private long responseTime;
    private ClientRequest request;
    private URL requestUrl;

    public ClientResponse() {
    }

    public ClientResponse(ClientRequest request, URL requestUrl) {
        this.request = request;
        this.requestUrl = requestUrl;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public StringBuilder getContent() {
        return content;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeaders(String name) {
        return headers.getOrDefault(name, null);
    }

    public String getHeaders(String name, String defaultValue) {
        return headers.getOrDefault(name, defaultValue);
    }

    public List<String> getCookies() {
        return cookies;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setHeaders(String name, String value) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.put(name, value);
    }

    public void setContent(String content) {
        if (this.content == null) {
            this.content = new StringBuilder();
        }
        this.content.append(content);
    }

    public void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    public void setCookies(String value) {
        if (this.cookies == null) {
            this.cookies = new ArrayList<>();
        }
        this.cookies.add(value);
    }

    public URL getRequestUrl() {
        return this.requestUrl;
    }

    public boolean isSuccess() {
        return this.status == ResponseStatus.OK;
    }

    public boolean isSuccess(int status) {
        return this.status == status;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public long getResponseTime() {
        return this.responseTime;
    }

    public void debugMessage() {
        System.out.println("\033[0;38m" + new String(new char[200]).replace("\0", "-") + "\033[0m");
        println(31, "       Thread name", Thread.currentThread().getName());
        println(31, "         Thread id", Thread.currentThread().getId());
        println(31, "   Local date time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS")));
        println(33, "   Request headers", request.getHeader());
        println(33, "   Request cookies", request.getCookie());
        println(33, "     Request param", request.getParam());
        println(33, "      Request form", request.getForm());
        println(33, "      Request body", request.getBodyData());
        println(33, "  Request formData", request.getFormData());
        println(33, "Request target url", getRequestUrl());
        println(32, "     Response time", getResponseTime() + "ms");
        println(32, "   Response status", getStatus());
        println(32, "  Response message", getMessage());
        println(32, "  Response headers", getHeaders());
        println(32, "  Response cookies", getCookies());
        println(32, "  Response context", getContent());
        System.out.println("\033[0;38m" + new String(new char[200]).replace("\0", "-") + "\033[0m");
    }

    private void println(int color, String title, Object message) {
        System.out.println("\033[1;" + color + "m" + title + " : \033[0m\033[0;38m" + message + "\033[0m");
    }

    @Override
    public String toString() {
        return getContent().toString();
    }
}
