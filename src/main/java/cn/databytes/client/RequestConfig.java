/*
 * +---------------------------------------------------------
 * | Author Jared.Yan<yanhuaiwen@163.com>
 * +---------------------------------------------------------
 * | Copyright (c) http://cmsen.com All rights reserved.
 * +---------------------------------------------------------
 */
package cn.databytes.client;

import javax.net.ssl.SSLSocketFactory;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public interface RequestConfig {
    String getHost();

    default boolean enableSSL() {
        return true;
    }

    default int getPort() {
        return -1;
    }

    default int getTimeOut() {
        return 30000;
    }

    default SSLSocketFactory getX509Certificates() {
        try {
            return DefaultX509TrustManager.sslContext().getSocketFactory();
        } catch (NoSuchProviderException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    default Charset getCharacterEncoding() {
        return Charset.defaultCharset();
    }

}
