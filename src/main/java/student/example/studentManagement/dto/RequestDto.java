package student.example.studentManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RequestDto {
    @NotBlank(message = "name can't be blank")
    private String name;

    @Email(message = "email must be in valid format")
    private String email;

    @Min(value = 0 , message="marks must be greater than 0")
    @Max(value = 100 , message = "marks can't be greater than 100")
    private int marks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
