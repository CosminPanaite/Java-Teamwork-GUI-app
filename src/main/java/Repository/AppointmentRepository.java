package Repository;

import Domain.Appointment;

import java.util.List;


public interface AppointmentRepository extends Repository<Appointment,Integer>{
    List<Appointment> sortAppointmentById();

}
