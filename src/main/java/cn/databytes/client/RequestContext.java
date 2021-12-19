/*
 * +---------------------------------------------------------
 * | Author Jared.Yan<yanhuaiwen@163.com>
 * +---------------------------------------------------------
 * | Copyright (c) http://cmsen.com All rights reserved.
 * +---------------------------------------------------------
 */
package cn.databytes.client;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public interface RequestContext {
    ResponseContext get();

    ResponseContext get(String path);

    ResponseContext post();

    ResponseContext post(String path);

    ResponseContext put();

    ResponseContext put(String path);

    ResponseContext delete();

    ResponseContext delete(String path);

    ResponseContext request(String path);

    ResponseContext request(String path, String method);

    void enableSSL(boolean ssl);

    void setHost(String host);

    void setPort(int port);

    void setTimeOut(int timeOut);

    void setMethod(String method);

    void setFollowRedirect(boolean followRedirect);

    void setCache(boolean cache);

    void setPath(String path);

    void setHeader(Map<String, String> header);

    void setHeader(String name, String value);

    void setCookie(Map<String, String> cookie);

    void setCookie(String name, String value);

    void setParam(Map<String, Object> param);

    void setParam(String name, Object value);

    void setForm(Map<String, Object> form);

    void setForm(String name, Object value);

    void setFormData(List<RequestFormData> formData);

    void setFormData(RequestFormData formData);

    void setFormDataAsText(String name, String text);

    void setFormDataAsXml(String name, String text);

    void setFormDataAsSOAPXml(String name, String text);

    void setFormDataAsJson(String name, String text);

    void setFormDataAsFile(String name, File file);

    void setFormDataAsFile(String name, File file, String filename);

    void setFormDataAsFile(String name, File file, String filename, String fileType);

    void setFormDataAsBytes(String name, byte[] bytes, String filename);

    void setFormDataAsBytes(String name, byte[] bytes, String filename, String fileType);

    void setFormDataAsStream(String name, InputStream stream, String filename);

    void setFormDataAsStream(String name, InputStream stream, String filename, String fileType);

    void setBodyData(Object stream);

    void setBodyData(String stream);

    void setBodyData(byte[] stream);

    void setBodyData(File stream);

    void setBodyData(InputStream stream);

    void setUrl(URL url);

    void setUrl(String url);

    void setUserAgent(String value);

    void setContentType(String contentType);

    void setContentLength(int contentLength);

    void setContentLength(long contentLength);

    void setAuthorization(String token);

    void setAuthorization(String bearer, String token);

    Map<String, String> getHeader();

    Map<String, String> getCookie();

    Map<String, Object> getParam();

    Map<String, Object> getForm();

    Object getBodyData();

    List<RequestFormData> getFormData();

    void clearParam();

    void clearStream();

    void clearHeader();

    void clearCookie();

    void clearFormData();

}
