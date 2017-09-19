package org.artemir.ssh.client;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public interface Auth {
    Session createSession(String host, int port) throws JSchException;
}
