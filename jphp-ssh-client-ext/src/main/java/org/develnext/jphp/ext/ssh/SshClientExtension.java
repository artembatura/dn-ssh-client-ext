package org.develnext.jphp.ext.ssh;

import org.artemir.ssh.client.Commander;
import org.artemir.ssh.client.Connection;
import org.develnext.jphp.ext.ssh.classes.client.WrapSSHClient;
import org.develnext.jphp.ext.ssh.classes.client.WrapCommander;
import org.develnext.jphp.ext.ssh.classes.client.WrapConnection;
import php.runtime.ext.support.Extension;
import php.runtime.env.CompileScope;


public class SshClientExtension extends Extension {
    public static final String NS = "php\\ssh\\client";

    public SshClientExtension() {}

    @Override
    public Status getStatus() {
        return Status.EXPERIMENTAL;
    }

    @Override
    public String[] getPackageNames() {
        return new String[] { "ssh", "ssh\client" };
    }

    @Override
    public void onRegister(CompileScope scope) {
        registerClass(scope, WrapSSHClient.class);
        registerWrapperClass(scope, Connection.class, WrapConnection.class);
        registerWrapperClass(scope, Commander.class, WrapCommander.class);
    }
}