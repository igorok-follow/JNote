package ml.javalearn.Thread;

import ml.javalearn.windows.MainWindow;

public class Threads extends Thread {

    MainWindow mainWindow;

    public Threads(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void run() {
        while (true) {
            mainWindow.checkCurrentAndSettedTimeNotification();
            try {
                Threads.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
