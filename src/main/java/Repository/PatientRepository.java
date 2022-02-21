package Repository;

import Domain.Patient;

import java.util.List;

public interface PatientRepository extends Repository<Patient,Integer>{

    List<Patient> sortPatientById();
    List<Patient> filterById(Integer idSearch);

}
