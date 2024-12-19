package com.matteoformenton.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

import com.matteoformenton.MainApp;
import com.matteoformenton.model.Course;
import com.matteoformenton.model.Person;
import com.matteoformenton.model.Student;
import com.matteoformenton.util.DateUtil;

public class StudentEditAddDialogController {
      /*   @FXML
        private TableView<Person> personTable;
        @FXML
        private TableColumn<Person, String> firstNameColumn;
        @FXML
        private TableColumn<Person, String> lastNameColumn;*/

        @FXML
        private TableView<Student> studentTableCourse;
        @FXML
        private TableColumn<Student, Integer> idColumn;
        @FXML
        private TableColumn<Student, String> nameColumn;
        @FXML
        private TableColumn<Student, String> lastnameColumn;

        private Stage dialogStage;
        private boolean okClicked = false;

        private MainApp mainApp;

        private Course selectedCourse;
        private Course corso;

        /**
         * The constructor.
         * The constructor is called before the initialize() method.
         */
        public StudentEditAddDialogController() {
        }

        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
        }

        /**
         * Initializes the controller class. This method is automatically called
         * after the fxml file has been loaded.
         */
        @FXML
        private void initialize() {
            
            // Initialize the person table with the two columns.
            idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
            lastnameColumn.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());

                // Clear person details.
                showStudentDetails(null);

        // Listen for selection changes and show the person details when changed.
        studentTableCourse.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showStudentDetails(newValue));
    }
        

 
  
    private void showStudentDetails(Student student) {
        }
    
        /**
         *  Called when the user clicks on the delete button.
         */

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
   /*  private void handleOk() {
        // Ottieni lo studente selezionato nella tabella
        Student selectedStudent = studentTableCourse.getSelectionModel().getSelectedItem();
    
        if (selectedStudent != null) {
            // Chiama il metodo aggiungiStudente() con lo studente selezionato
            mainApp.aggiungiStudente(selectedStudent);
    
            // Imposta okClicked a true e chiudi il dialog
            okClicked = true;
            dialogStage.close();
        } else {
            // Mostra un avviso se nessuno studente è stato selezionato
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Nessuno studente selezionato");
            alert.setHeaderText("Nessuna selezione");
            alert.setContentText("Per favore, seleziona uno studente dalla tabella.");
            alert.showAndWait();
        }
    }*/ 
    private void handleOk() {
        Student selectedStudent = studentTableCourse.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            // Aggiungi lo studente al corso selezionato
            corso.aggiungiStudente(selectedStudent);
            selectedStudent.aggiungiCorso(corso);
            corso.addStudentId(selectedStudent.getId());
    
            // Debug: stampa gli studenti iscritti dopo l'aggiunta
            System.out.println("Studente aggiunto: " + selectedStudent.getNome() + " " + selectedStudent.getCognome());
            System.out.println("Lista studenti iscritti aggiornata: ");
            corso.getStudentiIscritti().forEach(student -> 
                System.out.println(student.getNome() + " " + student.getCognome())
            );
    
            okClicked = true;
            dialogStage.close();
        } else {
            // Mostra un avviso se nessuno studente è stato selezionato
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Nessuno studente selezionato");
            alert.setHeaderText("Nessuno studente selezionato");
            alert.setContentText("Per favore, seleziona uno studente dalla tabella.");
            alert.showAndWait();
        }
    }

    
    

    @FXML
    private void handleEsci() {
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
   

        /**
         * Is called by the main application to give a reference back to itself.
         * 
         * @param mainApp
         */
        public void setMainApp(MainApp mainApp) {
            this.mainApp = mainApp;
        
            // Debug: stampa i dati recuperati da mainApp
            System.out.println("StudentData da mainApp:");
            mainApp.getStudentData().forEach(student -> {
                System.out.println("ID: " + student.getId() +
                                   ", Nome: " + student.getNome() +
                                   ", Cognome: " + student.getCognome());
            });
        
            // Popola la tabella con i dati degli studenti
            studentTableCourse.setItems(mainApp.getStudentData());
        
            // Debug: stampa i dati della tabella
            printTableData();
        }
        


    @FXML
    private void printTableData() {
        System.out.println("Contenuto della tabella studenti:");
        if (studentTableCourse.getItems().isEmpty()) {
            System.out.println("La tabella è vuota.");
        } else {
            studentTableCourse.getItems().forEach(student -> {
                System.out.println("ID: " + student.getId() +
                                   ", Nome: " + student.getNome() +
                                   ", Cognome: " + student.getCognome());
            });
        }
    }

    public void setSelectedCourse(Course course) {
        this.selectedCourse = course;
    }

    public void setCorso(Course corso) {
        this.corso = corso;
    
        // Debug: stampa il corso selezionato
        System.out.println("Corso selezionato: " + corso.getNome());
    
        // Non mostrare gli studenti iscritti, ma solo quelli disponibili per l'aggiunta
        studentTableCourse.setItems(mainApp.getStudentData()); // Mostra tutti gli studenti
    }
    
}

