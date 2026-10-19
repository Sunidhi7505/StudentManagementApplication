package student.example.studentManagement.exception;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import student.example.studentManagement.dto.ExceptionResponseDto;
import student.example.studentManagement.dto.ResponseDto;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceAlreadyExistException(ResourceAlreadyExistException e , HttpServletRequest request){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                LocalDateTime.now() ,
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                request.getRequestURI(),
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDto);
    }

    //This ResourceNotFoundException is my own custom Exception , this is not provided by java that's why i have created
    // ResourceNotFoundException class
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException e , HttpServletRequest request){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                LocalDateTime.now() ,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                request.getRequestURI(),
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("This is Null pointer Exception");
    }

    //Optional<Student>studentRes = repository.findByIdAndIsDeletedFalse(id)
    //ResponseDto response = entityToDto(studentRes.get());
    // above two line were present in findById of service class
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No such element exist");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
