package org.develnext.jphp.ext.ssh.classes.client;

import org.artemir.ssh.client.SSHClient;
import org.develnext.jphp.ext.ssh.SshClientExtension;
import org.artemir.ssh.client.Connection;
import php.runtime.env.Environment;
import php.runtime.lang.BaseObject;
import php.runtime.reflection.ClassEntity;

import static php.runtime.annotation.Reflection.*;

@Name("SSHClient")
@Namespace(SshClientExtension.NS)
final public class WrapSSHClient extends BaseObject {
    public WrapSSHClient(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    @Signature
    private void  __construct() {
    }

    @Signature
    public static Connection connectWithPassword(String host, int port, String user, String pass) {
        return SSHClient.connectWithPassword(host, port, user, pass);
    }
}