package com.example.akel_semprace;

import com.example.akel_semprace.Axis.DateAxis;
import com.example.akel_semprace.DataLayer.CSVReader;
import com.example.akel_semprace.DataLayer.Generator;
import com.example.akel_semprace.DataLayer.RowInput;
import com.example.akel_semprace.Logic.LeftHeapNode;
import com.example.akel_semprace.Logic.AbstrHeap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainController {
    public TableView SensorTable;
    public TableColumn DatumColumon;
    public TableColumn SensorIdColumon;
    public TableColumn M3Columon;
    public ComboBox<Integer> SensorsCombo;
    public TextField firstDate;
    public TextField secondDate;
    public TextField itemSensorId;
    public TextField ItemM3Id;
    public TextField ItemDateId;

    private List<RowInput> Rows = new ArrayList<>();
    private List<RowInput>  CurrentTableData = new ArrayList<>();
    private AbstrHeap<RowInput> Heap = new AbstrHeap();
    private static  Integer inputId=30000;
    @FXML
    private Label welcomeText;
    private Stage stage;
    private Scene scene;
    private Parent parent;
    @FXML
    LineChart<String,Number> lineChart;
    @FXML
    private PieChart pieChart;
    private ObservableList<PieChart.Data> getChartData() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        list.addAll(new PieChart.Data("2018", 75),
                new PieChart.Data("2019", 25)
        );
        return list;
    }
    public void displayChart(ActionEvent event) throws IOException {
        if (this.Rows == null || this.Rows.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot Display Data Charts");
            alert.setHeaderText("Error!");
            alert.setContentText("no Data  Input to display charts! please read a file or manually add some data ");
            alert.showAndWait();
        } else {
            ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();
            ObservableList<XYChart.Data<Date, Number>> series1Data = FXCollections.observableArrayList();
            series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2018, 1, 6).getTime(), 0));
            series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2018, 2, 3).getTime(), 0.135));
            series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2018, 6, 6).getTime(), 0.080));
            series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2018, 9, 6).getTime(), 0.0035));
            series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2019, 11, 9).getTime(), 0.2565));
            series.add(new XYChart.Series<>("Date", series1Data));

            NumberAxis numberAxis = new NumberAxis();
            DateAxis dateAxis = new DateAxis();
            LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, numberAxis, series);
            lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: #9e9e9e");

            //Creating a Group object
            Image icon = new Image("C:\\Users\\ahmad.akel\\Desktop\\School\\BDATS\\Sensor-Measuring-Device\\src\\stats.png");
            Group root = new Group(lineChart);
            Stage stage = new Stage();
            stage.setTitle("Line Chart");
            //stage.setResizable(false);
            stage.setX(10);
            stage.setY(540);
            stage.getIcons().add(icon);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(MainController.class.getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

            //Getting PieChart
            PieChart pieChart = new PieChart();
            pieChart.setData(getChartData());
            pieChart.setLegendSide(Side.LEFT);
            pieChart.setTitle("Sensor Measuring Per Year");
            pieChart.setClockwise(false);
            //Creating layout

            //Creating a Group object

            Image icon2 = new Image("C:\\Users\\ahmad.akel\\Desktop\\School\\BDATS\\Sensor-Measuring-Device\\src\\graph.png");
            Group root2 = new Group(pieChart);
            Stage stage2 = new Stage();
            stage2.setTitle("Pie Chart");
            stage2.setResizable(false);
            stage2.setX(10);
            stage2.setY(105);
            stage2.getIcons().add(icon2);
            Scene scene2 = new Scene(root2);
            stage2.setScene(scene2);
            stage2.show();
        }
    }

    public void readFile() throws ParseException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Select CSV File ", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) return;
        CSVReader reader = new CSVReader();
        var rows = reader.readFile(selectedFile.getAbsolutePath());
        ObservableList<RowInput> data = FXCollections.observableArrayList(rows);
        this.Rows = rows;
        //filling the comboBox with Sensors Ids;
        SensorsCombo.getItems().add(-1); // select -1 to remove sensorId filter;
        for (var row : data) {
            if (!SensorsCombo.getItems().contains(row.getSensorId())) {
                SensorsCombo.getItems().add(row.getSensorId());
            }
        }
        // Setting Table Data
        CurrentTableData = data;
        setTableData(data);
        if (this.Rows == null || this.Rows.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot Display Data Charts");
            alert.setHeaderText("Error!");
            alert.setContentText("no Data  Input to display charts! please read a file or manually add some data ");
            alert.showAndWait();
        } else {
            ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();
            ObservableList<XYChart.Data<Date, Number>> series1Data = FXCollections.observableArrayList();
            series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2018, 1, 6).getTime(), 0));
            series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2018, 2, 3).getTime(), 0.135));
            series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2018, 6, 6).getTime(), 0.080));
            series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2018, 9, 6).getTime(), 0.0035));
            series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2019, 11, 9).getTime(), 0.2565));
            series.add(new XYChart.Series<>("Date", series1Data));

            NumberAxis numberAxis = new NumberAxis();
            DateAxis dateAxis = new DateAxis();
            LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, numberAxis, series);
            lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: #9e9e9e");

            //Creating a Group object
            Image icon = new Image("C:\\Users\\ahmad.akel\\Desktop\\School\\BDATS\\Sensor-Measuring-Device\\src\\stats.png");
            Group root = new Group(lineChart);
            Stage stage = new Stage();
            stage.setTitle("Line Chart");
            //stage.setResizable(false);
            stage.setX(10);
            stage.setY(540);
            stage.getIcons().add(icon);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(MainController.class.getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

            //Getting PieChart
            PieChart pieChart = new PieChart();
            pieChart.setData(getChartData());
            pieChart.setLegendSide(Side.LEFT);
            pieChart.setTitle("Sensor Measuring Per Year");
            pieChart.setClockwise(false);
            //Creating layout

            //Creating a Group object

            Image icon2 = new Image("C:\\Users\\ahmad.akel\\Desktop\\School\\BDATS\\Sensor-Measuring-Device\\src\\graph.png");
            Group root2 = new Group(pieChart);
            Stage stage2 = new Stage();
            stage2.setTitle("Pie Chart");
            stage2.setResizable(false);
            stage2.setX(10);
            stage2.setY(105);
            stage2.getIcons().add(icon2);
            Scene scene2 = new Scene(root2);
            stage2.setScene(scene2);
            stage2.show();
        }
    }

    private void setTableData(ObservableList<RowInput> data) {
        M3Columon.setCellValueFactory(new PropertyValueFactory<RowInput, Double>("m3"));
        DatumColumon.setCellValueFactory(new PropertyValueFactory<RowInput, Date>("date"));
        SensorIdColumon.setCellValueFactory(new PropertyValueFactory<RowInput, Integer>("sensorId"));
        // clear old data
        SensorTable.getItems().clear();
        SensorTable.setItems(data);
    }

     private void CreateHeap()
     {
         if (CurrentTableData == null || CurrentTableData.size() == 0) {
             return;
         }
         Heap = new AbstrHeap();
         for (var row : this.CurrentTableData) {
             Heap.insert(row);
         }

         ObservableList<RowInput> data = FXCollections.observableArrayList(Heap.toArray(new RowInput[Heap.getSize()],true));
         for (var row : this.CurrentTableData) {
             Heap.insert(row);
         }
         setTableData(data);
         CurrentTableData = data;

     }
    public void createPF() {
        CreateHeap();
    }

    public void insertItem(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            Stage stage = new Stage();
            fxmlLoader.getController();
            stage.setTitle("New Window");
            stage.setScene(scene);
            stage.setUserData(this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getItem() {
        System.out.println("Get Item");
    }

    public void iterate() {
        if (Heap.getSize()<=0){
            return;
        }
        ObservableList<RowInput> data = FXCollections.observableArrayList(Heap.toArray(new RowInput[Heap.getSize()],true));
        setTableData(data);
        CurrentTableData = data;
    }

    public void reset() {
        ObservableList<RowInput> data = FXCollections.observableArrayList(this.Rows);
        setTableData(data);
        CurrentTableData = data;
    }

    public void submit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generate() {

        var generatedData =   Generator.Generate(10);
        var tableData = FXCollections.observableArrayList(generatedData);
        setTableData(tableData);
        CurrentTableData = tableData;
        if(this.Rows==null|Rows.size()==0)
        {
            Rows.addAll(generatedData);
        }

    }

    public void filterData(ActionEvent event) {
        // get dates
        // Get selected item form comboBox;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date from;
        Date to;
        try {
            from = dateFormat.parse(firstDate.getText());
            to = dateFormat.parse(secondDate.getText());

        } catch (ParseException e) {
            from = null;
            to = null;
        }

        Integer sensorId = SensorsCombo.getSelectionModel().getSelectedItem();
        if (sensorId != null && sensorId == -1) {
            sensorId = null;
        }
        var data = filter(from, to, sensorId);
        if (data != null) {
            setTableData(data);
            CurrentTableData = data;
        }
      CreateHeap();
    }

    private ObservableList<RowInput> filter(Date from, Date to, Integer sensId) {
        List<RowInput> filteredRows = new ArrayList<>();
        if (this.Rows == null || this.Rows.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot Filter Data");
            alert.setHeaderText("Error!");
            alert.setContentText("no Data  Input to perform filter! please read a file or manually add some data ");
            alert.showAndWait();
            return null;
        }
        if ((from == null || to == null) && sensId == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot Filter Data");
            alert.setHeaderText("Error!");
            alert.setContentText("Please Specify  filters ! Date or Sensor ID");
            alert.showAndWait();
            return null;
        }
        if (sensId != null && (from != null && to != null)) {
            for (var row : this.Rows) {
                if (((row.getDate().after(from) || row.getDate().equals(from)) && (row.getDate().before(to) || row.getDate().equals(to))) && row.getSensorId() == sensId) {
                    filteredRows.add(row);
                }
            }
        } else if (sensId == null && (from != null && to != null)) {
            for (var row : this.Rows) {
                if ((row.getDate().after(from) || row.getDate().equals(from)) && (row.getDate().before(to) || row.getDate().equals(to))) {
                    filteredRows.add(row);
                }
            }
        } else if (sensId != null && (from == null || to == null)) {
            for (var row : this.Rows) {
                if (row.getSensorId() == sensId) {
                    filteredRows.add(row);
                }
            }
        }

        return FXCollections.observableArrayList(filteredRows);
    }

    //inserting a new element

    @FXML
    private void submitItem(ActionEvent actionEvent) throws ParseException {;

        RowInput input = new RowInput();
      // insert to table data ;
        // rebuild PF
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            input.setID(inputId);
            input.setDate(df.parse(ItemDateId.getText()));
            Double m3 =  Double.parseDouble(ItemM3Id.getText());
            input.setM(m3);
            Integer sensId =  Integer.parseInt(itemSensorId.getText());
            input.setSensorID(sensId);
            this.Rows.add(input);
            if(SensorTable!=null &&  SensorTable.getItems()!=null)
            {
                SensorTable.getItems().add(input);
            }else
            {

                var data = FXCollections.observableArrayList(Rows);
                setTableData(data);
            }
            CurrentTableData = SensorTable.getItems();
            CreateHeap();
        }
        catch (ParseException ex){
            return;
        }


    }

    public void getMax(ActionEvent actionEvent) {
        if(Heap == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot Get Max");
            alert.setHeaderText("Error!");
            alert.setContentText("Create a PF First !");
            alert.showAndWait();
            return;
        }
        //Max Priority
        var max = Heap.deleteMin();
        System.out.println(max);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Max Removed");
        alert.setHeaderText("");
        alert.setContentText(max.toString());
        alert.showAndWait();
        SensorTable.getItems().remove(max);
        CurrentTableData = SensorTable.getItems();

    }
}