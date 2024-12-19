package com.matteoformenton.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.List;

import com.matteoformenton.MainApp;
import com.matteoformenton.model.Course;
import com.matteoformenton.model.Student;
import com.matteoformenton.model.Teacher;
import com.matteoformenton.util.DateUtil;

public class CourseEditDialogController {

    @FXML
    private TextField idLabelCourse;
    @FXML
    private TextField nomeLabelCourse;
    @FXML
    private TextField descrizioneLabelCourse;
    /*@FXML
    private TextField insegnateLabelCourse;*/


    private Stage dialogStage;
    private MainApp mainApp;
    //private Person person;
    private boolean okClicked = false;
    private Student student;
    private Course course;


    @FXML
    private ComboBox<Teacher> comboBoxInsegnanti;
    private List<Teacher> teachers; // Lista degli insegnanti

        
            /**
             * Initializes the controller class. This method is automatically called
             * after the fxml file has been loaded.
             */
            @FXML
            private void initialize() {

            }
        
            /**
             * Sets the stage of this dialog.
             * 
             * @param dialogStage
             */
            public void setDialogStage(Stage dialogStage) {
                this.dialogStage = dialogStage;
            }
        
            /**
             * Sets the person to be edited in the dialog.
             * 
             * @param course
             */
            public void setCourse(Course corso) {
            this.course = corso;

            //idLabelCourse.setText(Integer.toString(corso.getId()));
            nomeLabelCourse.setText(corso.getNome());
            descrizioneLabelCourse.setText(corso.getDescrizione());
            
            Teacher insegnante = course.getInsegnante();
/*             if (insegnante == null || 
                isTeacherEmpty(insegnante)) {
                insegnateLabelCourse.setText("Nessun insegnante assegnato al corso");
            } else {
                insegnateLabelCourse.setText(insegnante.toString());
            }*/
            

        /*Conversione della lista di corsi in una stringa leggibile
        List<Student> studentiIscritti = course.getStudentiIscritti();
        String studentiAsString = studentiIscritti.stream()
            .map(Student::getNome) // Assumendo che Student abbia un metodo getNome()
            .reduce((s1, s2) -> s1 + ", " + s2) // Combina i nomi in una stringa separata da virgole
            .orElse("Nessuno studente iscritto"); // Gestisce il caso in cui la lista sia vuota
        
            studentiLabelCourse.setText(studentiAsString);*/
        


        // birthdayField.setPromptText("dd.mm.yyyy");
        }

public void setTeachers(List<Teacher> teachers) {
    this.teachers = teachers;
    
    if (comboBoxInsegnanti != null) {
        comboBoxInsegnanti.getItems().clear();
        comboBoxInsegnanti.getItems().addAll(teachers);

        // Imposta una cell factory per visualizzare solo il nome e il cognome
        comboBoxInsegnanti.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(Teacher item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " " + item.getCognome());
                }
            }
        });

        comboBoxInsegnanti.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Teacher item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " " + item.getCognome());
                }
            }
        });
    }
}


       /* public void setStudent(Student studente) {
        this.student = studente;

        idStudenteField.setText(Integer.toString(studente.getId()));
        nomeStudenteField.setText(studente.getNome());
        cognomeStudenteField.setText(studente.getCognome());
        classeStudenteField.setText(studente.getClasse());
        corsoStudenteField.setText(studente.getCorsi());
        birthdayField.setText(studente.getDataNascita());
       // birthdayField.setPromptText("dd.mm.yyyy");
    }*/

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            course.setNome(nomeLabelCourse.getText());
            course.setDescrizione(descrizioneLabelCourse.getText());
            Teacher selectedTeacher = comboBoxInsegnanti.getSelectionModel().getSelectedItem();
            course.setInsegnante(selectedTeacher);
            selectedTeacher.aggiungiCorso(course);
            course.setInsegnanteId(selectedTeacher.getId());
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        
        // Validazione Nome
        if (nomeLabelCourse.getText() == null || nomeLabelCourse.getText().trim().isEmpty()) {
            errorMessage += "Nome studente non valido! Deve essere compilato.\n";
        }
    
        // Validazione Cognome
        if (descrizioneLabelCourse.getText() == null || descrizioneLabelCourse.getText().trim().isEmpty()) {
            errorMessage += "Cognome studente non valido! Deve essere compilato.\n";
        }
    
   // Validazione Data di nascita - formato gg/mm/aaaa
    
        if (errorMessage.isEmpty()) {
            return true; // Input è valido
        } else {
            // Mostra messaggio di errore
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Campi non validi");
            alert.setHeaderText("Correggi i seguenti errori:");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean isTeacherEmpty(Teacher teacher) {
        return teacher.getNome() == null || teacher.getNome().trim().isEmpty() ||
               teacher.getCognome() == null || teacher.getCognome().trim().isEmpty() ||
               teacher.getMateria() == null || teacher.getMateria().trim().isEmpty();
    }
    
}