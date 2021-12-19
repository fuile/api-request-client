/*
 * +---------------------------------------------------------
 * | Author Jared.Yan<yanhuaiwen@163.com>
 * +---------------------------------------------------------
 * | Copyright (c) http://cmsen.com All rights reserved.
 * +---------------------------------------------------------
 */
package cn.databytes.client;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientRequest extends Request implements RequestContext {

    private int redirectNum = 0;
    private final String boundaryName = UUID.randomUUID().toString();
    private ClientResponse response;

    public ClientRequest() {
        setHost(getHostAddress());
    }

    protected ClientRequest(RequestConfig config) {
        enableSSL(config.enableSSL());
        setHost(config.getHost());
        setPort(config.getPort());
        setTimeOut(config.getTimeOut());
        setX509Certificates(config.getX509Certificates());
        setCharacterEncoding(config.getCharacterEncoding());
    }

    protected ClientRequest(int port) {
        setHost(getHostAddress());
        setPort(port);
    }

    protected ClientRequest(int port, boolean ssl) {
        enableSSL(ssl);
        setHost(getHostAddress());
        setPort(port);
    }

    protected ClientRequest(String host) {
        setHost(host);
    }

    protected ClientRequest(String host, boolean ssl) {
        enableSSL(ssl);
        setHost(host);
    }

    protected ClientRequest(String host, int port) {
        setHost(host);
        setPort(port);
    }

    protected ClientRequest(String host, int port, boolean ssl) {
        enableSSL(ssl);
        setHost(host);
        setPort(port);
    }

    public ResponseContext get() {
        return request(null);
    }

    public ResponseContext get(String path) {
        return request(path);
    }

    public ResponseContext post() {
        return post(null);
    }

    public ResponseContext post(String path) {
        return request(path, RequestMethod.POST);
    }

    public ResponseContext put() {
        return put(null);
    }

    public ResponseContext put(String path) {
        return request(path, RequestMethod.PUT);
    }

    public ResponseContext delete() {
        return delete(null);
    }

    public ResponseContext delete(String path) {
        return request(path, RequestMethod.DELETE);
    }

    public ResponseContext request(String path) {
        setPath(path);
        setMethod(RequestMethod.GET);
        return request();
    }

    public ResponseContext request(String path, String method) {
        setPath(path);
        setMethod(method.toUpperCase());
        return request();
    }

    private ResponseContext request() {
        HttpURLConnection connect = null;
        long startTime = currentTime();
        try {
            connect = connect();
            if (!RequestMethod.GET.equalsIgnoreCase(getMethod())) {
                DataOutputStream dos = new DataOutputStream(connect.getOutputStream());
                if (!getForm().isEmpty()) {
                    int i = 1;
                    int paramSize = getParam().size();
                    for (Map.Entry<String, Object> entry : getForm().entrySet()) {
                        dos.writeBytes(entry.getKey());
                        dos.writeBytes("=");
                        dos.writeBytes(entry.getValue().toString());
                        if (i < paramSize) {
                            dos.writeBytes("&");
                        }
                        i++;
                    }
                } else if (getBodyData() != null) {
                    if (getBodyData() instanceof String) {
                        dos.writeBytes(getStreamAsString());
                    } else if (getBodyData() instanceof File) {
                        writeBytes(new FileInputStream(getStreamAsFile()), dos);
                    } else if (getBodyData() instanceof byte[]) {
                        dos.write(getStreamAsBytes());
                    } else if (getBodyData() instanceof InputStream) {
                        writeBytes(getStreamAsInputStream(), dos);
                    } else {
                        throw new RequestException("setStream ");
                    }
                } else if (!getFormData().isEmpty()) {
                    for (RequestFormData form : getFormData()) {
                        dos.writeBytes("------" + boundaryName + "\r\n");
                        if (form.getFilename() != null) {
                            dos.writeBytes("Content-Disposition: form-data; name=\"" + form.getName() + "\"; filename=\"" + form.getFilename() + "\"\r\n");
                        } else {
                            dos.writeBytes("Content-Disposition: form-data; name=\"" + form.getName() + "\"\r\n");
                        }
                        if (form.getFileType() != null) {
                            dos.writeBytes("Content-Type: " + form.getFileType() + "\r\n");
                        }
                        dos.writeBytes("\r\n");
                        if (form.getData() instanceof String) {
                            dos.writeBytes(form.getDataAsString());
                        } else if (form.getData() instanceof File) {
                            writeBytes(new FileInputStream(form.getDataAsFile()), dos);
                        } else if (form.getData() instanceof byte[]) {
                            dos.write(form.getDataAsBytes());
                        } else if (form.getData() instanceof InputStream) {
                            writeBytes(form.getDataAsStream(), dos);
                        }
                        dos.writeBytes("\r\n");
                    }
                    dos.writeBytes("------" + boundaryName + "--\r\n");
                }
                dos.flush();
                dos.close();
            }
            int responseCode = connect.getResponseCode();
            this.response.setStatus(responseCode);
            this.response.setMessage(connect.getResponseMessage());
            if (redirectNum <= 4 && isFollowRedirect() && (responseCode == ResponseStatus.MOVED_TEMP || responseCode == ResponseStatus.MOVED_PERM)) {
                String location = connect.getHeaderField("location");
                if (location != null && location.length() > 0) {
                    setUrl(location);
                    connect.disconnect();
                    ++redirectNum;
                    return request();
                }
            }
            readResponseHeader(connect.getHeaderFields());
            InputStream inputStream = connect.getInputStream();
            if (responseCode >= ResponseStatus.BAD_REQUEST) {
                inputStream = connect.getErrorStream();
            }
            readResponseBody(inputStream);
            connect.disconnect();
        } catch (Exception e) {
            if (connect != null) {
                connect.disconnect();
            }
            this.response.setMessage(e.getMessage());
            this.response.setStatus(-1);
            throw new RequestException(e);
        } finally {
            this.response.setResponseTime(currentTime() - startTime);
        }
        redirectNum = 0;
        return this.response;
    }

    private HttpURLConnection connect() throws Exception {
        URL url = getUrl() == null ? new URL(getProtocol(), getHost(), getPort(), getPath()) : getUrl();
        this.response = new ClientResponse(this, url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if ("HTTPS".equals(getProtocol()) && getX509Certificates() != null) {
            HttpsURLConnection https = (HttpsURLConnection) conn;
            https.setHostnameVerifier((hostname, session) -> true);
            https.setSSLSocketFactory(getX509Certificates());
        }
        conn.setRequestMethod(getMethod());
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(isCache());
        conn.setConnectTimeout(getTimeOut());
        conn.setReadTimeout(getTimeOut());
        conn.setInstanceFollowRedirects(isFollowRedirect());
        conn.setRequestProperty("User-Agent", getUserAgent());
        if (!getForm().isEmpty()) {
            conn.setRequestProperty("Content-Type", ContentEnctype.URLENCODED);
        } else if (!getFormData().isEmpty()) {
            conn.setRequestProperty("Content-Type", ContentEnctype.DATA + "; boundary=----" + boundaryName);
        }
        for (Map.Entry<String, String> head : getHeader().entrySet()) {
            conn.setRequestProperty(head.getKey(), head.getValue());
        }
        conn.connect();
        return conn;
    }

    private void readResponseHeader(Map<String, List<String>> headerFields) {
        for (Map.Entry<String, List<String>> header : headerFields.entrySet()) {
            if ("Set-Cookie".equalsIgnoreCase(header.getKey())) {
                this.response.setCookies(header.getValue());
                continue;
            }
            this.response.setHeaders(header.getKey(), header.getValue().get(0));
        }
    }

    private void readResponseBody(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, getCharacterEncoding());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String content;
        while ((content = bufferedReader.readLine()) != null) {
            this.response.setContent(content);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }

    private long currentTime() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    private String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    private void writeBytes(InputStream is, OutputStream dos) {
        try {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                dos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
