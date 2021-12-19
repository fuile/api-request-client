/*
 * +---------------------------------------------------------
 * | Author Jared.Yan<yanhuaiwen@163.com>
 * +---------------------------------------------------------
 * | Copyright (c) http://cmsen.com All rights reserved.
 * +---------------------------------------------------------
 */
package cn.databytes.client;

public interface ContentEnctype {
    String TEXT = "text/plain";
    String JSON = "application/json";
    String XML = "application/xml";
    String SOAP = "application/soap+xml";
    String DATA = "multipart/form-data";
    String STREAM = "application/octet-stream";
    String URLENCODED = "application/x-www-form-urlencoded";
}
