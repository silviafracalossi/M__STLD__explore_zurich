public class Main {

    public static void main(String[] args) throws Exception {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(MainWindow.class);
            }
        }.start();

    }
}
