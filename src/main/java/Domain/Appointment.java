package Domain;
import java.io.Serializable;
import java.util.Objects;

public class Appointment implements Identifiable<Integer> , Serializable {
    private Integer id;
    private Integer patientIdentifier;
    private String dateAppointemnt;
    public Appointment(){

    }

    public Appointment(Integer id, Integer patientId, String dateAppointemnt) {
        this.id = id;
        this.patientIdentifier = patientId;
        this.dateAppointemnt = dateAppointemnt;
    }



    public Integer getPatientIdentifier() {
        return patientIdentifier;
    }

    public String getDateAppointemnt() {
        return dateAppointemnt;
    }


    public void setPatientIdentifier(Integer patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public void setDateAppointemnt(String dateAppointemnt) {
        this.dateAppointemnt = dateAppointemnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id) && Objects.equals(patientIdentifier, that.patientIdentifier) && Objects.equals(dateAppointemnt, that.dateAppointemnt);
    }

    @Override
    public String toString() {
        return "The appointment has the ID "+ id+ " and the patient has the ID "+ patientIdentifier + " and the appointemnt is established for "+ dateAppointemnt ;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}
