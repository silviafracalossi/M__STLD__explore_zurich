
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import org.eclipse.rdf4j.model.util.Literals;
import org.eclipse.rdf4j.query.BindingSet;

import java.awt.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        // DATA LOADER
        final DataLoader dl = new DataLoader();
        List<BindingSet> neighbourhood_list = dl.getNeighbourhoodsAndDistrictNames();

        for (BindingSet bs: neighbourhood_list) {
            String d_name = Literals.getLabel(bs.getValue("d_name"), "");
            String n_name = Literals.getLabel(bs.getValue("n_name"), "");
            System.out.println(n_name+" in "+d_name);
        }

        // INTERFACE
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                     StartWindow window = new StartWindow(dl);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });


    }
}
