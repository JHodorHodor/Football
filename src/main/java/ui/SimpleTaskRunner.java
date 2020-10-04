package ui;

public class SimpleTaskRunner implements TaskRunner {
    @Override
    public void runTask(Runnable r) {
        r.run();
    }
}
