package student.example.studentManagement.service;

import org.springframework.stereotype.Service;
import student.example.studentManagement.dto.RequestDto;
import student.example.studentManagement.dto.ResponseDto;
import student.example.studentManagement.entity.Student;
import student.example.studentManagement.exception.ResourceAlreadyExistException;
import student.example.studentManagement.exception.ResourceNotFoundException;
import student.example.studentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository repository;
    public StudentService(StudentRepository repository){
        this.repository = repository;
    }

    //convert dto to entity
    private Student dtoToEntity(RequestDto studentReq){
        Student student = new Student();
        student.setName(studentReq.getName());
        student.setEmail(studentReq.getEmail());
        student.setMarks(studentReq.getMarks());

        return student;
    }

    //convert entity to dto
    private ResponseDto entityToDto(Student student){
        ResponseDto studentRes = new ResponseDto();
        studentRes.setName(student.getName());
        studentRes.setEmail(student.getEmail());
        studentRes.setMarks(student.getMarks());

        return studentRes;
    }

    //create
    public ResponseDto createStudent(RequestDto studentReq){

        if(repository.existsByEmail(studentReq.getEmail())) {
            throw new ResourceAlreadyExistException(
                    "Student already exists with email: " + studentReq.getEmail()
            );
        }

        Student student = dtoToEntity(studentReq);
        student.setDeleted(false);
        repository.save(student);

        ResponseDto studentRes = entityToDto(student);
        studentRes.setCreateAt(LocalDateTime.now());
        return studentRes;
    }

    //findAll
    public List<ResponseDto> getAll(){
        //select * from student where isdelted=false;
        List<Student> student = repository.findByIsDeletedFalse();
        List<ResponseDto> ans = new ArrayList<>();

        for(int i=0 ; i<student.size() ; i++){
               Student data = student.get(i);
               ResponseDto studentRes = entityToDto(data);
               studentRes.setCreateAt(LocalDateTime.now());
               ans.add(studentRes);
        }
        return ans;
    }

    //find by id
    public ResponseDto findById(Long id){
        //select * from student where id=1 and isdelted=false;
        Student studentRes = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("This id does not exist"));

        ResponseDto response = entityToDto(studentRes);
        response.setCreateAt(LocalDateTime.now());
        return response;
    }

    //find by marks
    public List<ResponseDto> findByMarks(int marks){
        //select* from student where marks = 80 and isdeleted = false;
        List<Student> student = repository.findByMarksAndIsDeletedFalse(marks);

        List<ResponseDto> ans = new ArrayList<>();
        for(int i=0 ; i<student.size() ; i++){
            Student data = student.get(i);
            ResponseDto studentRes = entityToDto(data);
            studentRes.setCreateAt(LocalDateTime.now());
            ans.add(studentRes);
        }
        return ans;
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

    public ResponseDto updateById(Long id , RequestDto studentReq){
        //select * from student where id = 1 and isdleted = false;

        Optional<Student> existingStudent = repository.findByIdAndIsDeletedFalse(id);
        if(existingStudent.isEmpty()){
            return null;
        }
        Student studentToSave = existingStudent.get();

        studentToSave.setName(studentReq.getName());
        studentToSave.setEmail(studentReq.getEmail());
        studentToSave.setMarks(studentReq.getMarks());

        Student updatedStudent = repository.save(studentToSave);
        ResponseDto studentRes = entityToDto(updatedStudent);
        studentRes.setCreateAt(LocalDateTime.now());
        return studentRes;
    }

    public boolean softDelete(Long id) {
        Optional<Student> existingStudent = repository.findByIdAndIsDeletedFalse(id);
        if (existingStudent.isEmpty()) {
            return false;
        }
        Student studentToSave = existingStudent.get();
        studentToSave.setDeleted(true);
        repository.save(studentToSave);
        return true;
    }
}
