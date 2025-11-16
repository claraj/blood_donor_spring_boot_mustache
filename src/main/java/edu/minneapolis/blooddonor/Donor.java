package edu.minneapolis.blooddonor;
import jakarta.validation.constraints.*;

import java.util.Objects;

public class Donor {

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    @Size(min=2, max=40, message="Name must be at least 2 characters")
    private String firstName;

    @Min(value = 0, message = "Age must be positive number")
    @Max(value = 130, message = "Age must be less than 130")
    private int age;

    @Min(value = 0, message = "Weight must be a positive number")
    @Max(value = 600, message = "Weight must be less than 600")
    private int weight;

    public Donor() {
        // no-argument constructor is required
    }

    public Donor(String firstName, int age, int weight) {
        this.firstName = firstName;
        this.age = age;
        this.weight = weight;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "age=" + age +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!Objects.equals(this.age, ((Donor) o).getAge())) return false;
        if (!Objects.equals(this.weight, ((Donor) o).getWeight())) return false;
        return true;

    }
}

