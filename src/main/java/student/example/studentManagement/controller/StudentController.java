package student.example.studentManagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.example.studentManagement.dto.RequestDto;
import student.example.studentManagement.dto.ResponseDto;
import student.example.studentManagement.entity.Student;
import student.example.studentManagement.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createStudent(@RequestBody RequestDto studentReq){
        ResponseDto studentRes = studentService.createStudent(studentReq);

        return ResponseEntity.status(201).body(studentRes);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll(){
        List<Student> studentRes = studentService.getAll();
        if(studentRes.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(studentRes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(@PathVariable Long id){
       Student studentRes  = studentService.findById(id);
       if(studentRes == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       return ResponseEntity.ok(studentRes);
    }

    @GetMapping("/marks")
    public ResponseEntity<List<Student>> findByMarks(@RequestParam int marks){
        List<Student> studentRes = studentService.findByMarks(marks);
        if(studentRes.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(studentRes);
    }

//    @ResponseStatus(HttpStatus.OK)
//    @DeleteMapping("/{id}")
//    public String deleteById(@PathVariable Long id){
//        boolean isIdDeleted = studentService.deleteById(id);
//        if(isIdDeleted) {
//            return "id is deleted succesfully";
//        }
//        return "id is not present";
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        boolean isIdDeleted = studentService.deleteById(id);
        if(isIdDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateById(@PathVariable Long id , @RequestBody Student student){
        Student studentRes = studentService.updateById(id , student);

        if(studentRes == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(studentRes);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> softDeleteById(@PathVariable Long id){
        boolean isDeleted = studentService.softDelete(id);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
