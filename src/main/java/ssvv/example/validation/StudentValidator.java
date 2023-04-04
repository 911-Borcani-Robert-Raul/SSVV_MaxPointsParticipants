package ssvv.example.validation;
import ssvv.example.domain.Student;

public class StudentValidator implements Validator<Student> {
    public void validate(Student student) throws ValidationException {
        if (student.getID() == null || student.getID().equals("")) {
            throw new ValidationException("ID invalid! \n");
        }
        try{
            Integer.parseInt(student.getID());
        }
        catch (Exception e){
            throw new ValidationException("The ID should be an integer! \n");
        }
        if(Integer.parseInt(student.getID()) > Integer.MAX_VALUE || Integer.parseInt(student.getID()) < 0){
            throw new ValidationException("The ID should be an integer! \n");
        }
        if (student.getNume() == null || student.getNume().equals("")) {
            throw new ValidationException("Nume invalid! \n");
        }
        if (student.getGrupa() <= 110 || student.getGrupa() >= 938) {
            throw new ValidationException("Grupa invalida! \n");
        }

    }
}

