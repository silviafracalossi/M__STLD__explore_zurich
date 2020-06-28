
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
import org.mapdb.Bind;

import java.util.List;

import static java.lang.Integer.parseInt;

public class MainWindow extends Application {

    private int hexRed = 0xFFFF0000;
    private int hexGreen = 0xFF00FF00;
    private int hexBlue = 0xFF0000FF;

    // Variable to retrieve data
    private DataLoader dl;

    // Variables to manage filters
    private String filters = "";
    private BindingSet selected_district;
    private int district_id;

    // Graphical general component
    private Stage general_stage;
    private StackPane stackPane;
    private Scene scene;

    // Components of visualization
    private GraphicsOverlay graphicsOverlay;
    private VBox controlsVBox;
    private MapView mapView;

    // Others
    private SpatialReference spatialReference;
    private static final int SCALE = 5000;

    @Override
    public void start(Stage stage) throws Exception {

        // Loading the DataLoader class
        dl = new DataLoader();

        // Adding no filter to initial view
        filters = "";

        // Starting graphical components
        stackPane = new StackPane();
        scene = new Scene(stackPane);
        general_stage = stage;

        // Starting the window based on settings
        startWindow();
    }

    private void startWindow() {

        System.out.println("[INFO] Starting the window...");

        // Setting the stage of the visualization
        general_stage.setTitle("Explore Zurich");
        general_stage.setWidth(1000);
        general_stage.setHeight(600);
        general_stage.setScene(scene);
        general_stage.show();

        // Setting the map of the visualization
        mapView = new MapView();
        double zurich_latitude = 47.3769;
        double zurich_longitude = 8.5417;
        setupMap(zurich_latitude, zurich_longitude);

        // Creating overlay
        setupGraphicsOverlay();

        // Creating filter panel
        setupFilterPanel(filters);

        // Retrieving the markers and visualizing them on the map
        if(this.filters.compareTo("") != 0) {
            List<BindingSet> marker_data = dl.getMarkerData(district_id, filters);
            if (marker_data != null) {
                addMarkers(marker_data);
            }
        }

        // Preparing the home - Normal Setting
        if (filters.compareTo("") == 0) {
            System.out.println("[INFO] Showing normal visualization");

//            List<BindingSet> districts_results = dl.getDistrictAreas();
//            if (districts_results != null) {
//                addDistrictGraphic(districts_results);
//            }
        } else {

            // Preparing the home - Display District
            if (filters.contains("d")) {
                System.out.println("[INFO] Visualizing district");
                addSingleDistrictGraphic(selected_district);
            }
        }

        // Adding map and control panel to the stack pane
        stackPane.getChildren().addAll(mapView, controlsVBox);
        StackPane.setAlignment(controlsVBox, Pos.TOP_LEFT);
        StackPane.setMargin(controlsVBox, new Insets(10, 0, 0, 10));
    }

    // Setting the center of the map and zoom
    private void setupMap(double latitude, double longitude) {
        if (mapView != null) {
            Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR;
            int levelOfDetail = 12;
            ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            mapView.setMap(map);
        }
    }

    // Adding the graphics overlay
    private void setupGraphicsOverlay() {
        if (mapView != null) {
            graphicsOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(graphicsOverlay);
        }
    }

