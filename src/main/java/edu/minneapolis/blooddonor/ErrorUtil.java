package edu.minneapolis.blooddonor;


import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorUtil {

    public static Map<String, String> createErrorMap(BindingResult bindingResult) {
        System.out.println("\n******************************* Validation Map ************************");
        Map<String, String> validationErrorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            System.out.println("fieldError " + error);
            String fieldName = error.getField();

            String errorMessage = error.getDefaultMessage();
            if (error.getCodes() != null && List.of(error.getCodes()).contains("typeMismatch.int")) {
                errorMessage = "Enter only integer values";
            }
            validationErrorMap.put(fieldName, errorMessage);
        }
        System.out.println( validationErrorMap);
        System.out.println("\n");
        return validationErrorMap;
    }
}

