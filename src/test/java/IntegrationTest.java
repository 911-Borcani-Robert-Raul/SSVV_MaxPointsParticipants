import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.Validator;

public class IntegrationTest {
    Validator<Student> studentValidator;
    Validator<Tema> temaValidator;
    Validator<Nota> notaValidator;
    StudentXMLRepository studentRepo;
    TemaXMLRepository temaRepo;
    NotaXMLRepository notaRepo;
    Service service;

    @Before
    public void init() {
        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        notaValidator = new NotaValidator();

        studentRepo = new StudentXMLRepository(studentValidator, "studenti.xml");
        for (Student s : studentRepo.findAll()) {
            studentRepo.delete(s.getID());
        }
        temaRepo = new TemaXMLRepository(temaValidator, "teme.xml");
        notaRepo = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(studentRepo, temaRepo, notaRepo);

        service.saveStudent("99", "Jeffrey Bezos", 111);
        service.saveTema("99", "o descriere interesanta", 4, 3);
    }

    @Test
    public void testAddStudent() {
        String studentId = "0";
        String studentName = "a";
        int group = 111;

        int result = service.saveStudent(studentId, studentName, group);

        Assert.assertEquals(0, result);
    }

    @Test
    public void testAddAssignment() {
        int result = service.saveTema("1", "descriere", 3, 2);

        Assert.assertEquals(0, result);
    }

    @Test
    public void testAddGrade() {
        int result = service.saveNota("99", "99", 6, 3, "could be better");

        Assert.assertEquals(0, result);
    }

    @Test
    public void testAll() {
        int resStudent = service.saveStudent("199", "a", 111);
        int resAssignment = service.saveTema("299", "o tema", 2, 1);
        int resGrade = service.saveNota("199", "299", 7, 3, "feedback");

        Assert.assertEquals(0, resStudent);
        Assert.assertEquals(0, resAssignment);
        Assert.assertEquals(0, resGrade);
    }
}
