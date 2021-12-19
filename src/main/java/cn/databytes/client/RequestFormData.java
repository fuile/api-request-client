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

public class RequestFormData {
    private String name;
    private String filename;
    private String fileType;
    private Object data;

    public RequestFormData() {
    }

    public RequestFormData(String name, Object data, String fileType) {
        this(name, data, null, fileType);
    }

    public RequestFormData(String name, File data, String filename) {
        this(name, data, filename, null);
    }

    public RequestFormData(String name, byte[] data, String filename) {
        this(name, data, filename, null);
    }

    public RequestFormData(String name, InputStream data, String filename) {
        this(name, data, filename, null);
    }

    public RequestFormData(String name, Object data, String filename, String fileType) {
        this.name = name;
        this.filename = filename;
        this.fileType = fileType;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Object getData() {
        return data;
    }

    public File getDataAsFile() {
        return (File) data;
    }

    public byte[] getDataAsBytes() {
        return (byte[]) data;
    }

    public String getDataAsString() {
        return (String) data;
    }

    public InputStream getDataAsStream() {
        return (InputStream) data;
    }

    @Override
    public String toString() {
        return "RequestFormData{" +
                "name='" + name + '\'' +
                ", filename='" + filename + '\'' +
                ", fileType='" + fileType + '\'' +
                ", data=" + data +
                '}';
    }
}
