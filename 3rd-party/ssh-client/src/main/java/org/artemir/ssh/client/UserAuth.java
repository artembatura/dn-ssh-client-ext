package org.artemir.ssh.client;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class UserAuth implements Auth {
    private String pass, user;

    public UserAuth(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.pass = password;
    }

    @Override
    public Session createSession(String host, int port) throws JSchException {
        Session s  = new JSch().getSession(user, host, port);
        if (pass != null) {
            s.setPassword(pass);
        }
        return s;
    }
}
