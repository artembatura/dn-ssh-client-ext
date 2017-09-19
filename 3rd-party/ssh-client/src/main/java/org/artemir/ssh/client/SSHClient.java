package org.artemir.ssh.client;

final public class SSHClient {
    private SSHClient() {}
    public static Connection connectWithPassword (String host, int port, String user, String pass) {
        UserAuth uAT = new UserAuth(user);
        uAT.setPassword(pass);
        return new Connection(host, port, uAT);
    }
}