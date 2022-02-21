package Repository;

import Domain.Appointment;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class AppointmentInMemoryRepository extends AbstractRepository<Appointment,Integer> implements AppointmentRepository{


    @Override
    public List<Appointment> sortAppointmentById() {


        List<Appointment>appointmentList= getAll().stream().sorted(Comparator.comparing(Appointment::getId)).collect(Collectors.toList());
        Collections.reverse(appointmentList);
        return appointmentList;
    }
}