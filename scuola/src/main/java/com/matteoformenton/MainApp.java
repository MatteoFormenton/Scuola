package com.matteoformenton;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import com.matteoformenton.controller.StudentEditDialogController;
import com.matteoformenton.controller.StudentOverviewController;
import com.matteoformenton.controller.TeacherEditDialogController;
import com.matteoformenton.controller.TeacherOverviewController;
import com.matteoformenton.controller.CourseEditDialogController;
import com.matteoformenton.controller.CourseOverviewController;
import com.matteoformenton.controller.RootLayoutController;
import com.matteoformenton.controller.StudentEditAddDialogController;
import com.matteoformenton.model.Course;
import com.matteoformenton.model.Student;
import com.matteoformenton.model.Teacher;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.application.Application;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
   // private ObservableList <Person> personData=FXCollections.observableArrayList();
    private ObservableList<Student> studentData = FXCollections.observableArrayList();
    private ObservableList<Teacher> teacherData = FXCollections.observableArrayList();
    private ObservableList<Course> courseData = FXCollections.observableArrayList();


    public MainApp() {
		// Add some sample data
        studentData.add(new Student(1,"Carlo","Cipolla","02.08.2004","2A"));

        teacherData.add(new Teacher(1,"Cipolla", "Franco","Storia"));

        courseData.add(new Course(1,"Alpinismo", "pericoloso"));

	}
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        if (rootLayout != null) {

            showStudentOverview();
            showTeacherOverview();
            showCourseOverview();
            /*showAtleti();
            showAttivita();
            showIscrizioni();*/
        }
        primaryStage.setMinWidth(1380);
        primaryStage.setMinHeight(800);    
    this.primaryStage.setResizable(false);
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
            
            // Qui non caricare il file JSON automaticamente
            showStudentOverview();
           // showTeacherOverview();
           // showCourseOverview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showStudentOverview() {
        try {
            // Carica la vista StudentOverview.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/com/matteoformenton/view/StudentOverview.fxml"));
            AnchorPane studentOverview = loader.load();
    
            // Accedi al nodo centrale del rootLayout (AnchorPane)
            AnchorPane centerPane = (AnchorPane) rootLayout.getCenter();
    
            // Trova il TabPane dentro l'AnchorPane
            TabPane tabPane = (TabPane) centerPane.getChildren().get(0);
    
            // Trova il Tab degli studenti
            Tab studentTab = tabPane.getTabs().get(0);
            AnchorPane studentContent = (AnchorPane) studentTab.getContent();
    
            // Aggiungi la vista degli studenti al contenuto del Tab
            studentContent.getChildren().clear();
            studentContent.getChildren().add(studentOverview);
            AnchorPane.setTopAnchor(studentOverview, 0.0);
            AnchorPane.setBottomAnchor(studentOverview, 0.0);
            AnchorPane.setLeftAnchor(studentOverview, 0.0);
            AnchorPane.setRightAnchor(studentOverview, 0.0);
    
            // Dai accesso al controller alla main app
            StudentOverviewController controller = loader.getController();
            controller.setMainApp(this);
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTeacherOverview() {
        try {
            // Carica la vista TeacherOverview.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TeacherOverview.fxml"));
            AnchorPane teacherOverview = loader.load();
    
            // Accedi al nodo centrale del rootLayout (AnchorPane)
            AnchorPane centerPane = (AnchorPane) rootLayout.getCenter();
    
            // Trova il TabPane nel centerPane
            TabPane tabPane = null;
            for (Node node : centerPane.getChildren()) {
                if (node instanceof TabPane) {
                    tabPane = (TabPane) node;
                    break;
                }
            }
    
            if (tabPane == null) {
                System.out.println("TabPane non trovato nel centro del layout!");
                return;
            }
    
            // Debug: stampa il numero di schede nel TabPane
            ObservableList<Tab> tabs = tabPane.getTabs();
            System.out.println("Numero totale di schede nel TabPane: " + tabs.size());
    
            for (int i = 0; i < tabs.size(); i++) {
                System.out.println("Scheda " + i + ": " + tabs.get(i).getText());
            }
    
            // Accedi alla scheda degli insegnanti
            if (tabs.size() > 1) { // Controlla che ci siano abbastanza schede
                Tab teacherTab = tabs.get(1); // Scheda "Insegnanti"
                System.out.println("Scheda selezionata: " + teacherTab.getText());
    
                if (teacherTab.getContent() == null) {
                    System.out.println("Il contenuto della scheda è null, lo inizializzo come AnchorPane.");
                    AnchorPane newContent = new AnchorPane();
                    teacherTab.setContent(newContent);
                }
    
                if (teacherTab.getContent() instanceof AnchorPane) {
                    AnchorPane teacherContent = (AnchorPane) teacherTab.getContent();
    
                    // Aggiungi la vista degli insegnanti al contenuto della scheda
                    teacherContent.getChildren().clear();
                    teacherContent.getChildren().add(teacherOverview);
    
                    // Imposta gli anchor per il layout
                    AnchorPane.setTopAnchor(teacherOverview, 0.0);
                    AnchorPane.setBottomAnchor(teacherOverview, 0.0);
                    AnchorPane.setLeftAnchor(teacherOverview, 0.0);
                    AnchorPane.setRightAnchor(teacherOverview, 0.0);
    
                    // Dai accesso al controller alla main app
                    TeacherOverviewController controller = loader.getController();
                    controller.setMainApp(this);
                } else {
                    System.out.println("Il contenuto della scheda non è un AnchorPane, lo sostituisco.");
                    AnchorPane newContent = new AnchorPane();
                    teacherTab.setContent(newContent);
    
                    // Riprova con il contenuto appena inizializzato
                    AnchorPane teacherContent = (AnchorPane) teacherTab.getContent();
                    teacherContent.getChildren().clear();
                    teacherContent.getChildren().add(teacherOverview);
    
                    // Imposta gli anchor per il layout
                    AnchorPane.setTopAnchor(teacherOverview, 0.0);
                    AnchorPane.setBottomAnchor(teacherOverview, 0.0);
                    AnchorPane.setLeftAnchor(teacherOverview, 0.0);
                    AnchorPane.setRightAnchor(teacherOverview, 0.0);
    
                    // Dai accesso al controller alla main app
                    TeacherOverviewController controller = loader.getController();
                    controller.setMainApp(this);
                }
            } else {
                System.out.println("Non ci sono abbastanza schede nel TabPane per accedere all'indice 1!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCourseOverview() {
        try {
            // Carica la vista TeacherOverview.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CourseOverview.fxml"));
            AnchorPane courseOverview = loader.load();
    
            // Accedi al nodo centrale del rootLayout (AnchorPane)
            AnchorPane centerPane = (AnchorPane) rootLayout.getCenter();
    
            // Trova il TabPane nel centerPane
            TabPane tabPane = null;
            for (Node node : centerPane.getChildren()) {
                if (node instanceof TabPane) {
                    tabPane = (TabPane) node;
                    break;
                }
            }
    
            if (tabPane == null) {
                System.out.println("TabPane non trovato nel centro del layout!");
                return;
            }
    
            // Debug: stampa il numero di schede nel TabPane
            ObservableList<Tab> tabs = tabPane.getTabs();
            System.out.println("Numero totale di schede nel TabPane: " + tabs.size());
    
            for (int i = 0; i < tabs.size(); i++) {
                System.out.println("Scheda " + i + ": " + tabs.get(i).getText());
            }
    
            // Accedi alla scheda degli insegnanti
            if (tabs.size() > 1) { // Controlla che ci siano abbastanza schede
                Tab corsitab = tabs.get(2); // Scheda "Insegnanti"
                System.out.println("Scheda selezionata: " + corsitab.getText());
    
                if (corsitab.getContent() == null) {
                    System.out.println("Il contenuto della scheda è null, lo inizializzo come AnchorPane.");
                    AnchorPane newContent = new AnchorPane();
                    corsitab.setContent(newContent);
                }
    
                if (corsitab.getContent() instanceof AnchorPane) {
                    AnchorPane courseContent = (AnchorPane) corsitab.getContent();
    
                    // Aggiungi la vista degli insegnanti al contenuto della scheda
                    courseContent.getChildren().clear();
                    courseContent.getChildren().add(courseOverview);
    
                    // Imposta gli anchor per il layout
                    AnchorPane.setTopAnchor(courseOverview, 0.0);
                    AnchorPane.setBottomAnchor(courseOverview, 0.0);
                    AnchorPane.setLeftAnchor(courseOverview, 0.0);
                    AnchorPane.setRightAnchor(courseOverview, 0.0);
    
                    // Dai accesso al controller alla main app
                    CourseOverviewController controller = loader.getController();
                    controller.setMainApp(this);
                } else {
                    System.out.println("Il contenuto della scheda non è un AnchorPane, lo sostituisco.");
                    AnchorPane newContent = new AnchorPane();
                    corsitab.setContent(newContent);
    
                    // Riprova con il contenuto appena inizializzato
                    AnchorPane courseContent = (AnchorPane) corsitab.getContent();
                    courseContent.getChildren().clear();
                    courseContent.getChildren().add(courseOverview);
    
                    // Imposta gli anchor per il layout
                    AnchorPane.setTopAnchor(courseOverview, 0.0);
                    AnchorPane.setBottomAnchor(courseOverview, 0.0);
                    AnchorPane.setLeftAnchor(courseOverview, 0.0);
                    AnchorPane.setRightAnchor(courseOverview, 0.0);
    
                    // Dai accesso al controller alla main app
                    CourseOverviewController controller = loader.getController();
                    controller.setMainApp(this);
                }
            } else {
                System.out.println("Non ci sono abbastanza schede nel TabPane per accedere all'indice 1!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    
    /**
 * Opens a dialog to edit details for the specified person. If the user
 * clicks OK, the changes are saved into the provided person object and true
 * is returned.
 * 
 * @param student the person object to be edited
 * @return true if the user clicked OK, false otherwise.
 */
public boolean showStudentEditDialog(Student studente) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/StudentEditDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Student");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller.
        StudentEditDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setStudent(studente);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

    /**
 * 
 * @param teacher the person object to be edited
 * @return true if the user clicked OK, false otherwise.
 */

public boolean showTeacherEditDialog(Teacher teacher) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/TeacherEditDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Teacher");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller.
        TeacherEditDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setTeacher(teacher);
        controller.setMainApp(this);


        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

   /**
 * 
 * @param course the person object to be edited
 * @return true if the user clicked OK, false otherwise.
 */

 public boolean showCourseEditDialog(Course course) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/CourseEditDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Course");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller.
        CourseEditDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCourse(course);

        controller.setTeachers(getTeacherData());

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
/*********************************************************************************************************************************************************************** */
/*public boolean showStudentEditAddDialog(Student studente) {
    try {
        // Load the FXML file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/StudentEditAddDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Student Course");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the student into the controller.
        StudentEditAddDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        // Pass the main application instance to the controller.
        controller.setMainApp(this);

        // Show the dialog and wait until the user closes it.
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}*/

public boolean showStudentEditAddDialog(Course selectedCourse) {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/StudentEditAddDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Aggiungi Studente al Corso");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        StudentEditAddDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMainApp(this);
        controller.setCorso(selectedCourse); // Passa il corso selezionato

        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}



    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public ObservableList<Student> getStudentData() {
		return studentData;
	}

    public ObservableList<Teacher> getTeacherData() {
		return teacherData;
	}

    public ObservableList<Course> getCourseData() {
		return courseData;
	}

     /**
    * Returnsthe student file preference, i.e. the file that waslast opened.
    * preference can be found, null is returned.
    * The preference is read from the OS specific registry. If no such
    *
    * @return
    */
    public File getStudentFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePathStudent", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
    * Sets the file path of the currently loaded file. The path is persisted in
    * the OS specific registry.
    *
    * @param file the file or null to remove the path
    */
    public void setStudentFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePathStudent", file.getPath());
            // Update the stage title.
            primaryStage.setTitle("Scuola - " + file.getName());
        } else {
                prefs.remove("filePathStudent");
                // Update the stage title.
                primaryStage.setTitle("Scuola");
        }
    }

    public void loadStudentDataFromFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Student> studentList = mapper.readValue(file, new TypeReference<List<Student>>() {});
            studentData.setAll(studentList);
    
            setStudentFilePath(file);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile caricare i dati");
            alert.setContentText("Non è stato possibile caricare i dati dal file:\n" + file.getPath());
    
            alert.showAndWait();
        }
    }
    
    
    public void saveStudentDataToFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
    
            // Temporarily remove circular references
            for (Student student : studentData) {
                for (Course course : student.getCorsi()) {
                    course.getStudentiIscritti().remove(student);
                }
            }
    
            // Convert ObservableList to List before serialization
            List<Student> students = new ArrayList<>(studentData);
            mapper.writeValue(file, students);
    
            // Restore circular references
            for (Student student : studentData) {
                for (Course course : student.getCorsi()) {
                    course.aggiungiStudente(student);
                }
            }
    
            setStudentFilePath(file);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile salvare i dati");
            alert.setContentText("Non è stato possibile salvare i dati nel file:\n" + file.getPath());
    
            alert.showAndWait();
        }
    }

    public File getTeacherFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("teacherFilePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setTeacherFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("teacherFilePath", file.getPath());
            primaryStage.setTitle("Gestionale Scuola By UnTizio - " + file.getName());
        } else {
            prefs.remove("teacherFilePath");
            primaryStage.setTitle("Gestionale Scuola By UnTizio");
        }
    }

    public void loadTeacherDataFromFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Teacher> teacherList = mapper.readValue(file, new TypeReference<List<Teacher>>() {});
            teacherData.clear();
            teacherData.addAll(teacherList);

            setTeacherFilePath(file);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile caricare i dati");
            alert.setContentText("Non è stato possibile caricare i dati dal file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveTeacherDataToFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
    
            // Convert ObservableList to List before serialization
            List<Teacher> teachers = new ArrayList<>(teacherData);
            mapper.writeValue(file, teachers);
    
            setTeacherFilePath(file);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile salvare i dati");
            alert.setContentText("Non è stato possibile salvare i dati nel file:\n" + file.getPath());
    
            alert.showAndWait();
        }
    }

    public File getCourseFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("courseFilePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setCourseFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("courseFilePath", file.getPath());
            primaryStage.setTitle("Gestionale Scuola By UnTizio - " + file.getName());
        } else {
            prefs.remove("courseFilePath");
        }
    }

    public void loadCourseDataFromFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Course> courseList = mapper.readValue(file, new TypeReference<List<Course>>() {});
            courseData.setAll(courseList);
    
            // Rebuild references between courses and teachers
            for (Course course : courseList) {
                int teacherId = course.getInsegnanteId();
                Teacher teacher = teacherData.stream()
                                             .filter(t -> t.getId() == teacherId)
                                             .findFirst()
                                             .orElse(null);
                course.setInsegnante(teacher);
                if (teacher != null) {
                    teacher.aggiungiCorso(course);
                }
    
                // Rebuild references between courses and students
                List<Student> students = new ArrayList<>();
                for (int studentId : course.getStudenteId()) {
                    Student student = studentData.stream()
                                                 .filter(s -> s.getId() == studentId)
                                                 .findFirst()
                                                 .orElse(null);
                    if (student != null) {
                        students.add(student);
                        student.aggiungiCorso(course);
                    }
                }
                course.setStudentiIscritti(students);
            }
    
            setCourseFilePath(file);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile caricare i dati");
            alert.setContentText("Non è stato possibile caricare i dati dal file:\n" + file.getPath());
    
            alert.showAndWait();
        }
    }

    public void saveCourseDataToFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
    
            // Convert ObservableList to List before serialization
            List<Course> courses = new ArrayList<>(courseData);
            mapper.writeValue(file, courses);
    
            setCourseFilePath(file);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile salvare i dati");
            alert.setContentText("Non è stato possibile salvare i dati nel file:\n" + file.getPath());
    
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}