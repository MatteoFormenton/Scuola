package com.matteoformenton.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.List;

import com.matteoformenton.model.Course;
import com.matteoformenton.model.Student;
import com.matteoformenton.util.DateUtil;

/**
 * Dialog to edit details of a person.
 * 
 * @author Marco Jakob
 */
public class StudentEditDialogController {

    @FXML
    private TextField idStudenteField;
    @FXML
    private TextField nomeStudenteField;
    @FXML
    private TextField cognomeStudenteField;
    @FXML
    private TextField dataNascitaField;
    @FXML
    private TextField classeStudenteField;
    /*@FXML
    private TextField corsoStudenteField;*/


    private Stage dialogStage;
    //private Person person;
    private boolean okClicked = false;
    private Student student;
    
        /**
         * Initializes the controller class. This method is automatically called
         * after the fxml file has been loaded.
         */
        @FXML
        private void initialize() {
            idStudenteField.setEditable(false);
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
         * @param student
         */
        public void setStudent(Student studente) {
            this.student = studente;

            idStudenteField.setText(Integer.toString(studente.getId()));
            nomeStudenteField.setText(studente.getNome());
            cognomeStudenteField.setText(studente.getCognome());
            classeStudenteField.setText(studente.getClasse());

        // Conversione della lista di corsi in una stringa leggibile
          /*   List<Course> corsi = studente.getCorsi();
            String corsiAsString = corsi.stream()
            .map(Course::getNome) // Ottieni solo i nomi dei corsi
            .reduce((c1, c2) -> c1 + ", " + c2) // Combina i nomi in una stringa separata da virgole
            .orElse("Nessun corso iscritto"); // Gestisce il caso in cui non ci siano corsi
            corsoStudenteField.setText(corsiAsString);*/

            dataNascitaField.setText(studente.getDataNascita());
        // birthdayField.setPromptText("dd.mm.yyyy");
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
            int id = Integer.parseInt(idStudenteField.getText());
            student.setId(id);
            student.setNome(nomeStudenteField.getText());
            student.setCognome(cognomeStudenteField.getText());
            student.setClasse(classeStudenteField.getText());
           // farlo dopo
           // student.setcorso(corsoStudenteField.getText());
            student.setDataNascita(dataNascitaField.getText());

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

    private boolean isDateValid(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate.parse(dateStr, formatter); // Prova a convertire la stringa in una data
            return true;
        } catch (DateTimeParseException e) {
            return false;
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
        if (nomeStudenteField.getText() == null || nomeStudenteField.getText().trim().isEmpty()) {
            errorMessage += "Nome studente non valido! Deve essere compilato.\n";
        }
    
        // Validazione Cognome
        if (cognomeStudenteField.getText() == null || cognomeStudenteField.getText().trim().isEmpty()) {
            errorMessage += "Cognome studente non valido! Deve essere compilato.\n";
        }
    
        // Validazione Classe - solo lettere e numeri
        String lettereENumeriRegex = "^[\\p{L}0-9]+$";
        if (classeStudenteField.getText() == null || classeStudenteField.getText().isEmpty()) {
            errorMessage += "Classe non valida! Deve essere compilata.\n";
        } else if (!classeStudenteField.getText().matches(lettereENumeriRegex)) {
            errorMessage += "Classe deve contenere solo lettere e numeri.\n";
        }
    
   // Validazione Data di nascita - formato gg/mm/aaaa
   if (dataNascitaField.getText() == null || dataNascitaField.getText().isEmpty()) {
    errorMessage += "Data di nascita non valida! Deve essere compilata.\n";
} else if (!isDateValid(dataNascitaField.getText())) {
    errorMessage += "Formato data di nascita non corretto! Usa il formato gg.mm.aaaa.\n";
}
    
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
    
    
}
