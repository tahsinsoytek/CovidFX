module com.example.covidfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    opens com.example.covidfx to javafx.fxml;
    exports com.example.covidfx;
}