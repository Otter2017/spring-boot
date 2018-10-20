package org.springframework.boot.peach.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "socketservice")
public class SocketServiceConfig {
    private int serverPort=6000;
    private int serverSleepInterval = 2000;
    private String serverIP="127.0.0.1";

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerSleepInterval() {
        return serverSleepInterval;
    }

    public void setServerSleepInterval(int serverSleepInterval) {
        this.serverSleepInterval = serverSleepInterval;
    }
}
