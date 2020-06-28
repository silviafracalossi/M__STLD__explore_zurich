public class Main {

    public static void main(String[] args) throws Exception {
        // INTERFACE
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(MainWindow.class);
            }
        }.start();

    }
}
