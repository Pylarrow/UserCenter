package com.idthk.network.http;

/**
 * 配置类
 */
public class Builder {
    private Class appClass;
    private String hostPath;

    private String clientSystemType;

    private String appVersion;

    public Class getAppClass() {
        return appClass;
    }

    public String getHostPath() {
        return hostPath;
    }

    public Builder setAppClass(Class appClass) {
        this.appClass = appClass;
        return this;
    }

    public Builder setHostPath(String hostPath) {
        this.hostPath = hostPath;
        return this;
    }


    public Builder setClientSystemType(String clientSystemType) {
        this.clientSystemType = clientSystemType;
        return this;
    }


    public Builder setAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public String getClientSystemType() {
        return clientSystemType;
    }

    public String getAppVersion() {
        return appVersion;
    }
}
