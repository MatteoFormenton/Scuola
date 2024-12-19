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

import java.util.List;

import com.matteoformenton.MainApp;
import com.matteoformenton.model.Course;
import com.matteoformenton.model.Student;
import com.matteoformenton.model.Teacher;
import com.matteoformenton.util.DateUtil;

public class TeacherOverviewController {

      /*   @FXML
        private TableView<Person> personTable;
        @FXML
        private TableColumn<Person, String> firstNameColumn;
        @FXML
        private TableColumn<Person, String> lastNameColumn;*/

        @FXML
        private TableView<Teacher> teacherTable;
        @FXML
        private TableColumn<Teacher, String> firstColumnTeacher;
        @FXML
        private TableColumn<Teacher, String> lastColumnteacherTable;


        @FXML
        private Label idLabelTeacher;
        @FXML
        private Label nomeLabelTeacher;
        @FXML
        private Label cognomeLabelTeacher;
        @FXML
        private Label materiaLabelTeacher;
        @FXML
        private Label corsiLabelTeacher;

        // Reference to the main application.
        private MainApp mainApp;

        private int lastTeacherId = 0;

        @FXML
        private TextField searchField;
        /**
         * The constructor.
         * The constructor is called before the initialize() method.
         */
        public TeacherOverviewController() {
        }

        /**
         * Initializes the controller class. This method is automatically called
         * after the fxml file has been loaded.
         */
        @FXML
        private void initialize() {
            // Initialize the person table with the two columns.
            firstColumnTeacher.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
            lastColumnteacherTable.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());

                // Clear person details.
                showTeacherDetails(null);

        // Listen for selection changes and show the person details when changed.
        teacherTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTeacherDetails(newValue));
    }
        

        /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param teacher the person or null
     */
        private void showTeacherDetails(Teacher teacher) {
        if (teacher != null) {
            // Fill the labels with info from the student object.
            idLabelTeacher.setText(Integer.toString(teacher.getId()));
            nomeLabelTeacher.setText(teacher.getNome());
            cognomeLabelTeacher.setText(teacher.getCognome());
            materiaLabelTeacher.setText(teacher.getMateria());

            // Codice per aggiornare la label dei corsi
            List<Course> corsi = teacher.getCorsiInsegnati();
            String corsiAsString = corsi.stream()
                .map(Course::getNome) // Ottieni solo i nomi dei corsi
                .reduce((c1, c2) -> c1 + ", " + c2) // Combina i nomi in una stringa separata da virgole
                .orElse("Nessun corso iscritto"); // Gestione del caso in cui non ci sono corsi

                corsiLabelTeacher.setText(corsiAsString);

        } else {
            // Se lo studente è null, rimuovi tutto il testo dalle label.
            idLabelTeacher.setText("");
            nomeLabelTeacher.setText("");
            cognomeLabelTeacher.setText("");
            materiaLabelTeacher.setText("");
            corsiLabelTeacher.setText("");
        }
    }

    /*private void showStudentDetails(Student student) {
        if (student != null) {
            // Fill the labels with info from the person object.
            idLabel.setText(Integer.toString(student.getId()));
            nomeLabel.setText(student.getNome());
            cognomeLabel.setText(student.getCognome());
            classeLabel.setText(student.getClasse());
            corsiLabel.setText(student.getCorsi().toString());

            // TODO: We need a way to convert the birthday into a String!
            dataNascitaLabel.setText(student.getDataNascita());
        } else {
            // Person is null, remove all the text.
            idLabel.setText("");
            nomeLabel.setText("");
            cognomeLabel.setText("");
            classeLabel.setText("");
            corsiLabel.setText("");
            dataNascitaLabel.setText("");
        }
    }*/
        /**
         *  Called when the user clicks on the delete button.
         */
        @FXML
        private void handleDeleteTeacher() {
            int selectedIndex = teacherTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Ottieni l'insegnante selezionato
                Teacher selectedTeacher = teacherTable.getItems().get(selectedIndex);
                
                // Rimuovi dalla lista principale
                mainApp.getTeacherData().remove(selectedTeacher);
            } else {
                // Mostra un messaggio di errore se non è stato selezionato nulla
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No teacher Selected");
                alert.setContentText("Please select a teacher in the table.");
                alert.showAndWait();
            }
        }
        

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewTeacher() {
        lastTeacherId++;
        Teacher tempTeacher = new Teacher(lastTeacherId, "", "", "");
        boolean okClicked = mainApp.showTeacherEditDialog(tempTeacher);
        if (okClicked) {
            mainApp.getTeacherData().add(tempTeacher);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditTeacher() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            boolean okClicked = mainApp.showTeacherEditDialog(selectedTeacher);
            if (okClicked) {
                showTeacherDetails(selectedTeacher);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No teacher Selected");
            alert.setContentText("Please select a teacher in the table.");

            alert.showAndWait();
        }
    }

        /**
         * Is called by the main application to give a reference back to itself.
         * 
         * @param mainApp
         */
        public void setMainApp(MainApp mainApp) {
            this.mainApp = mainApp;

            for (Teacher teacher : mainApp.getTeacherData()) {
                if (teacher.getId() > lastTeacherId) {
                    lastTeacherId = teacher.getId();
                }
            }
             FilteredList<Teacher> filteredData = new FilteredList<>(mainApp.getTeacherData(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                // If filter text is empty, display all students.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare ID, name, and surname of every student with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(student.getId()).contains(lowerCaseFilter)) {
                    return true; // Filter matches ID.
                } else if (student.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (student.getCognome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Teacher> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(teacherTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        teacherTable.setItems(sortedData);
            // Add observable list data to the table
        }
    
}
