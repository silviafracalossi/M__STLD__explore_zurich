
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

public class EnrichedPoints extends Application {

    private int hexRed = 0xFFFF0000;
    private int hexBlue = 0xFF00FF00;
    private int hexGreen = 0xFF0000FF;

    private GraphicsOverlay graphicsOverlay;
    private MapView mapView;

    @Override
    public void start(Stage stage) {
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);

        stage.setTitle("DevLabs");
        stage.setWidth(600);
        stage.setHeight(350);
        stage.setScene(scene);
        stage.show();

        mapView = new MapView();
        setupMap();

        setupGraphicsOverlay();
        addPointGraphic();
        // addPolylineGraphic();
        // addPolygonGraphic();

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
            PointCollection polygonPoints = new PointCollection(SpatialReferences.getWgs84());
            polygonPoints.add(new Point(-118.27653, 34.15121));
            polygonPoints.add(new Point(-118.24460, 34.15462));
            polygonPoints.add(new Point(-118.22915, 34.14439));
            polygonPoints.add(new Point(-118.23327, 34.12279));
            polygonPoints.add(new Point(-118.25318, 34.10972));
            polygonPoints.add(new Point(-118.26486, 34.11625));
            polygonPoints.add(new Point(-118.27653, 34.15121));
            Polygon polygon = new Polygon(polygonPoints);
            SimpleFillSymbol polygonSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, hexGreen,
                    new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, hexBlue, 2.0f));
            Graphic polygonGraphic = new Graphic(polygon, polygonSymbol);
            graphicsOverlay.getGraphics().add(polygonGraphic);
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
