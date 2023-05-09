package ssvv.example;

import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Pair;
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

@RunWith(MockitoJUnitRunner.class)
public class TestIntegrationMockito {

    Validator<Student> studentValidator;
    Validator<Tema> temaValidator;
    Validator<Nota> notaValidator;

    @Mock
    private NotaXMLRepository notaXMLRepo;

    @Mock
    private StudentXMLRepository studentXmlRepo;

    @Mock
    private TemaXMLRepository temaXMLRepo;

    private Service service;

    @Before
    public void createInitialState() {
        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        notaValidator = new NotaValidator();

        // Remove the following three lines of code
        // studentXmlRepo = new StudentXMLRepository(studentValidator, "studenti.xml");
        // temaXMLRepo = new TemaXMLRepository(temaValidator, "teme.xml");
        // notaXMLRepo = new NotaXMLRepository(notaValidator, "note.xml");

        // Use the mocked repositories when creating the service object
        service = new Service(studentXmlRepo, temaXMLRepo, notaXMLRepo);
    }

    @Test
    public void testStudent() {
        String studentId = "0";
        String studentName = "a";
        int group = 111;
        Student student = new Student(studentId, studentName, group);

        when(studentXmlRepo.findOne("0")).thenReturn(null);
        when(studentXmlRepo.save(argThat(argument -> argument.getID().equals("0") &&
                argument.getNume().equals("a") &&
                argument.getGrupa() == 111))).thenReturn(student);

        int result = service.saveStudent(studentId, studentName, group);

        Assert.assertEquals(0, result);
    }

    @Test
    public void testAssignment() {
        Tema tema = new Tema("399", "o tema", 2, 1);
        when(temaXMLRepo.save(tema)).thenReturn(tema);

        // Call the mocked methods
        int resAssignment = service.saveTema("399", "o tema", 2, 1);

        Assert.assertEquals(0, resAssignment);

        // Verify that the mocked methods were called
//        verify(service).saveTema("399", "o tema", 2, 1);
    }

    @Test
    public void testGrade() {
        // Set up the mock service to return 0 for all three methods
        Student student = new Student("499", "a", 111);
        Tema tema = new Tema("599", "o tema", 2, 1);
        Nota nota = new Nota(new Pair<>("499", "599"), 4.5, 3, "feedback");
        when(studentXmlRepo.findOne("499")).thenReturn(student);
        when(temaXMLRepo.findOne("599")).thenReturn(tema);
        when(notaXMLRepo.save(argThat(argument -> argument.getID().getObject1().equals("499") &&
                argument.getID().getObject2().equals("599") &&
                argument.getNota() == 4.5 &&
                argument.getSaptamanaPredare() == 3 &&
                argument.getFeedback().equals("feedback")))).thenReturn(nota);


        // Call the mocked methods
        int resGrade = service.saveNota("499", "599", 7, 3, "feedback");

        // Verify the results
        Assert.assertEquals(0, resGrade);
    }
}
