package org.artemir.ssh.client;

import com.jcraft.jsch.*;

public class Connection {
    private String host;
    private int port;
    private Session session;
    private Auth auth;
    private Commander commander;

    public Connection(String address, int port, Auth auth) {
        this.host = address;
        this.port = port;
        this.auth = auth;
    }

    public Commander execute() throws JSchException {
        if (session == null) {
            session = auth.createSession(host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            commander = new Commander(this);
        }

        if (!session.isConnected()) {
            session.connect();
        }

        return commander;
    }

    public Session getSession() {
        return session;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Auth getAuth() {
        return auth;
    }

    public Commander getCommander() {
        return commander;
    }

    public void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
