package org.artemir.ssh.client;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.nio.charset.Charset;

public class Commander {
    private ChannelExec cExec;
    private Session session;
    private InputStream stdOut, stdErr;
    private String lastErr, lastOut;
    public int maxWaitStdErr = 500, maxWaitStdOut = 1000;

    public Commander(Connection c) {
        session = c.getSession();
    }

    private void exec(String command) throws IOException {
        try {
            if (cExec != null && cExec.isConnected()) {
                cExec.disconnect();
                cExec = null;
            }

            cExec = (ChannelExec) session.openChannel("exec");
            cExec.setCommand(command);
            cExec.setInputStream(null);

            stdErr = cExec.getErrStream();
            stdOut = cExec.getInputStream();

            cExec.connect();
        } catch (JSchException e) {
            throw new IOException(e);
        }
    }

    private void execWithSudo(String command, String password) throws IOException {
        exec("sudo -S -p '' " + command);
        cExec.getOutputStream().write((password + "\n").getBytes());
        cExec.getOutputStream().flush();
    }

    public void exec(String command, String consoleCharset) throws IOException {
        exec(command);
        readResult(consoleCharset);
    }

    public void execWithSudo(String command, String password, String consoleCharset) throws IOException {
        execWithSudo(command, password);
        readResult(consoleCharset);
    }

    public void readResult(String charset) throws IOException {
        lastErr = readInputStream(stdErr, charset, maxWaitStdErr);
        lastOut = readInputStream(stdOut, charset, maxWaitStdOut);
    }

    private String readInputStream(InputStream stream, String charset, int maxWaitTimeData) throws IOException {
        if (stream == null) {
            throw new StreamCorruptedException("Stream is null");
        }

        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();

        byte[] tempBuffer = new byte[1024];
        long maxTime = System.currentTimeMillis() + maxWaitTimeData;

        while (true) {
            while (stream.available() > 0) {
                int i = stream.read(tempBuffer, 0, 1024);

                if (i < 0) {
                    break;
                }

                outputBuffer.write(tempBuffer, 0, i);
                maxTime = System.currentTimeMillis() + maxWaitTimeData;
            }

            if (cExec.isClosed() || System.currentTimeMillis() >= maxTime) {
                break;
            }
        }

        return outputBuffer.toString(charset);
    }

    public void end() {
        if (cExec != null) {
            cExec.disconnect();
        }
    }

    public byte[] getLastError(String toCharset) throws UnsupportedEncodingException {
        if (toCharset == null) {
            toCharset = Charset.defaultCharset().toString();
        }

        return lastErr.getBytes(toCharset);
    }

    public byte[] getLastOutput(String toCharset) throws UnsupportedEncodingException {
        if (toCharset == null) {
            toCharset = Charset.defaultCharset().toString();
        }

        return lastOut.getBytes(toCharset);
    }
}
