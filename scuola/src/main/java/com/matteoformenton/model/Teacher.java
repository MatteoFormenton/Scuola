package com.matteoformenton.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Teacher {
    @JsonProperty("id")
    private final IntegerProperty id;
    @JsonProperty("nome")
    private final StringProperty nome;
    @JsonProperty("cognome")
    private final StringProperty cognome;
    @JsonProperty("materia")
    private final StringProperty materia;
    @JsonIgnore
    private final List<Course> corsiInsegnati;

    public Teacher(@JsonProperty("id") int id, @JsonProperty("nome") String nome, @JsonProperty("cognome") String cognome, @JsonProperty("materia") String materia) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.materia = new SimpleStringProperty(materia);
        this.corsiInsegnati = new ArrayList<>();
    }

    public Teacher(){
        this.id = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.cognome = new SimpleStringProperty();
        this.materia = new SimpleStringProperty();
        this.corsiInsegnati = new ArrayList<>();
    }

    public Teacher(String nome, String cognome) {
		this.nome = new SimpleStringProperty(nome);
		this.cognome = new SimpleStringProperty(cognome);
		
		// Some initial dummy data, just for convenient testing.
		this.id = new SimpleIntegerProperty();
        this.materia = new SimpleStringProperty("prova");
        this.corsiInsegnati = new ArrayList<>();

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

    public String getMateria() {
        return materia.get();
    }

    public void setMateria(String materia) {
        this.materia.set(materia);
    }

    public StringProperty materiaProperty() {
        return materia;
    }

    public List<Course> getCorsiInsegnati() {
        return corsiInsegnati;
    }

    public void aggiungiCorso(Course course) {
        if (!this.corsiInsegnati.contains(course)) { // Evita duplicati
            this.corsiInsegnati.add(course);
        }
    }
    

    public void rimuoviCorso(Course corso) {
        corsiInsegnati.remove(corso);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id.get() +
                ", nome=" + nome.get() +
                ", cognome=" + cognome.get() +
                ", materia=" + materia.get() +
                ", corsiInsegnati=" + corsiInsegnati +
                '}';
    }
}
