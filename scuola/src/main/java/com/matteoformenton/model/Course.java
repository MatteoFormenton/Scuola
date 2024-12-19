package com.matteoformenton.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Course {

    @JsonProperty("id")
    private final IntegerProperty id;
    @JsonProperty("nome")
    private final StringProperty nome;
    @JsonProperty("descrizione")
    private final StringProperty descrizione;
    @JsonIgnore
    private Teacher insegnante;
    @JsonIgnore
    private List<Student> studentiIscritti;

    @JsonProperty("insegnanteId")
    private final IntegerProperty insegnanteId;
    @JsonProperty("studenteId")
    private final List<Integer> studenteId;

    public Course(@JsonProperty("id") int id, 
                  @JsonProperty("nome") String nome, 
                  @JsonProperty("descrizione") String descrizione) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.descrizione = new SimpleStringProperty(descrizione);
        this.studentiIscritti = new ArrayList<>();
        this.insegnanteId = new SimpleIntegerProperty();
        this.studenteId = new ArrayList<>();
    }

    public Course() {
        this.id = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.descrizione = new SimpleStringProperty();
        this.insegnante = null; // Insegnante inizialmente null
        this.studentiIscritti = FXCollections.observableArrayList(); // Inizializzazione
        this.insegnanteId=new SimpleIntegerProperty();
        this.studenteId=FXCollections.observableArrayList();
    }
    
   /*  public Course() {
        this.id = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.descrizione = new SimpleStringProperty();
        this.insegnante = new Teacher();
        this.studentiIscritti =new ArrayList<>();
    }*/

    public Course(String nome, String descrizione) {
		this.nome = new SimpleStringProperty(nome);
		this.descrizione = new SimpleStringProperty(descrizione);
		
		// Some initial dummy data, just for convenient testing.
		this.id = new SimpleIntegerProperty(1234);
		this.insegnante = new Teacher();
        this.studentiIscritti = FXCollections.observableArrayList(); // Inizializzazione
        this.insegnanteId=new SimpleIntegerProperty();
        this.studenteId=FXCollections.observableArrayList();

	}    
    
    public Course(IntegerProperty id, String nome, String descrizione) {
		this.nome = new SimpleStringProperty(nome);
		this.descrizione = new SimpleStringProperty(descrizione);
		
		// Some initial dummy data, just for convenient testing.
		this.id = id;
		this.insegnante = new Teacher();
        this.studentiIscritti = FXCollections.observableArrayList(); // Inizializzazione
        this.insegnanteId=new SimpleIntegerProperty();
        this.studenteId=FXCollections.observableArrayList();

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

    public String getDescrizione() {
        return descrizione.get();
    }

    public void setDescrizione(String descrizione) {
        this.descrizione.set(descrizione);
    }

    public StringProperty descrizioneProperty() {
        return descrizione;
    }

    public Teacher getInsegnante() {
        return insegnante;
    }

    public void setInsegnante(Teacher insegnante) {
        this.insegnante = insegnante;
    }

    public ObservableList<Student> getStudentiIscritti() {
        return FXCollections.observableArrayList(studentiIscritti);
    }

    public void setStudentiIscritti(List<Student> studentiIscritti) {
        this.studentiIscritti = studentiIscritti;
    }

    public void aggiungiStudente(Student studente) {
        studentiIscritti.add(studente);
    }

    public void rimuoviStudente(Student studente) {
        studentiIscritti.remove(studente);
    }

    public int getInsegnanteId() {
        return insegnanteId.get();
    }

    public void setInsegnanteId(int insegnanteId) {
        this.insegnanteId.set(insegnanteId);
    }

    public List<Integer> getStudenteId() {
        return studenteId;
    }

    public void addStudentId(int id){
        this.studenteId.add(id);
    }

    public void aggiungiStudenteId(int studenteId) {
        this.studenteId.add(studenteId);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id.get() +
                ", nome=" + nome.get() +
                ", descrizione=" + descrizione.get() +
                ", insegnante=" + insegnante +
                ", studentiIscritti=" + studentiIscritti +
                '}';
    }
}