    // Creating the filter panel visualized on the left side
    private void setupFilterPanel(String filters) {

        // =====================Creating the filter panel===============================
        controlsVBox = new VBox(6);
        controlsVBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("rgba(0,0,0,0.5)"),
                CornerRadii.EMPTY, Insets.EMPTY)));
        controlsVBox.setPadding(new Insets(10.0));
        controlsVBox.setMaxSize(260, 120);
        controlsVBox.getStyleClass().add("panel-region");

        // ====================Creating the title of the panel===========================
        Label title = new Label("Filter Section");
        title.setFont(new Font("Arial", 20));
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-text-fill: white;");

        // Adding the header box
        HBox title_box = new HBox(10);
        title_box.setAlignment(Pos.CENTER);
        title_box.setPadding(new Insets(10.0));
        title_box.getChildren().addAll(title);

        // =======================Creating the District label=============================
        Label district_label = new Label("District");
        district_label.setFont(new Font("Arial", 15));
        district_label.setAlignment(Pos.CENTER_LEFT);
        district_label.setStyle("-fx-text-fill: white;");

        // Add the district choice box
        ChoiceBox district_choice_box = new ChoiceBox();
        district_choice_box.getItems().add("");
        for (int i=1; i<13; i++) {
            district_choice_box.getItems().add("District " + i);
        }

        // Adding the District box
        HBox district_box = new HBox(10);
        district_box.setAlignment(Pos.CENTER);
        district_box.getChildren().addAll(district_label, district_choice_box);
        district_box.setPadding(new Insets(10.0));

        // =======================Creating the Include part===============================
        Label include_label = new Label("Include facilities:");
        include_label.setFont(new Font("Arial", 15));
        include_label.setAlignment(Pos.TOP_LEFT);
        include_label.setStyle("-fx-text-fill: white;");

        // Add Public Transportation check
        CheckBox pubtrans_check = new CheckBox("Public Transportation");
        pubtrans_check.setFont(new Font("Arial", 15));
        pubtrans_check.setAlignment(Pos.CENTER_LEFT);
        pubtrans_check.setStyle("-fx-text-fill: white;");

        // Add Parking check
        CheckBox parking_check = new CheckBox("Parking");
        parking_check.setFont(new Font("Arial", 15));
        parking_check.setAlignment(Pos.CENTER_LEFT);
        parking_check.setStyle("-fx-text-fill: white;");
        parking_check.setPadding(new Insets(0,0,10,0));


        // =======================Creating the POIs part===============================
        Label pois_label = new Label("Include Points of Interest:");
        pois_label.setFont(new Font("Arial", 15));
        pois_label.setAlignment(Pos.TOP_LEFT);
        pois_label.setStyle("-fx-text-fill: white;");

        // Add Restaurant check
        CheckBox restaurant_check = new CheckBox("Restaurants");
        restaurant_check.setFont(new Font("Arial", 15));
        restaurant_check.setAlignment(Pos.CENTER_LEFT);
        restaurant_check.setStyle("-fx-text-fill: white;");

        // Add Bar check
        CheckBox bar_check = new CheckBox("Bar");
        bar_check.setFont(new Font("Arial", 15));
        bar_check.setAlignment(Pos.CENTER_LEFT);
        bar_check.setStyle("-fx-text-fill: white;");

        // Add Museum check
        CheckBox museum_check = new CheckBox("Museum");
        museum_check.setFont(new Font("Arial", 15));
        museum_check.setAlignment(Pos.CENTER_LEFT);
        museum_check.setStyle("-fx-text-fill: white;");

        // Add Bar check
        CheckBox attraction_check = new CheckBox("Attraction");
        attraction_check.setFont(new Font("Arial", 15));
        attraction_check.setAlignment(Pos.CENTER_LEFT);
        attraction_check.setStyle("-fx-text-fill: white;");

        // Add Shop check
        CheckBox shop_check = new CheckBox("Shop");
        shop_check.setFont(new Font("Arial", 15));
        shop_check.setAlignment(Pos.CENTER_LEFT);
        shop_check.setStyle("-fx-text-fill: white;");
        shop_check.setPadding(new Insets(0,0,10,0));


        // ======================Handling previous filter=================================

        // Sets checks as selected if they were in the previous interface
        pubtrans_check.setSelected(filters.contains("t"));
        parking_check.setSelected(filters.contains("p"));
        restaurant_check.setSelected(filters.contains("r"));
        bar_check.setSelected(filters.contains("b"));
        museum_check.setSelected(filters.contains("m"));
        attraction_check.setSelected(filters.contains("a"));
        shop_check.setSelected(filters.contains("s"));

        // Sets default value
        if (filters.contains("d")) {
            district_choice_box.setValue("District "+district_id);
        } else {
            district_choice_box.setValue("");
        }


        // ======================Creating the filter button=================================
        Button filter_button = new Button("Apply Filter");
        filter_button.setStyle("-fx-margin: 10;");
        filter_button.setMaxWidth(Double.MAX_VALUE);

        // Setting the commands for the filtering option
        filter_button.setOnAction(e -> {

            System.out.println("---------------Processing the filtering request------------------");

            // Setting filters to empty
            this.filters = "";

            // Retrieve district selected data
            String district_chosen = (String) district_choice_box.getValue();
            if (district_chosen != null && district_chosen != "") {
                int new_district_id = parseInt(district_chosen.substring(9));

                if (new_district_id != district_id) {
                    district_id = new_district_id;
                    selected_district = dl.getDistrictById(district_id);
                }

                // Setting the filter variables
                this.filters += "d";
            } else {
                district_id = 0;
                selected_district = null;
            }

            // Retrieving the selected checkboxes
            this.filters += (pubtrans_check.isSelected()) ? "t" : "";
            this.filters += (parking_check.isSelected()) ? "p" : "";
            this.filters += (restaurant_check.isSelected()) ? "r" : "";
            this.filters += (bar_check.isSelected()) ? "b" : "";
            this.filters += (museum_check.isSelected()) ? "m" : "";
            this.filters += (attraction_check.isSelected()) ? "a" : "";
            this.filters += (shop_check.isSelected()) ? "s" : "";

            // Restart the window
            startWindow();
        });

        // Adding all the elements to the panel
        controlsVBox.getChildren().addAll(title_box, district_box,                             // district opinion
            include_label, pubtrans_check, parking_check,                                           // facilities option
            pois_label, restaurant_check, bar_check, museum_check, attraction_check, shop_check,    // pois option
            filter_button);                                                                         // filter button
    }


    // Converting polygon retrieved in string format to actual polygon
    private Polygon polygon_from_string (String text) {

        // Declaring polygon points collection
        PointCollection polygonPoints = new PointCollection(SpatialReferences.getWgs84());

        // Fixing the text structure
        text = text.replace("POLYGON ((", "");
        text = text.replace("))", "");

        // Iterating through polygon coordinates
        String[] coordinate_pairs = text.split(", ");
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

    // Converting polygon retrieved in string format to actual polygon
    private Point point_from_string (String text) {

        // Fixing the text structure
        text = text.replace("POINT (", "");
        text = text.replace(")", "");
        String[] coordinate_pairs = text.split(" ");

        // Creating and returning point
        return new Point(Double.parseDouble(coordinate_pairs[1]),
                Double.parseDouble(coordinate_pairs[0]),
                SpatialReferences.getWgs84());
    }


    // Showing markers on the map
    private void addMarkers(List<BindingSet> marker_data) {
        for (BindingSet bs: marker_data) {
            addPointGraphic(bs);
        }
    }

    // Adding the specified marker to graphics
    private void addPointGraphic(BindingSet marker) {
        if (graphicsOverlay != null) {
            SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, hexRed, 10.0f);
            pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, hexBlue, 2.0f));
            Point point = point_from_string(Literals.getLabel(marker.getValue("locat"), ""));
            graphicsOverlay.getGraphics().add(new Graphic (point, pointSymbol));
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


    // Retrieving District polygons and showing them on the map
    private void addDistrictGraphic(List<BindingSet> districts_data) {
        for (BindingSet bs: districts_data) {
            addSingleDistrictGraphic(bs);
        }
    }

    // Adding the specified district to graphics
    private void addSingleDistrictGraphic(BindingSet district) {
        if (graphicsOverlay != null) {
            Polygon polygon = polygon_from_string(Literals.getLabel(district.getValue("d_area"), ""));
            SimpleLineSymbol polygonSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, hexBlue, 2.0f);
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
