package pl.agh.edu.zookeeper;

import java.io.IOException;

public class ProcessExecutor {

    private final String command;
    private Process process;

    public ProcessExecutor(String command) {
        this.command = command;
    }

    public void startProcess() throws IOException {
        if (process == null) {
            Runtime runtime = Runtime.getRuntime();
            process = runtime.exec(command);
        }
    }

    public void stopProcess() {
        if (process != null) {
            process.destroy();
            process = null;
        }
    }
}
