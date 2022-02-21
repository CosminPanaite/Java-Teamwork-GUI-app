package Repository;



import Domain.Patient;
import Exceptions.RepoExceptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public  class PatientRepositoryFile extends PatientInMemoryRepository {
    private String filename;
    public PatientRepositoryFile(String filename){
        this.filename=filename;
        readFromFile();
    }

    private void readFromFile(){
        try(BufferedReader reader=new BufferedReader(new FileReader(filename))){
            String line;
            while((line=reader.readLine())!=null){
                String[] el=line.split(";");
                if(el.length!=5){
                    System.err.println("Not a valid number of atributes "+line);
                    continue;
                }
                try{
                    int Id=Integer.parseInt(el[0]);
                    int age=Integer.parseInt(el[3]);

                    Patient appointment=new Patient(Id,el[1],el[2],age,el[4]);
                    super.add(appointment);
                }
                catch(NumberFormatException n){
                    System.err.println("The ID is not a valid number "+el[0]);
                }
            }
        }
        catch(IOException ex){
            throw new RepoExceptions("Error reading"+ex);
        }
    }

    @Override
    public Patient add(Patient obj) {
        try{
            super.add(obj);
            writeToFile();
        }
        catch(RuntimeException e){
            throw new RepoExceptions("Object was not added" + e + " " + obj);
        }
        return obj;
    }

    private void writeToFile() {
        try(PrintWriter pw = new PrintWriter(filename)) {
            for(Patient el : findAll()) {
                String line = el.getId() + ";" + el.getFirstName() + ";" + el.getLastName() +
                        ";" + el.getAge() + ";" + el.getProblem() ;
                pw.println(line);
            }
        }
        catch(IOException ex) {
            throw new RepoExceptions("Error writing" + ex);
        }
    }

    @Override
    public void delete(Patient elem) {
        try{

            super.delete(elem);
            writeToFile();
        }
        catch(RuntimeException ex){
            throw new RepoExceptions("Object was not deleted"+ex+" "+elem);
        }
    }


    @Override
    public void update( Integer id,Patient obj){
        try{
            super.update(id, obj);
            writeToFile();
        }
        catch(RuntimeException ex){
            throw new RepoExceptions("Object was not updated" + ex + " " + obj);
        }
    }

    @Override
    public Collection<Patient> getAll() {
        return super.getAll();
    }
}
