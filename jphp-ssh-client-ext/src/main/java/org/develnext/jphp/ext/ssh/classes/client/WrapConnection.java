package org.develnext.jphp.ext.ssh.classes.client;

import org.artemir.ssh.client.Commander;
import org.develnext.jphp.ext.ssh.SshClientExtension;
import org.artemir.ssh.client.Connection;
import php.runtime.env.Environment;
import php.runtime.lang.BaseWrapper;
import php.runtime.reflection.ClassEntity;

import java.io.IOException;

import static php.runtime.annotation.Reflection.*;

@Abstract
@Name("Connection")
@Namespace(SshClientExtension.NS)
public class WrapConnection extends BaseWrapper<Connection> {
    public interface WrappedInterface {
        Commander execute() throws IOException;
        String getHost();
        int getPort();
        Commander getCommander();
        void close();
    }

    public WrapConnection(Environment env, Connection object) {
        super(env, object);
    }

    public WrapConnection(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }
}