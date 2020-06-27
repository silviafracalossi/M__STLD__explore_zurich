public class Main {

    public static void main(String[] args) throws Exception {

        // DATA LOADER
//        final DataLoader dl = new DataLoader();
//        List<BindingSet> neighbourhood_list = dl.getNeighbourhoodsAndDistrictNames();
//
//        for (BindingSet bs: neighbourhood_list) {
//            String d_name = Literals.getLabel(bs.getValue("d_name"), "");
//            String n_name = Literals.getLabel(bs.getValue("n_name"), "");
//            System.out.println(n_name+" in "+d_name);
//        }

        // INTERFACE
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(MainWindow.class);
            }
        }.start();

        // STARTWINDOW INTERFACE
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                     // StartWindow window = new StartWindow(dl);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });



    }
}
