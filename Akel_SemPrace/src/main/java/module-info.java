module com.example.akel_semprace {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.akel_semprace to javafx.fxml;
    opens com.example.akel_semprace.DataLayer;
    exports com.example.akel_semprace;

}