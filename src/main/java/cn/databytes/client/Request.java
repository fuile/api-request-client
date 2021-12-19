/*
 * +---------------------------------------------------------
 * | Author Jared.Yan<yanhuaiwen@163.com>
 * +---------------------------------------------------------
 * | Copyright (c) http://cmsen.com All rights reserved.
 * +---------------------------------------------------------
 */
package cn.databytes.client;

import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public abstract class Request implements RequestConfig {

    private String protocol = "HTTPS";
    private String host;
    private int port = -1;
    private String path = "";
    private int timeOut = 30000;
    private String method = RequestMethod.GET;
    private boolean followRedirect = false;
    private boolean cache = false;
    private Map<String, String> header = new Hashtable<>();
    private Map<String, String> cookie = new Hashtable<>();
    private Map<String, Object> param = new LinkedHashMap<>();
    private Map<String, Object> form = new LinkedHashMap<>();
    private List<RequestFormData> formData = new LinkedList<>();
    private Object stream;
    private URL url;
    private Charset characterEncoding = Charset.defaultCharset();
    private SSLSocketFactory x509Certificates;
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) YHW/1.0 Chrome/78.0.3904.108 Safari/537.36";

    public String getProtocol() {
        return protocol;
    }

    public void enableSSL(boolean ssl) {
        this.protocol = ssl ? "HTTPS" : "HTTP";
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isFollowRedirect() {
        return followRedirect;
    }

    public void setFollowRedirect(boolean followRedirect) {
        this.followRedirect = followRedirect;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public String getPath() {
        if (!this.param.isEmpty()) {
            StringBuilder _param = new StringBuilder();
            for (Map.Entry<String, Object> entry : this.param.entrySet()) {
                if (entry.getKey() == null || "".equals(entry.getKey())) {
                    continue;
                }
                _param.append("&").append(entry.getKey()).append("=");
                if (entry.getValue() != null) {
                    _param.append(entry.getValue());
                }
            }
            if (_param.length() > 0) {
                if (!this.path.contains("?")) {
                    _param.replace(0, 1, "?");
                }
                this.setPath(this.path + _param.toString());
            }
        }
        return this.path;
    }

    public void setPath(String path) {
        this.path = path == null ? "" : path;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        if (header == null) {
            header = new Hashtable<>();
        }
        this.header = header;
    }

    public void setHeader(String name, String value) {
        if (!"".equals(name)) {
            this.header.put(name, value);
        }
    }

    public Map<String, String> getCookie() {
        return cookie;
    }

    public void setCookie(Map<String, String> cookie) {
        if (cookie == null) {
            cookie = new Hashtable<>();
        }
        this.cookie = cookie;
    }

    public void setCookie(String name, String value) {
        if (!"".equals(name)) {
            this.cookie.put(name, value);
        }
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        if (param == null) {
            param = new LinkedHashMap<>();
        }
        this.param = param;
    }

    public void setParam(String name, Object value) {
        if (name != null && !"".equals(name)) {
            this.param.put(name, value);
        }
    }

    public List<RequestFormData> getFormData() {
        return formData;
    }

    public void setFormData(List<RequestFormData> formData) {
        if (formData == null) {
            formData = new LinkedList<>();
        }
        this.formData = formData;
    }

    public Map<String, Object> getForm() {
        return form;
    }

    public void setForm(Map<String, Object> form) {
        if (form == null) {
            form = new LinkedHashMap<>();
        }
        this.form = form;
    }

    public void setForm(String name, Object value) {
        this.form.put(name, value);
    }

    public void setFormData(RequestFormData formData) {
        this.formData.add(formData);
    }

    public void setFormDataAsText(String name, String text) {
        this.formData.add(new RequestFormData(name, text, ContentEnctype.TEXT));
    }

    public void setFormDataAsXml(String name, String text) {
        this.formData.add(new RequestFormData(name, text, ContentEnctype.XML));
    }

    public void setFormDataAsSOAPXml(String name, String text) {
        this.formData.add(new RequestFormData(name, text, ContentEnctype.SOAP));
    }

    public void setFormDataAsJson(String name, String text) {
        this.formData.add(new RequestFormData(name, text, ContentEnctype.JSON));
    }

    public void setFormDataAsFile(String name, File file) {
        this.setFormDataAsFile(name, file, file.getName());
    }

    public void setFormDataAsFile(String name, File file, String filename) {
        this.setFormDataAsFile(name, file, filename, ContentEnctype.STREAM);
    }

    public void setFormDataAsFile(String name, File file, String filename, String fileType) {
        this.formData.add(new RequestFormData(name, file, filename, fileType));
    }

    public void setFormDataAsBytes(String name, byte[] bytes, String filename) {
        this.setFormDataAsBytes(name, bytes, filename, ContentEnctype.STREAM);
    }

    public void setFormDataAsBytes(String name, byte[] bytes, String filename, String fileType) {
        this.formData.add(new RequestFormData(name, bytes, filename, fileType));
    }

    public void setFormDataAsStream(String name, InputStream stream, String filename) {
        this.setFormDataAsStream(name, stream, filename, ContentEnctype.STREAM);
    }

    public void setFormDataAsStream(String name, InputStream stream, String filename, String fileType) {
        this.formData.add(new RequestFormData(name, stream, filename, fileType));
    }

    public Object getBodyData() {
        return stream;
    }

    public String getStreamAsString() {
        return (String) stream;
    }

    public byte[] getStreamAsBytes() {
        return (byte[]) stream;
    }

    public File getStreamAsFile() {
        return (File) stream;
    }

    public InputStream getStreamAsInputStream() {
        return (InputStream) stream;
    }

    public void setBodyData(Object stream) {
        this.stream = stream;
    }

    public void setBodyData(String string) {
        this.stream = string;
    }

    public void setBodyData(byte[] bytes) {
        this.stream = bytes;
    }

    public void setBodyData(File file) {
        this.stream = file;
    }

    public void setBodyData(InputStream stream) {
        this.stream = stream;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new RequestException(e);
        }
    }

    @Override
    public Charset getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(Charset characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    @Override
    public SSLSocketFactory getX509Certificates() {
        return x509Certificates;
    }

    public void setX509Certificates(SSLSocketFactory x509Certificates) {
        this.x509Certificates = x509Certificates;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String value) {
        this.userAgent = value;
    }

    public void setContentType(String contentType) {
        setHeader("Content-Type", contentType);
    }

    public void setContentLength(int contentLength) {
        setHeader("Content-Length", String.valueOf(contentLength));
    }

    public void setContentLength(long contentLength) {
        setHeader("Content-Length", String.valueOf(contentLength));
    }

    public void setAuthorization(String token) {
        setHeader("Authorization", token);
    }

    public void setAuthorization(String bearer, String token) {
        setHeader("Authorization", bearer + " " + token);
    }

    public void clearParam() {
        this.cookie.clear();
    }

    public void clearStream() {
        this.stream = new byte[0];
    }

    public void clearHeader() {
        this.header.clear();
    }

    public void clearCookie() {
        this.cookie.clear();
    }

    public void clearFormData() {
        this.formData.clear();
    }
}
