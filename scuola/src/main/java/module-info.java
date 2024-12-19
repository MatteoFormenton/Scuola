module com.matteoformenton {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    

    opens com.matteoformenton to javafx.fxml;
    opens com.matteoformenton.controller to javafx.fxml;
    opens com.matteoformenton.model to com.fasterxml.jackson.databind;

    exports com.matteoformenton.model;
    exports com.matteoformenton.controller;
   // exports com.matteoformenton.view;
    exports com.matteoformenton;
}
