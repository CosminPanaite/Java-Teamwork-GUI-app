package Repository;


import Domain.Appointment;
import Exceptions.RepoExceptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AppointmentRepositoryFile extends AppointmentInMemoryRepository {

    private String filename;

    public AppointmentRepositoryFile(String filename) {
        this.filename = filename;
        readFromFile();
    }

    private void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] el = line.split(";");
                if (el.length != 3) {
                    System.err.println("Not a valid number of atributes " + line);
                    continue;
                }
                try {
                    int Id = Integer.parseInt(el[0]);
                    int PatientId = Integer.parseInt(el[1]);
                    Appointment appointment = new Appointment(Id, PatientId, el[2]);
                    super.add(appointment);
                } catch (NumberFormatException n) {
                    System.err.println("The ID is not a valid number " + el[0]);
                }
            }
        } catch (IOException ex) {
            throw new RepoExceptions("Error reading" + ex);
        }
    }

    @Override
    public Appointment add(Appointment obj) {
        try {
            super.add(obj);
            writeToFile();
        } catch (RuntimeException e) {
            throw new RepoExceptions("Object was not added" + e + " " + obj);
        }
        return obj;
    }

    private void writeToFile() {
        try (PrintWriter pw = new PrintWriter(filename)) {
            for (Appointment el : findAll()) {
                String line = el.getId() + ";" + el.getPatientIdentifier() + ";" + el.getDateAppointemnt();
                pw.println(line);
            }
        } catch (IOException ex) {
            throw new RepoExceptions("Error writing" + ex);
        }
    }

    @Override
    public void delete(Appointment obj) {
        try {
            super.delete(obj);
            writeToFile();
        } catch (RuntimeException ex) {
            throw new RepoExceptions("Object was not deleted" + ex + " " + obj);
        }
    }

    @Override
    public void update(Integer id, Appointment el) {
        try {
            super.update(id, el);
            writeToFile();
        } catch (RuntimeException ex) {
            throw new RepoExceptions("Object was not updated" + ex + " " + el);
        }
    }
}
