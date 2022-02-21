package Controller;

import Service.ServiceException;
import Domain.Appointment;
import Domain.Patient;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private Button sortByIdPatientBtn;
    @FXML
    private TextField tfFilterAge;
    @FXML
    private TextField tfFilterProblem;
    @FXML
    private TextField tfSearchIDApp;
    @FXML
    private Label idSearch_tf;
    @FXML
    private TextField tfSearchLastName;
    @FXML
    private TextField tfSearchFirstName;
    @FXML
    TableView<Appointment> appointmentTableView;
    @FXML
    TableView<Patient> patientTableView;
    @FXML
    private TextField idSeach_tf;
    @FXML
    private TextField id_tf, firstName_tf, lastName_tf, age_tf, problem_tf;
    @FXML
    private TextField idAp_tf, idPa_tf, date_tf;

    private final ObservableList<Patient> patientObservableList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
    private Service service;

    public Controller() {

    }

    @FXML
    public void initialize() {
        patientTableView.setItems(patientObservableList);
        appointmentTableView.setItems(appointmentObservableList);

        patientTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> showPatient(newItem));
        appointmentTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> showAppointment(newItem));
    }

    public void setService(Service service) {
        this.service = service;
        patientObservableList.addAll(service.getPatientList());
        appointmentObservableList.addAll(service.getAppointmentList());
    }

    @FXML
    public void addPatientButton() {
        Integer ID = Integer.parseInt(id_tf.getText());
        String FirstName = firstName_tf.getText();
        String LastName = lastName_tf.getText();
        Integer Age = Integer.parseInt(age_tf.getText());
        String Problem = problem_tf.getText();
        if (ID.equals("") || FirstName.equals("") || LastName.equals("") || Age.equals("") || Problem.equals("")) {
            showErrorMessage("All fields must be completed");

        }

        try {
            service.addPatient(ID, FirstName, LastName, Age, Problem);
            patientObservableList.setAll(service.getPatientList());
        } catch (Exception exception) {
            showNotification(exception.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    public void addAppointmentButton() {
        Integer ID = Integer.parseInt(idAp_tf.getText());
        Integer PatientId = Integer.parseInt(idPa_tf.getText());
        String Date = date_tf.getText();
        try {
            service.addAppointment(ID, PatientId, Date);
            appointmentObservableList.setAll(service.getAppointmentList());
        } catch (Exception exception) {
            showNotification(exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void showPatient(Patient p) {
        if (p == null)
            clearPatientFields();
        else {
            id_tf.setText("" + p.getId());
            firstName_tf.setText("" + p.getFirstName());
            lastName_tf.setText("" + p.getLastName());
            age_tf.setText("" + p.getAge());
            problem_tf.setText("" + p.getProblem());

        }

    }

    @FXML
    private void showAppointment(Appointment a) {
        if (a == null)
            clearAppointmentFields();
        else {
            idAp_tf.setText("" + a.getId());
            idPa_tf.setText("" + a.getPatientIdentifier());
            date_tf.setText("" + a.getDateAppointemnt());

        }
    }


    @FXML
    private void updateAppointmentButton() throws ServiceException {
        int selectedApp = appointmentTableView.getSelectionModel().getSelectedIndex();

        if (selectedApp < 0) {
            showNotification("You must select an appointment first! ", Alert.AlertType.ERROR);
        } else {
            Integer newId = Integer.parseInt(idAp_tf.getText());
            Integer newPatientId = Integer.parseInt(idPa_tf.getText());
            String newDate = date_tf.getText();

            Appointment appointment = new Appointment(newId, newPatientId, newDate);
            service.updateAppointemnt(appointment.getId(), appointment);
            clearAppointmentFields();
            ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
            appointmentObservableList.setAll(service.getAppointmentList());
            appointmentTableView.setItems(appointmentObservableList);
            showNotification("Appointment successfully modified! ", Alert.AlertType.INFORMATION);


        }
    }

    @FXML
    private void updatePatientButton() throws ServiceException {
        int selectedApp = patientTableView.getSelectionModel().getSelectedIndex();

        if (selectedApp < 0) {
            showNotification("You must select an patient first! ", Alert.AlertType.ERROR);
        } else {
            Integer newId = Integer.parseInt(id_tf.getText());
            String newFirstName = firstName_tf.getText();
            String newLastName = lastName_tf.getText();
            Integer newAge = Integer.parseInt(age_tf.getText());
            String newProblem = problem_tf.getText();

            Patient patient = new Patient(newId, newFirstName, newLastName, newAge, newProblem);
            service.updatePatient(patient.getId(), patient);
            clearAppointmentFields();
            ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
            patientObservableList.setAll(service.getPatientList());
            patientTableView.setItems(patientObservableList);
            showNotification("Patient successfully modified! ", Alert.AlertType.INFORMATION);


        }
    }

    @FXML
    private void deleteAppointmentButton() {
        int selectedApp = appointmentTableView.getSelectionModel().getSelectedIndex();
        if (selectedApp < 0) {
            showNotification("You must select a appointment", Alert.AlertType.ERROR);
        }
        Appointment app = appointmentTableView.getItems().get(selectedApp);

        try {
            service.deleteAppointment(app);
            appointmentTableView.getItems().remove(app);
            showNotification("Appointment was successfully deleted", Alert.AlertType.INFORMATION);
        } catch (ServiceException ex) {
            showNotification(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deletePatientButton() {
        int selectedApp = patientTableView.getSelectionModel().getSelectedIndex();
        if (selectedApp < 0) {
            showNotification("You must select a patient", Alert.AlertType.ERROR);
        }
        Patient p = patientTableView.getItems().get(selectedApp);

        try {
            service.deletePatient(p);
            patientTableView.getItems().remove(p);
            showNotification("Patient was successfully deleted", Alert.AlertType.INFORMATION);
        } catch (ServiceException ex) {
            showNotification(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void searchPacientHandler(ActionEvent actionEvent) {
        List<Patient> result = service.findPatientsByName(tfSearchFirstName.getText(), tfSearchLastName.getText());
        if (result.isEmpty()) {
            showNotification("No patient with these name!", Alert.AlertType.INFORMATION);
        }
        patientTableView.getItems().clear();
        patientTableView.getItems().addAll(result);
    }

    @FXML
    public void sortAppointmentById() {
        List<Appointment> appointmentList = service.sortById();
        appointmentTableView.getItems().clear();
        appointmentTableView.getItems().addAll(appointmentList);
    }

    @FXML
    public void sortPatientById() {
        List<Patient> patients = service.sortPatientById();
        patientTableView.getItems().clear();
        patientTableView.getItems().addAll(patients);
    }

    @FXML
    private void clearPatientFields() {
        id_tf.setText("");
        firstName_tf.setText("");
        lastName_tf.setText("");
        age_tf.setText("");
        problem_tf.setText("");
    }

    @FXML
    private void clearAppointmentFields() {
        idAp_tf.setText("");
        idPa_tf.setText("");
        date_tf.setText("");
    }


    void showErrorMessage(String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message");
        message.setContentText(text);
        message.showAndWait();
    }

    private void showNotification(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void searchAppointmentsByID(ActionEvent actionEvent) {
        int id;
        List<Appointment> result = new ArrayList<>();
        try {
            id = Integer.parseInt(tfSearchIDApp.getText());
            result = service.findAppointmentsByID(id);

        } catch (IllegalArgumentException e) {
            System.out.println("Introduce a numeric value");
        }
        if (result.isEmpty()) {
            showNotification("No patient with these name!", Alert.AlertType.INFORMATION);
        }
        appointmentTableView.getItems().clear();
        appointmentTableView.getItems().addAll(result);
    }

    public void filterByAgeAndAffection(ActionEvent actionEvent) {
        int age;
        String problem;
        List<Patient> result = new ArrayList<>();
        try {
            age = Integer.parseInt(tfFilterAge.getText());
            problem = tfFilterProblem.getText();
            result = service.filterPatientsOfACertainAgeWithGivenAffection(problem, age);

        } catch (IllegalArgumentException e) {
            System.out.println("Introduce a numeric value");
        }
        if (result.isEmpty()) {
            showNotification("No patient with neither this age nor this problem!", Alert.AlertType.INFORMATION);
        }
        patientTableView.getItems().clear();
        patientTableView.getItems().addAll(result);
    }
}




