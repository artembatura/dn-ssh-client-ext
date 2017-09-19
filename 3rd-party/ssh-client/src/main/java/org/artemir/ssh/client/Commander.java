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
    /*
    public static class CommandPromptOutput {
        public static abstract class Message {
            private String msg;
            Message(String msg) {
                this.msg = msg;
            }
            public String getMessage() {
                return this.msg;
            }
            @Override
            public String toString() {
                return getMessage();
            }
        }

        public static class ErrorMessage extends Message {
            ErrorMessage(String msg) {
                super(msg);
            }
        }

        public static class OutputMessage extends Message {
            OutputMessage(String msg) {
                super(msg);
            }
        }

        public CommandPromptOutput(Result result, Message[] messageCollection) {
            this.result = result;
            this.messages = messageCollection;
        }

        public CommandPromptOutput(Message[] messageCollection) {
            this.messages = messageCollection;
            for (Message msg : messages) {
                if (msg instanceof ErrorMessage) {
                    if (this.result == null) {
                        this.result = Result.ERRORS;
                    }
                    if (this.result == Result.DONE) {
                        this.result = Result.DONE_WITH_ERRORS;
                    }
                }
                if (msg instanceof OutputMessage) {
                    if (this.result == null) {
                        this.result = Result.DONE;
                    }
                    if (this.result == Result.ERRORS) {
                        this.result = Result.DONE_WITH_ERRORS;
                    }
                }
            }
            if (this.result == null) {
                this.result = Result.UNKNOWN;
            }
        }

        public enum Result {
            ERRORS, DONE, DONE_WITH_ERRORS, UNKNOWN
        }

        protected Result result;
        protected Message[] messages;

        public boolean isDone() {
            return result == Result.DONE;
        }

        public boolean isUnknown() {
            return result == Result.UNKNOWN;
        }

        public boolean isErrored() {
            return result == Result.ERRORS;
        }

        public boolean isDoneWithErrors() {
            return result == Result.DONE_WITH_ERRORS;
        }

        public Message[] getMessages() {
            return messages;
        }

        public Result getResult() {
            return result;
        }

        @Override
        public String toString() {
            StringBuilder sB = new StringBuilder();
            for (Commander.CommandPromptOutput.Message m: messages) {
                sB.append(m);
            }
            return sB.toString();
        }
    }
    */

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

        /*CommandPromptOutput.Message[] messages = new CommandPromptOutput.Message[]{null};

        if ((lastErr != null && !lastErr.isEmpty()) && (lastOut != null && !lastOut.isEmpty())) {
            messages = new CommandPromptOutput.Message[]{new CommandPromptOutput.ErrorMessage(lastErr), new CommandPromptOutput.OutputMessage(lastOut)};
        }

        if (lastErr != null && !lastErr.isEmpty()) {
            messages = new CommandPromptOutput.Message[]{new CommandPromptOutput.ErrorMessage(lastErr)};
        }

        if (lastOut != null && !lastOut.isEmpty()) {
            messages = new CommandPromptOutput.Message[]{new CommandPromptOutput.OutputMessage(lastOut)};
        }
        return new CommandPromptOutput(messages);*/
    }

    private String readInputStream(InputStream stream, String charset, int maxWaitTimeData) throws IOException {
        if (stream == null) {
            throw new StreamCorruptedException("Stream is null");
        }
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        /*int i = stream.read(); while (i != 0xffffffff) { i = stream.read(); outputBuffer.write(i); }*/
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
