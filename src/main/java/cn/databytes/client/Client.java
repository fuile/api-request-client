/*
 * +---------------------------------------------------------
 * | Author Jared.Yan<yanhuaiwen@163.com>
 * +---------------------------------------------------------
 * | Copyright (c) http://cmsen.com All rights reserved.
 * +---------------------------------------------------------
 */
package cn.databytes.client;

public abstract class Client {

    public static RequestContext request() {
        return new ClientRequest();
    }

    public static RequestContext request(int port) {
        return new ClientRequest(port);
    }

    public static RequestContext request(int port, boolean ssl) {
        return new ClientRequest(port, ssl);
    }

    public static RequestContext request(String host) {
        return new ClientRequest(host);
    }

    public static RequestContext request(String host, int port) {
        return new ClientRequest(host, port);
    }

    public static RequestContext request(String host, int port, boolean ssl) {
        return new ClientRequest(host, port, ssl);
    }

    public static RequestContext request(RequestConfig config) {
        return new ClientRequest(config);
    }
}
