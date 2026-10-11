package student.example.studentManagement.service;

import org.springframework.stereotype.Service;
import student.example.studentManagement.entity.Student;
import student.example.studentManagement.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository repository;
    public StudentService(StudentRepository repository){
        this.repository = repository;
    }

    //create
    public Student createStudent(Student student){
       return repository.save(student);
    }

    //findAll
    public List<Student> getAll(){
        return repository.findAll();
    }

    //find by id
    public Student findById(Long id){
        Optional<Student> studentRes = repository.findById(id);
        if(studentRes.isEmpty()){
            return null;
        }
        return studentRes.get();
    }

    //find by marks
    public List<Student> findByMarks(int marks){
         return repository.findByMarks(marks);
    }

    //deleteById
    public boolean deleteById(Long id){
         boolean isIdPresent = repository.existsById(id);
         if(isIdPresent){
             repository.deleteById(id);
             return true;
         }
         return false;
    }

    public Student updateById(Long id , Student student){
        Optional<Student> existingStudent = repository.findById(id);
        if(existingStudent.isEmpty()){
            return null;
        }
        Student studentToSave = existingStudent.get();

        studentToSave.setName(student.getName());
        studentToSave.setEmail(student.getEmail());
        studentToSave.setMarks(student.getMarks());

        return repository.save(studentToSave);
    }

}
