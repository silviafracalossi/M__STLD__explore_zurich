
import com.esri.arcgisruntime.mapping.Viewpoint;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.eclipse.rdf4j.model.util.Literals;
import org.eclipse.rdf4j.query.BindingSet;

import javax.xml.crypto.Data;
import java.util.List;

public class MainWindow extends Application {

    private int hexRed = 0xFFFF0000;
    private int hexBlue = 0xFF00FF00;
    private int hexGreen = 0xFF0000FF;

    private static final int SCALE = 5000;

    private SpatialReference spatialReference;
    private GraphicsOverlay graphicsOverlay;
    private VBox controlsVBox;
    private MapView mapView;
    private DataLoader dl;
    //private ChoiceBox choiceBox;

    @Override
    public void start(Stage stage) throws Exception {

        dl = new DataLoader();

//        Button button = new Button();
//        button.

        // Usual content
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);

        stage.setTitle("Explore Zurich");
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();

        mapView = new MapView();
        setupMap();

        setupGraphicsOverlay();
        setupFilterPanel();
        // addDistrictGraphic();
        // addPointGraphic();
        // addPolylineGraphic();
        // addPolygonGraphic();

        // add map view and control panel to stack pane
        stackPane.getChildren().addAll(mapView, controlsVBox);
        StackPane.setAlignment(controlsVBox, Pos.TOP_LEFT);
        StackPane.setMargin(controlsVBox, new Insets(10, 0, 0, 10));

    }


    private void setupMap() {
        if (mapView != null) {
            Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR;
            double latitude = 47.3769;
            double longitude = 8.5417;
            int levelOfDetail = 12;
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

    private void setupFilterPanel() {

        // Creating the filter panel
        controlsVBox = new VBox(6);
        controlsVBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("rgba(0,0,0,0.5)"),
                CornerRadii.EMPTY, Insets.EMPTY)));
        controlsVBox.setPadding(new Insets(10.0));
        controlsVBox.setMaxSize(260, 120);
        controlsVBox.getStyleClass().add("panel-region");

        // Creating the title of the panel
        Label title = new Label("Filter Section");
        title.setFont(new Font("Arial", 20));
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-text-fill: white;");

        // Adding the header box
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10.0));
        hbox.getChildren().addAll(title);

        // Creating the District label
        Label district_label = new Label("District");
        district_label.setFont(new Font("Arial", 15));
        district_label.setAlignment(Pos.CENTER_LEFT);
        district_label.setStyle("-fx-text-fill: white;");

        // Add the district choice box
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().add("District 1");
        choiceBox.getItems().add("District 2");
        choiceBox.getItems().add("District 3");

        // Adding the District box
        HBox dbox = new HBox(10);
        dbox.setAlignment(Pos.CENTER);
        dbox.getChildren().addAll(district_label, choiceBox);


        // Creating the filter button
        Button filter_button = new Button("Apply Filter");
        filter_button.setMaxWidth(Double.MAX_VALUE);
        filter_button.setOnAction(e -> {

            // Call to DataLoader class
            dl.printAnything();

            // Retrieve district selected
            String value = (String) choiceBox.getValue();
            System.out.println("District x chosen: " +value);

        });




        // Old buttons
//        Button animateButton = new Button("LONDON (Animate)");
//        Button centerButton = new Button("WATERLOO (Center and Scaled)");
//        Button geometryButton = new Button("WESTMINSTER (Geometry)");
//        animateButton.setMaxWidth(Double.MAX_VALUE);
//        centerButton.setMaxWidth(Double.MAX_VALUE);
//        geometryButton.setMaxWidth(Double.MAX_VALUE);
//
//        // Old listeners
//        animateButton.setOnAction(e -> {
//            // create the London location point
//            Point londonPoint = new Point(-14093, 6711377, spatialReference);
//            // create the viewpoint with the London point and scale
//            Viewpoint viewpoint = new Viewpoint(londonPoint, SCALE);
//
//            // set the map views's viewpoint to London with a seven second duration
//            mapView.setViewpointAsync(viewpoint, 7);
//        });
//
//        centerButton.setOnAction(e -> {
//            // create the Waterloo location point
//            Point waterlooPoint = new Point(-12153, 6710527, spatialReference);
//            // set the map views's viewpoint centered on Waterloo and scaled
//            mapView.setViewpointCenterAsync(waterlooPoint, SCALE);
//        });
//
//        geometryButton.setOnAction(e -> {
//            // create a collection of points around Westminster
//            PointCollection westminsterPoints = new PointCollection(spatialReference);
//            westminsterPoints.add(new Point(-13823, 6710390));
//            westminsterPoints.add(new Point(-13823, 6710150));
//            westminsterPoints.add(new Point(-14680, 6710390));
//            westminsterPoints.add(new Point(-14680, 6710150));
//
//            Polyline geometry = new Polyline(westminsterPoints);
//
//            // set the map views's viewpoint to Westminster
//            mapView.setViewpointGeometryAsync(geometry);
//        });

        // add controls to the user interface panel
        // controlsVBox.getChildren().addAll(animateButton, centerButton, geometryButton);

        controlsVBox.getChildren().addAll(hbox, dbox, filter_button);
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
