/*
 * +---------------------------------------------------------
 * | Author Jared.Yan<yanhuaiwen@163.com>
 * +---------------------------------------------------------
 * | Copyright (c) http://cmsen.com All rights reserved.
 * +---------------------------------------------------------
 */
package cn.databytes.client;

import org.junit.Test;

public class ClientTest {

    @Test
    public void request() {
        RequestContext request = Client.request("api.weixin.qq.com");
        request.setParam("appid", "APPID");
        request.setParam("secret", "SECRET");
        request.setParam("js_code", "JSCODE");
        request.setParam("null", null);
        request.setParam("grant_type", "authorization_code");
        ResponseContext response = request.get("/sns/jscode2session?u=0");
        response.debugMessage();
    }

}