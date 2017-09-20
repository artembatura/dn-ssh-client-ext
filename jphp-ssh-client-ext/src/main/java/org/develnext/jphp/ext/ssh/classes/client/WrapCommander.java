package org.develnext.jphp.ext.ssh.classes.client;

import org.artemir.ssh.client.Commander;

import org.develnext.jphp.ext.ssh.SshClientExtension;
import php.runtime.Memory;
import php.runtime.env.Environment;
import php.runtime.lang.BaseWrapper;
import php.runtime.reflection.ClassEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static php.runtime.annotation.Reflection.*;

@Abstract
@Name("Commander")
@Namespace(SshClientExtension.NS)
public class WrapCommander extends BaseWrapper<Commander> {
    public interface WrappedInterface {
        void exec(String command, String consoleCharset) throws IOException;
        void execWithSudo(String command, String password, String consoleCharset) throws IOException;
        void end();
    }

    @Signature(@Arg(value = "toCharset", optional = @Optional("null")))
    byte[] getLastOutput(Memory toCharset) throws UnsupportedEncodingException {
        if (toCharset == Memory.NULL) {
            return getWrappedObject().getLastOutput(null);
        }
        if (!toCharset.isString()) {
            throw new UnsupportedEncodingException("Invalid type argument $toCharset. Need: String");
        }
        return getWrappedObject().getLastOutput(toCharset.toString());
    }

    @Signature(@Arg(value = "toCharset", optional = @Optional("null")))
    byte[] getLastError(Memory toCharset) throws UnsupportedEncodingException {
        if (toCharset == Memory.NULL) {
            return getWrappedObject().getLastError(null);
        }
        if (!toCharset.isString()) {
            throw new UnsupportedEncodingException("Invalid type argument $toCharset. Need: String");
        }
        return getWrappedObject().getLastError(toCharset.toString());
    }

    public WrapCommander(Environment env, Commander object) {
        super(env, object);
    }

    public WrapCommander(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }
}