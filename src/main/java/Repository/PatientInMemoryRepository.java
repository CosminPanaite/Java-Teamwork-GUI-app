package Repository;

import Domain.Appointment;
import Domain.Patient;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PatientInMemoryRepository extends AbstractRepository<Patient, Integer> implements PatientRepository {
    @Override
    public List<Patient> sortPatientById() {
        List<Patient> patientList = (getAll().stream().sorted(Comparator.comparing(Patient::getId)).collect(Collectors.toList()));
        Collections.reverse(patientList);
        return patientList;
    }

    @Override
    public List<Patient> filterById(Integer idSearch) {

        return getAll().stream().filter(x -> x.getId() == idSearch).collect(Collectors.toList());

    }

}
