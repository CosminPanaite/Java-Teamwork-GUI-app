package Repository;



import Domain.Appointment;
import Exceptions.RepoExceptions;

import java.io.*;
import java.util.Map;

public  class AppointmentRepositorySerialization extends AbstractRepository<Appointment, Integer> {
    private String filename;

    public AppointmentRepositorySerialization(String filename) {
        this.filename = filename;
        readFromFile();
    }

    private void writeToFile() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            objectOutputStream.writeObject(elem);
        } catch (IOException r) {
            throw new RepoExceptions("message " + r);
        }
    }

    private void readFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            elem = (Map<Integer, Appointment>) in.readObject();
        } catch (IOException | ClassNotFoundException err) {
            if (!(err instanceof EOFException)) {
                throw new RepoExceptions("Error reading from file: " + err);


            }
        }

    }

    @Override
    public Appointment add(Appointment obj) {
        try {
            super.add(obj);
            writeToFile();
        } catch (RuntimeException e) {
            throw new RepoExceptions("Object wasn't added" + e + " " + obj);
        }
        return obj;
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
