# API Request Client

### API

```
    RequestContext request = Client.request();
    
    ResponseContext response = request.get()
    ResponseContext response = request.post()
    ResponseContext response = request.put()
    ResponseContext response = request.delete()
    
```

### Usage

```java

public class ClientTest {
    @Test
    public void request() {
        RequestContext request = Client.request("api.weixin.qq.com");

        // 小程序登录
        request.setParam("appid", "APPID");
        request.setParam("secret", "SECRET");
        request.setParam("js_code", "JSCODE");
        request.setParam("null", null);
        request.setParam("grant_type", "authorization_code");
        ResponseContext response = request.get("/sns/jscode2session");
        response.debugMessage("小程序登录");

        request.clearParam();

        // 下发小程序和公众号统一的服务消息
        request.setParam("access_token", "ACCESS_TOKEN");
        ResponseContext response2 = request.post("/cgi-bin/message/wxopen/template/uniform_send");
        response2.debugMessage("下发小程序和公众号统一的服务消息");
    }
}
```
