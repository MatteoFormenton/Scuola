package com.matteoformenton.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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

public class CourseOverviewController {
    
    /*   @FXML
        private TableView<Person> personTable;
        @FXML
        private TableColumn<Person, String> firstNameColumn;
        @FXML
        private TableColumn<Person, String> lastNameColumn;*/

        @FXML
        private TableView<Course> courseTable;
        @FXML
        private TableColumn<Course, Integer> firstColumnCourse;
        @FXML
        private TableColumn<Course, String> lastColumnCourse;

        @FXML
        private TableView<Student> corsoStudente;
        @FXML
        private TableColumn<Student, String> nomeStudente;
        @FXML
        private TableColumn<Student, String> cognomeStudente;

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
        private Label idLabelCourse;
        @FXML
        private Label nomeLabelCourse;
        @FXML
        private Label descrizioneLabelCourse;
        @FXML
        private Label insegnateLabelCourse;
        //@FXML
       // private Label studentiLabelCourse;


        // Reference to the main application.
        private MainApp mainApp;

        private int lastCourseid = 0;

        @FXML
        private TextField searchField;
        /**
         * The constructor.
         * The constructor is called before the initialize() method.
         */
        public CourseOverviewController() {
        }

        /**
         * Initializes the controller class. This method is automatically called
         * after the fxml file has been loaded.
         */
        @FXML
private void initialize() {
    // Impostare la configurazione della tabella dei corsi
    firstColumnCourse.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
    lastColumnCourse.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());

