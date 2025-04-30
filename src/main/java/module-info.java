module com.example.finalexam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens fxml to javafx.fxml;
    opens GUI to javafx.fxml;
    opens  Main to javafx.fxml;
    exports GUI;
    exports Main;
}