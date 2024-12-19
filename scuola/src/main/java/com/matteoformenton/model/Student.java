package com.matteoformenton.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    @JsonProperty("id")
    private IntegerProperty id;
    @JsonProperty("nome")
    private StringProperty nome;
    @JsonProperty("cognome")
    private StringProperty cognome;
    @JsonProperty("dataNascita")
    private StringProperty dataNascita;
    @JsonProperty("classe")
    private StringProperty classe;
    @JsonIgnore
    private List<Course> corsi;


@JsonCreator
    public Student(@JsonProperty("id") int id, 
                   @JsonProperty("nome") String nome, 
                   @JsonProperty("cognome") String cognome, 
                   @JsonProperty("dataNascita") String dataNascita, 
                   @JsonProperty("classe") String classe) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.dataNascita = new SimpleStringProperty(dataNascita);
        this.classe = new SimpleStringProperty(classe);
        this.corsi = new ArrayList<>();
    }

    public Student(String nome, String cognome) {
		this.nome = new SimpleStringProperty(nome);
		this.cognome = new SimpleStringProperty(cognome);
		
		// Some initial dummy data, just for convenient testing.
		this.id = new SimpleIntegerProperty(1234);
		this.dataNascita = new SimpleStringProperty(" ");
        this.corsi = new ArrayList<>();
        this.classe=new SimpleStringProperty("2A");
	}

    public Student() {
        this.id = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.cognome = new SimpleStringProperty();
        this.dataNascita = new SimpleStringProperty();
        this.classe = new SimpleStringProperty();
        this.corsi = new ArrayList<>();
    }
    


    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public String getCognome() {
        return cognome.get();
    }

    public void setCognome(String cognome) {
        this.cognome.set(cognome);
    }

    public StringProperty cognomeProperty() {
        return cognome;
    }

    public String getDataNascita() {
        return dataNascita.get();
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita.set(dataNascita);
    }

    public StringProperty dataNascitaProperty() {
        return dataNascita;
    }

    public String getClasse() {
        return classe.get();
    }

    public void setClasse(String classe) {
        this.classe.set(classe);
    }

    public StringProperty classeProperty() {
        return classe;
    }

    public List<Course> getCorsi() {
        return corsi;
    }

    
    public void aggiungiCorsoSingolo(Course corsoSingolo) {
        if (!corsi.contains(corsoSingolo)) {
            corsi.add(corsoSingolo); // Aggiunge singolarmente senza sovrascrivere.
        }
    }
    
    public void aggiungiCorsiMultipli(List<Course> nuoviCorsi) {
        for (Course corso : nuoviCorsi) {
            if (!corsi.contains(corso)) {
                corsi.add(corso); // Aggiunge ogni corso senza sovrascrivere.
            }
        }
    }
    

    public void aggiungiCorso(Course corso) {
        corsi.add(corso);
    }

    public void rimuoviCorso(Course corso) {
        corsi.remove(corso);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id.get() +
                ", nome=" + nome.get() +
                ", cognome=" + cognome.get() +
                ", dataNascita=" + dataNascita.get() +
                ", classe=" + classe.get() +
                ", corsi=" + corsi +
                '}';
    }
}
