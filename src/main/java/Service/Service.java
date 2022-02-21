package Service;

import Domain.Appointment;
import Domain.Patient;
import Exceptions.RepoExceptions;
import Repository.AppointmentRepository;
import Repository.PatientRepository;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;

    public Service(PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;

    }

    public void addPatient(Integer ID, String FirstName, String LastName, Integer Age, String Problem) {
        Patient patient = new Patient(ID, FirstName, LastName, Age, Problem);
        patientRepository.add(patient);
    }

    public void addAppointment(Integer ID, Integer PatientId, String Date) {
        Appointment appointment = new Appointment(ID, PatientId, Date);
        appointmentRepository.add(appointment);
    }

    public List<Patient> getPatientList() {
        return new ArrayList<>(patientRepository.getAll());
    }

    public List<Appointment> getAppointmentList() {
        return new ArrayList<>(appointmentRepository.getAll());
    }

    public void updateAppointemnt(Integer id, Appointment appointment) {
        appointmentRepository.update(id, appointment);
    }


    public void deleteAppointment(Appointment appointment) throws ServiceException {

        try {
            appointmentRepository.delete(appointment);
        } catch (RepoExceptions exceptions) {
            throw new ServiceException("Error deleting" + exceptions);

        }
    }


    public List<Appointment> sortById() {
        return appointmentRepository.sortAppointmentById();
    }


    public void updatePatient(Integer id, Patient patient) {
        patientRepository.update(id, patient);
    }


    public void deletePatient(Patient patient) throws ServiceException {

        try {
            patientRepository.delete(patient);
        } catch (RepoExceptions exceptions) {
            throw new ServiceException("Error deleting" + exceptions);

        }
    }


    public List<Patient> sortPatientById() {
        return patientRepository.sortPatientById();
    }

    public List<Patient> getPatientById(Integer idSearch) {
        return patientRepository.filterById(idSearch);
    }

    public List<Patient> findPatientsByName(String firstName, String lastName) {
        return getPatientList()
                .stream()
                .filter(patient -> (patient.getFirstName().toLowerCase()).contains(firstName.toLowerCase()) &&
                        (patient.getLastName().toLowerCase()).contains(lastName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Appointment> findAppointmentsByID(Integer id) {
        return getAppointmentList()
                .stream()
                .filter(appointment -> appointment.getId().equals(id))
                .collect(Collectors.toList());
    }

    public List<Patient> filterPatientsOfACertainAgeWithGivenAffection(String affection, Integer age) {
        return getPatientList()
                .stream()
                .filter(patient -> patient.getProblem().equalsIgnoreCase(affection) && patient.getAge().equals(age))
                .collect(Collectors.toList());
    }
}