    // Impostare la configurazione della tabella studenti
    nomeStudente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
    cognomeStudente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCognome()));

    // Clear course details
    showCourseDetails(null);

    // Ascoltare la selezione nella courseTable
    courseTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showCourseDetails(newValue));
}

        

        /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param student the person or null
     */
    private void showCourseDetails(Course course) {
        if (course != null) {
            // Aggiornare le label con le informazioni del corso
            idLabelCourse.setText(Integer.toString(course.getId()));
            nomeLabelCourse.setText(course.getNome());
            descrizioneLabelCourse.setText(course.getDescrizione());
    
            Teacher insegnante = course.getInsegnante();
            if (insegnante == null || isTeacherEmpty(insegnante)) {
                insegnateLabelCourse.setText("Nessun insegnante assegnato al corso");
            } else {
                insegnateLabelCourse.setText(insegnante.getNome() + " " + insegnante.getCognome());
            }
    
            // Aggiornare la lista degli studenti iscritti nella TableView
            List<Student> studentiIscritti = course.getStudentiIscritti();
    
            if (studentiIscritti.isEmpty()) {
                // Se nessuno studente è iscritto, mostra una sola entry con il messaggio
                List<Student> listaVuotaConMessaggio = List.of(new Student("Nessun studente", "iscritto"));
                corsoStudente.setItems(FXCollections.observableArrayList(listaVuotaConMessaggio));
            } else {
                corsoStudente.setItems(FXCollections.observableArrayList(studentiIscritti));
            }
    
        } else {
            // Se il corso è null, svuotare le informazioni e la lista studenti
            idLabelCourse.setText("");
            nomeLabelCourse.setText("");
            descrizioneLabelCourse.setText("");
            insegnateLabelCourse.setText("");
            corsoStudente.setItems(FXCollections.observableArrayList());
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
        private void handleDeleteCourse() {
            int selectedIndex = courseTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Ottieni l'insegnante selezionato
                Course selectedCourse = courseTable.getItems().get(selectedIndex);
                
                // Rimuovi dalla lista principale
                mainApp.getCourseData().remove(selectedCourse);
            } else {
                // Mostra un messaggio di errore se non è stato selezionato nulla
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No course Selected");
                alert.setContentText("Please select a course in the table.");
                alert.showAndWait();
            }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewCourse() {

        Teacher emptyTeacher = new Teacher(0, "", "", "");
        lastCourseid++;
        Course tempCourse = new Course(lastCourseid, "", "");
        boolean okClicked = mainApp.showCourseEditDialog(tempCourse);
        if (okClicked) {
            mainApp.getCourseData().add(tempCourse);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditCourse() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            boolean okClicked = mainApp.showCourseEditDialog(selectedCourse);
            if (okClicked) {
                showCourseDetails(selectedCourse);
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

    @FXML
    private void handleEditTeacher() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            boolean okClicked = mainApp.showCourseEditDialog(selectedCourse);
            if (okClicked) {
                showCourseDetails(selectedCourse);
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
/*********************************************************************************************************************************************************** */
@FXML

private void handleAddStudent() {
    // Ottieni il corso selezionato
    Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
    if (selectedCourse != null) {
        // Mostra la finestra di dialogo per aggiungere uno studente
        boolean okClicked = mainApp.showStudentEditAddDialog(selectedCourse);

        // Aggiorna la tabella degli studenti se l'operazione è andata a buon fine
        if (okClicked) {
            corsoStudente.setItems(selectedCourse.getStudentiIscritti());
        }
    } else {
        // Mostra un avviso se nessun corso è selezionato
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Nessun corso selezionato");
        alert.setHeaderText("Nessun corso selezionato");
        alert.setContentText("Per favore, seleziona un corso dalla tabella.");
        alert.showAndWait();
    }
}
/*private void handleNewStudentCourse() {
    // Controlla se la TableView è vuota
    if (corsoStudente.getItems().isEmpty()) {
        // Se la tabella è vuota, crea uno studente con valori nulli
        Student tempStudent = new Student(0, null, null, null, null);
        boolean okClicked = mainApp.showStudentEditAddDialog(tempStudent);
        if (okClicked) {
            mainApp.getStudentData().add(tempStudent);
        }
    } else {
        // Seleziona lo studente dalla tabella
        Student selectedStudent = corsoStudente.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Copia i valori dello studente selezionato in tempStudent
            Student tempStudent = new Student(
                selectedStudent.getId(),
                selectedStudent.getNome(),
                selectedStudent.getCognome(),
                selectedStudent.getDataNascita(),
                selectedStudent.getClasse()
            );

            // Puoi includere anche i corsi se la classe Student li gestisce direttamente
            // tempStudent.setCorsi(selectedStudent.getCorsi());

            boolean okClicked = mainApp.showStudentEditAddDialog(tempStudent);
            if (okClicked) {
                mainApp.getStudentData().add(tempStudent);
            }
        } else {
            // Gestisci il caso in cui nessuno studente sia selezionato nella tabella
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText("Nessuno studente selezionato");
            alert.setContentText("Per favore, seleziona uno studente dalla tabella.");
            alert.showAndWait();
        }
    }
}*/
   @FXML
private void handleDeleteStudentCourse() {
    // Ottieni il corso selezionato
    Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
    
    if (selectedCourse != null) {
        // Ottieni lo studente selezionato nella tabella corsoStudente
        Student selectedStudent = corsoStudente.getSelectionModel().getSelectedItem();
        
        if (selectedStudent != null) {
            // Conferma la rimozione
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Conferma Rimozione");
            alert.setHeaderText("Rimuovere Studente dal Corso?");
            alert.setContentText("Sei sicuro di voler rimuovere lo studente " + 
                                  selectedStudent.getNome() + " " + selectedStudent.getCognome() + 
                                  " dal corso " + selectedCourse.getNome() + "?");

            // Mostra la finestra di dialogo e verifica il pulsante selezionato
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Rimuovi lo studente dal corso
                    selectedCourse.rimuoviStudente(selectedStudent);
                    selectedStudent.rimuoviCorso(selectedCourse);

                    // Aggiorna la tabella corsoStudente
                    corsoStudente.setItems(FXCollections.observableArrayList(selectedCourse.getStudentiIscritti()));

                    // Debug: Stampa il risultato
                    System.out.println("Studente rimosso: " + selectedStudent.getNome() + " " + selectedStudent.getCognome());
                    System.out.println("Lista aggiornata degli studenti iscritti al corso: ");
                    selectedCourse.getStudentiIscritti().forEach(student -> 
                        System.out.println(student.getNome() + " " + student.getCognome())
                    );
                }
            });
        } else {
            // Mostra un avviso se nessuno studente è stato selezionato
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nessuno studente selezionato");
            alert.setHeaderText("Nessuno studente selezionato");
            alert.setContentText("Per favore, seleziona uno studente dalla tabella per rimuoverlo.");
            alert.showAndWait();
        }
    } else {
        // Mostra un avviso se nessun corso è stato selezionato
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Nessun corso selezionato");
        alert.setHeaderText("Nessun corso selezionato");
        alert.setContentText("Per favore, seleziona un corso dalla tabella.");
        alert.showAndWait();
    }
}



/**
 * Called when the user clicks the new button. Opens a dialog to edit
 * details for a new person.
 *//* 
@FXML
private void handleNewStudentCourse() {
    // Apri la finestra di dialogo per la selezione dello studente
    Student selectedStudent = mainApp.showStudentEditAddDialog();
    if (selectedStudent != null) {
        // Aggiungi lo studente alla lista degli studenti iscritti
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            selectedCourse.getStudentiIscritti().add(selectedStudent);
            showCourseDetails(selectedCourse); // Aggiorna la tabella con l'elenco aggiornato
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Course Selected");
            alert.setHeaderText("No course selected");
            alert.setContentText("Select a course before adding a student.");
            alert.showAndWait();
        }
    }
}*/


/**
 * Called when the user clicks the edit button. Opens a dialog to edit
 * details for the selected person.
 */
/* 
@FXML
private void handleEditStudentCourse() {
    Student selectedStudent = corsoStudente.getSelectionModel().getSelectedItem();
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
}*/
        /**
         * Is called by the main application to give a reference back to itself.
         * 
         * @param mainApp
         */
        public void setMainApp(MainApp mainApp) {
            this.mainApp = mainApp;

            // 1. Crea una FilteredList basata sui dati dei corsi.
            FilteredList<Course> filteredData = new FilteredList<>(mainApp.getCourseData(), p -> true);
        
            // 2. Imposta un listener sulla textProperty del campo di ricerca (searchField).
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(course -> {
                    // Se il testo del filtro è vuoto, mostra tutti i corsi.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
        
                    // Confronta ID e nome del corso con il testo del filtro.
                    String lowerCaseFilter = newValue.toLowerCase();
        
                    // Verifica se l'ID del corso o il nome corrispondono al filtro.
                    if (String.valueOf(course.getId()).contains(lowerCaseFilter)) {
                        return true; // Il filtro corrisponde all'ID.
                    } else if (course.getNome().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Il filtro corrisponde al nome.
                    }
        
                    return false; // Nessuna corrispondenza.
                });
            });
        
            // 3. Wrappa la FilteredList in una SortedList per ordinare i dati.
            SortedList<Course> sortedData = new SortedList<>(filteredData);
        
            // 4. Collega il comparatore della SortedList a quello della TableView.
            sortedData.comparatorProperty().bind(courseTable.comparatorProperty());
        
            // 5. Aggiungi i dati ordinati (e filtrati) alla tabella.
            courseTable.setItems(sortedData);
        }

        private boolean isTeacherEmpty(Teacher teacher) {
            return teacher.getNome() == null || teacher.getNome().trim().isEmpty() ||
                   teacher.getCognome() == null || teacher.getCognome().trim().isEmpty() ||
                   teacher.getMateria() == null || teacher.getMateria().trim().isEmpty();
        }
}
