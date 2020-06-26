
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

import org.eclipse.rdf4j.model.util.Literals;
import org.eclipse.rdf4j.query.BindingSet;
import java.util.List;

public class EnrichedPoints extends Application {

    private int hexRed = 0xFFFF0000;
    private int hexBlue = 0xFF00FF00;
    private int hexGreen = 0xFF0000FF;

    private GraphicsOverlay graphicsOverlay;
    private MapView mapView;

    private DataLoader dl;

    /**
     * Create the application.
     */
//    public EnrichedPoints(DataLoader dl) {
//        this.dl = dl;
//        //Application.launch();
//
//    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);

        stage.setTitle("DevLabs");
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();

        mapView = new MapView();
        setupMap();

        setupGraphicsOverlay();
        // addPointGraphic();
        // addPolylineGraphic();
        // addPolygonGraphic();

        addDistrictGraphic();

        stackPane.getChildren().addAll(mapView);
    }

    private void setupMap() {
        if (mapView != null) {
            Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR;
            double latitude = 47.411526;
            double longitude = 8.54414;
            int levelOfDetail = 11;
            ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            mapView.setMap(map);
        }
    }

    private void setupGraphicsOverlay() {
        if (mapView != null) {
            graphicsOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(graphicsOverlay);
        }
    }


    // Converting polygon retrieved in string format to actual polygon
    private Polygon polygon_from_string (String text) {

        // Declaring polygon points collection
        PointCollection polygonPoints = new PointCollection(SpatialReferences.getWgs84());

        // Fixing the text structure
        text = text.replace("POLYGON ((", "");
        text = text.replace("))", "");

        // Iterating through polygon coordinates
        String[] coordinate_pairs = text.split(", ");         // "47.35725186638428 8.621464853225644"
        for (int i=0; i<coordinate_pairs.length; i++) {
            String[] coordinates = coordinate_pairs[i].split(" ");
            polygonPoints.add(new com.esri.arcgisruntime.geometry.Point(
                    Double.parseDouble(coordinates[1]),
                    Double.parseDouble(coordinates[0]))
            );
        }

        // Returning polygon with all points
        return new Polygon(polygonPoints);
    }

    private void addPointGraphic() {
        if (graphicsOverlay != null) {
            SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, hexRed, 10.0f);
            pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, hexBlue, 2.0f));
            Point p_json_1 = new Point(8.54414, 47.411526,SpatialReferences.getWgs84());
            graphicsOverlay.getGraphics().add(new Graphic (p_json_1, pointSymbol));
        }
    }

    private void addPolylineGraphic() {
        if (graphicsOverlay != null) {
            PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());
            polylinePoints.add(new Point(-118.29026, 34.1816));
            polylinePoints.add(new Point(-118.26451, 34.09664));
            Polyline polyline = new Polyline(polylinePoints);
            SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, hexBlue, 3.0f);
            Graphic polylineGraphic = new Graphic(polyline, polylineSymbol);
            graphicsOverlay.getGraphics().add(polylineGraphic);
        }
    }

    private void addPolygonGraphic() {
        if (graphicsOverlay != null) {
            Polygon polygon = polygon_from_string("");      // TODO insert actual polygon text and remove fake from method
            SimpleFillSymbol polygonSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, hexGreen,
                    new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, hexBlue, 2.0f));
            Graphic polygonGraphic = new Graphic(polygon, polygonSymbol);
            graphicsOverlay.getGraphics().add(polygonGraphic);
        }
    }

    // Retrieving District polygons and showing them on the map
    private void addDistrictGraphic() throws Exception {

        // Retrieving districts polygons
        DataLoader dl = new DataLoader();
        List<BindingSet> result = dl.getDistrictAreas();

        if (graphicsOverlay != null) {

            for (BindingSet bs: result) {
                Polygon polygon = polygon_from_string(Literals.getLabel(bs.getValue("d_area"), ""));
                SimpleFillSymbol polygonSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, hexGreen,
                    new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, hexBlue, 2.0f));
                Graphic polygonGraphic = new Graphic(polygon, polygonSymbol);
                graphicsOverlay.getGraphics().add(polygonGraphic);

            }


        }

    }

    @Override
    public void stop() {
        if (mapView != null) {
            mapView.dispose();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
