package edu.minneapolis.blooddonor;
import jakarta.validation.constraints.*;

public class Donor {

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be positive whole number")
    @Max(value = 130, message = "Age must be less than 130")
    private Integer age;

    @NotNull(message = "Weight is required")
    @Min(value = 0, message = "Weight must be a positive whole number")
    @Max(value = 600, message = "Weight must be less than 600")
    private Integer weight;

    public Donor() {
        // no-argument constructor is required
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "age=" + age +
                ", weight=" + weight +
                '}';
    }
}

