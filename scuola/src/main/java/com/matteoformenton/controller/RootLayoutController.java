package com.matteoformenton.controller;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import com.matteoformenton.MainApp;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author Marco Jakob
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;
    private TabPane tabPane;
    


    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNewStudent() {
        mainApp.getStudentData().clear();
        mainApp.setStudentFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select a student list to load.
     */
    @FXML
    private void handleOpenStudent() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File studentFile = mainApp.getStudentFilePath();
        if (studentFile != null) {
            fileChooser.initialFileNameProperty().set(studentFile.getName());
        }

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadStudentDataFromFile(file);
        }
    }

    /**
     * Saves the file to the student file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSaveStudent() {
        File studentFile = mainApp.getStudentFilePath();
        if (studentFile != null) {
            mainApp.saveStudentDataToFile(studentFile);
        } else {
            handleSaveAsStudent();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAsStudent() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File studentFile = mainApp.getStudentFilePath();
        if (studentFile != null) {
            fileChooser.initialFileNameProperty().set(studentFile.getName());
        }

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".json")) {
                file = new File(file.getPath() + ".json");
            }
            mainApp.saveStudentDataToFile(file);
        }
    }

    @FXML
    private void handleNewTeacher() {
        mainApp.getTeacherData().clear();
        mainApp.setTeacherFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select a student list to load.
     */
    @FXML
    private void handleOpenTeacher() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File teacherFile = mainApp.getTeacherFilePath();
        if (teacherFile != null) {
            fileChooser.initialFileNameProperty().set(teacherFile.getName());
        }

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadTeacherDataFromFile(file);
        }
    }

    /**
     * Saves the file to the student file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSaveTeacher() {
        File teacherFile = mainApp.getTeacherFilePath();
        if (teacherFile != null) {
            mainApp.saveTeacherDataToFile(teacherFile);
        } else {
            handleSaveAsTeacher();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAsTeacher() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File teacherFile = mainApp.getTeacherFilePath();
        if (teacherFile != null) {
            fileChooser.initialFileNameProperty().set(teacherFile.getName());
        }

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".json")) {
                file = new File(file.getPath() + ".json");
            }
            mainApp.saveTeacherDataToFile(file);
        }
    }

    @FXML
    private void handleNewCourse() {
        mainApp.getCourseData().clear();
        mainApp.setCourseFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select a student list to load.
     */
    @FXML
    private void handleOpenCourse() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File courseFile = mainApp.getCourseFilePath();
        if (courseFile != null) {
            fileChooser.initialFileNameProperty().set(courseFile.getName());
        }

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadCourseDataFromFile(file);
        }
    }

    /**
     * Saves the file to the student file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSaveCourse() {
        File courseFile = mainApp.getCourseFilePath();
        if (courseFile != null) {
            mainApp.saveCourseDataToFile(courseFile);
        } else {
            handleSaveAsCourse();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAsCourse() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File courseFile = mainApp.getCourseFilePath();
        if (courseFile != null) {
            fileChooser.initialFileNameProperty().set(courseFile.getName());
        }

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".json")) {
                file = new File(file.getPath() + ".json");
            }
            mainApp.saveCourseDataToFile(file);
        }
    }
    /**
     * Opens an about dialog.
     */
  /*   @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("AddressApp");
    	alert.setHeaderText("About");
    	alert.setContentText("Author: Marco Jakob\nWebsite: http://code.makery.ch");

    	alert.showAndWait();
    }*/

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    public TabPane getTabPane() {
        return tabPane;
    }

        @FXML
    private void handleShowStudentOverview() {
    if (mainApp != null) {
        mainApp.showStudentOverview();
    } else {
        System.err.println("Errore: riferimento a mainApp non impostato.");
    }
}

    
}
