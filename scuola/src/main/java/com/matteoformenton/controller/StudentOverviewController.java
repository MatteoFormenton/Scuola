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
import java.util.stream.Collectors;

import com.matteoformenton.MainApp;
    import com.matteoformenton.model.Course;
    import com.matteoformenton.model.Person;
    import com.matteoformenton.model.Student;
import com.matteoformenton.model.Teacher;
import com.matteoformenton.util.DateUtil;

    public class StudentOverviewController {
    /*   @FXML
        private TableView<Person> personTable;
        @FXML
        private TableColumn<Person, String> firstNameColumn;
        @FXML
        private TableColumn<Person, String> lastNameColumn;*/

        @FXML
        private TableView<Student> studentTable;
        @FXML
        private TableColumn<Student, Integer> idColumn;
        @FXML
        private TableColumn<Student, String> firstColumn;
        @FXML
        private TableColumn<Student, String> lastColumn;
        /*@FXML
        private Label firstNameLabel;
        @FXML
        private Label lastNameLabel;
        @FXML
        private Label streetLabel;
        @FXML
        private Label postalCodeLabel;
        @FXML
        private Label cityLabel;
        @FXML
        private Label birthdayLabel;*/

        @FXML
        private Label idLabel;
        @FXML
        private Label nomeLabel;
        @FXML
        private Label cognomeLabel;
        @FXML
        private Label dataNascitaLabel;
        @FXML
        private Label classeLabel;
        @FXML
        private Label corsiLabel;

        // Reference to the main application.
        private MainApp mainApp;

        private int lastStudentId = 0;

        @FXML
        private TextField searchField;

        /**
         * The constructor.
         * The constructor is called before the initialize() method.
         */
        public StudentOverviewController() {
        }

        /**
         * Initializes the controller class. This method is automatically called
         * after the fxml file has been loaded.
         */
        @FXML
        private void initialize() {
            
            // Initialize the person table with the two columns.
            idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
            firstColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
            lastColumn.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());

                // Clear person details.
                showStudentDetails(null);

        // Listen for selection changes and show the person details when changed.
        studentTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showStudentDetails(newValue));
    }
        

        /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param student the person or null
     */
        private void showStudentDetails(Student student) {
        if (student != null) {
            // Fill the labels with info from the student object.
            idLabel.setText(Integer.toString(student.getId()));
            nomeLabel.setText(student.getNome());
            cognomeLabel.setText(student.getCognome());
            classeLabel.setText(student.getClasse());

            // Codice per aggiornare la label dei corsi
List<Course> corsi = student.getCorsi();
String corsiAsString = corsi.stream()
    .map(Course::getNome) // Ottieni solo i nomi dei corsi
    .collect(Collectors.joining(", ")); // Combina i nomi in una stringa separata da virgole

if (corsiAsString.isEmpty()) {
    corsiAsString = "Nessun corso iscritto"; // Gestione del caso in cui non ci sono corsi
}

            corsiLabel.setText(corsiAsString);

            // TODO: We need a way to convert the birthday into a String!
            dataNascitaLabel.setText(student.getDataNascita());
        } else {
            // Se lo studente è null, rimuovi tutto il testo dalle label.
            idLabel.setText("");
            nomeLabel.setText("");
            cognomeLabel.setText("");
            classeLabel.setText("");
            corsiLabel.setText("");
            dataNascitaLabel.setText("");
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
        private void handleDeleteStudent() {
            int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Ottieni l'insegnante selezionato
                Student selectedStudent = studentTable.getItems().get(selectedIndex);
                
                // Rimuovi dalla lista principale
                mainApp.getStudentData().remove(selectedStudent);
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
    private void handleNewStudent() {
        lastStudentId++;
        Student tempStudent = new Student(lastStudentId, "", "", "", "");
        boolean okClicked = mainApp.showStudentEditDialog(tempStudent);
        if (okClicked) {
            mainApp.getStudentData().add(tempStudent);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            boolean okClicked = mainApp.showStudentEditDialog(selectedStudent);
            if (okClicked) {
                showStudentDetails(selectedStudent);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select a Student in the table.");

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

            for (Student student : mainApp.getStudentData()) {
                if (student.getId() > lastStudentId) {
                    lastStudentId = student.getId();
                }
            }

                    // Add observable list data to the table
        FilteredList<Student> filteredData = new FilteredList<>(mainApp.getStudentData(), p -> true);

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
        SortedList<Student> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(studentTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        studentTable.setItems(sortedData);

            // Add observable list data to the table
           // studentTable.setItems(mainApp.getStudentData());
        }
    }
