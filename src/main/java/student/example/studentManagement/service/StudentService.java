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
        student.setDeleted(false);
       return repository.save(student);
    }

    //findAll
    public List<Student> getAll(){
        //select * from student where isdelted=false;
        return repository.findByIsDeletedFalse();
    }

    //find by id
    public Student findById(Long id){
        //select * from student where id=1 and isdelted=false;
        Optional<Student> studentRes = repository.findByIdAndIsDeletedFalse(id);
        if(studentRes.isEmpty()){
            return null;
        }
        return studentRes.get();
    }

    //find by marks
    public List<Student> findByMarks(int marks){
        //select* from student where marks = 80 and isdeleted = false;
         return repository.findByMarksAndIsDeletedFalse(marks);
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
        //select * from student where id = 1 and isdleted = false;

        Optional<Student> existingStudent = repository.findByIdAndIsDeletedFalse(id);
        if(existingStudent.isEmpty()){
            return null;
        }
        Student studentToSave = existingStudent.get();

        studentToSave.setName(student.getName());
        studentToSave.setEmail(student.getEmail());
        studentToSave.setMarks(student.getMarks());

        return repository.save(studentToSave);
    }

    public boolean softDelete(Long id){
        Optional<Student> existingStudent = repository.findByIdAndIsDeletedFalse(id);
        if(existingStudent.isEmpty()){
            return false;
        }
        Student studentToSave = existingStudent.get();
        studentToSave.setDeleted(true);
        repository.save(studentToSave);
        return true;
    }

}
