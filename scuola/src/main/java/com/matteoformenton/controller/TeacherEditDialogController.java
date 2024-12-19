package com.matteoformenton.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.matteoformenton.MainApp;
import com.matteoformenton.model.Course;
import com.matteoformenton.model.Student;
import com.matteoformenton.model.Teacher;
import com.matteoformenton.util.DateUtil;

public class TeacherEditDialogController {
    
    
    @FXML
    private TextField nomeTeacherField;
    @FXML
    private TextField cognomeTeacherField;
    @FXML
    private TextField materiaTeacherField;
    /*@FXML
    private TextField corsoTeacherField;*/

    /*@FXML
    private ComboBox<Course> corsoTeacherComboBox;*/

    private Stage dialogStage;
    //private Person person;
    private boolean okClicked = false;
    private Teacher teacher;
        private MainApp mainApp;
        
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
             * @param teacher
             */
            public void setTeacher(Teacher insegnante) {
                this.teacher = insegnante;
        
                nomeTeacherField.setText(insegnante.getNome());
                cognomeTeacherField.setText(insegnante.getCognome());
                materiaTeacherField.setText(insegnante.getMateria());
        
                // Imposta il valore selezionato nella ComboBox in base ai corsi dell'insegnante
               /*  if (insegnante.getCorsiInsegnati() != null) {
                    for (Course course : insegnante.getCorsiInsegnati()) {
                        if (mainApp.getCourseData().contains(course)) {
                            corsoTeacherComboBox.setValue(course);
                        }
                    }
                }*/
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
         * Called when the user clicks ok.
         */
        @FXML
        private void handleOk() {
            if (isInputValid()) {
                teacher.setNome(nomeTeacherField.getText());
                teacher.setCognome(cognomeTeacherField.getText());
                teacher.setMateria(materiaTeacherField.getText());
               // farlo dopo
               // student.setcorso(corsoStudenteField.getText());
    
    
                okClicked = true;
                dialogStage.close();
            }
        }
    
        /**
         * Called when the user clicks cancel.
         */
        @FXML
        private void handleCancel() {
            dialogStage.close();
        }
    
        /**
         * Validates the user input in the text fields.
         * 
         * @return true if the input is valid
         */
        private boolean isInputValid() {
            String errorMessage = "";
        
            // Validazione Nome
            if (nomeTeacherField.getText() == null || nomeTeacherField.getText().trim().isEmpty()) {
                errorMessage += "Nome insegnante non valido! Deve essere compilato.\n";
            }
        
            // Validazione Cognome
            if (cognomeTeacherField.getText() == null || cognomeTeacherField.getText().trim().isEmpty()) {
                errorMessage += "Cognome insegnante non valido! Deve essere compilato.\n";
            }
        
            // Validazione Materia (solo caratteri alfabetici)
            if (materiaTeacherField.getText() == null || !materiaTeacherField.getText().matches("[a-zA-Z]+")) {
                errorMessage += "Materia non valida! Inserire solo caratteri alfabetici.\n";
            }
        
            // Validazione Corso (solo caratteri alfabetici)
          /*   if (corsoTeacherField.getText() == null || !corsoTeacherField.getText().matches("[a-zA-Z]+")) {
                errorMessage += "Corso non valido! Inserire solo caratteri alfabetici.\n";
            }*/
        
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
    
            public void setMainApp(MainApp mainApp) {
            this.mainApp = mainApp;
        //populateComboBox();
    }
/* 
private void populateComboBox() {
    if (mainApp != null) {
        List<Course> courses = mainApp.getCourseData();
        corsoTeacherComboBox.getItems().clear();
        
        if (courses != null && !courses.isEmpty()) {
            corsoTeacherComboBox.getItems().addAll(courses);
            
            // Imposta la cellFactory per visualizzare solo il nome dei corsi
            corsoTeacherComboBox.setCellFactory(comboBox -> new ListCell<Course>() {
                @Override
                protected void updateItem(Course course, boolean empty) {
                    super.updateItem(course, empty);
                    setText(empty || course == null ? "" : course.getNome());
                }
            });
            
            // Imposta la visualizzazione del valore selezionato
            corsoTeacherComboBox.setButtonCell(new ListCell<Course>() {
                @Override
                protected void updateItem(Course course, boolean empty) {
                    super.updateItem(course, empty);
                    setText(empty || course == null ? "" : course.getNome());
                }
            });
            
            System.out.println("Corsi aggiunti alla ComboBox con successo.");
        } else {
            System.out.println("Nessun corso da caricare nella ComboBox.");
        }
    } else {
        System.err.println("MainApp non è stato impostato correttamente.");
    }
}*/

    
    

    
    
    
}
