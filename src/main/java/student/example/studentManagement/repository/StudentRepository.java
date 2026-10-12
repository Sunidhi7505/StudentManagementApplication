package student.example.studentManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import student.example.studentManagement.entity.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByMarks(int marks);

    List<Student> findByIsDeletedFalse();

    Optional<Student> findByIdAndIsDeletedFalse(Long id);

    List<Student> findByMarksAndIsDeletedFalse(int marks);

}
