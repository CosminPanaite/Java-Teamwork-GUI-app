import Controller.Controller;
import Service.*;

import Repository.AppointmentRepository;
import Repository.AppointmentRepositoryFile;
import Repository.PatientRepository;
import Repository.PatientRepositoryFile;
import Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MainFX extends Application {

    static public String patientFileName;
    static public String appointmentFileName;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Appointments for patients");
            primaryStage.show();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app " + exception);
            alert.showAndWait();
            System.err.println(exception);
        }

    }

    static Service getService() throws ServiceException {
        readFile();
        AppointmentRepository appointmentRepository = new AppointmentRepositoryFile(appointmentFileName);
        PatientRepository patientRepository = new PatientRepositoryFile(patientFileName);
        Service service = new Service(patientRepository, appointmentRepository);
        return service;
    }

    //--------------------read from file----------------------------
    private static void readFile() throws ServiceException {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("app.properties"));
            appointmentFileName = properties.getProperty("AppointmentFile");
            if (appointmentFileName == null) {
                appointmentFileName = "appointments.txt";
                System.err.println("Appointments File not found. Using default" + appointmentFileName);
            }
            patientFileName = properties.getProperty("PatientFile");
            if (patientFileName == null) {
                patientFileName = "patients.txt";
                System.err.println("Patients File not found. Using default" + patientFileName);
            }
        } catch (IOException exception) {
            throw new ServiceException("Error reading the configuration file" + exception);
        }


    }
}
