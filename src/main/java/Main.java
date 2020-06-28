public class Main {

    public static void main(String[] args) throws Exception {




        // DATA LOADER
//
//        DataLoader dl = new DataLoader();
//        dl.getMarkerData(10, "bra");
//
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



        // Print result
//        if (result_list != null) {
//            for (BindingSet bs : result_list) {
//                String poi = bs.getBinding("poi").getValue().toString();
//                String nam = Literals.getLabel(bs.getValue("nam"), "");
//                String descr = Literals.getLabel(bs.getValue("descr"), "");
//                System.out.println(poi+": " +nam+ " and " +descr);
//            }
//        } else {
//            System.out.println("no data :( ");
//        }


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
