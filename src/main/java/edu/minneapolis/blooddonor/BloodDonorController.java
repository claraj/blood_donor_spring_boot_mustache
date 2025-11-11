package edu.minneapolis.blooddonor;

import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class BloodDonorController {

    @GetMapping("/")
    public String showDonorForm(Model model) {
        model.addAttribute("donor", new Donor());
        return "donor_form";
    }

    @PostMapping("/submitDonorForm")
//    public String processDonorForm(Model model, @RequestParam("age") String ageString, @RequestParam("weight") String weightString) {
    public String processDonorForm(@Valid Donor donor, BindingResult bindingResult, Model model) {


        System.out.println("DONOR " + donor);

        if (bindingResult.hasErrors()) {

            Map<String, String> validationErrorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                if (errorMessage != null && errorMessage.startsWith("Failed to convert property value of type 'java.lang.String' to required type 'java.lang.Integer'")) {
                    errorMessage = "Only enter whole numbers";
                }
                validationErrorMap.put(fieldName, errorMessage);
            }

            System.out.print(validationErrorMap);  // {weight=Weight must be less than 600,age='Age must be positive whole number'}
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            model.addAttribute("validationErrorMap", validationErrorMap);
            System.out.println(bindingResult.getAllErrors());
            return "donor_form";  // automatically receives model
        }

        else {
            String resultMessage;
            List<String> ineligibleReasons = new ArrayList<>();

            if (donor.getAge() < 17) {
                ineligibleReasons.add("Age must be greater than 17");
            }

            if (donor.getWeight() < 110) {
                ineligibleReasons.add("Weight must be greater than 110 pounds");
            }

            if (ineligibleReasons.isEmpty()) {
                resultMessage = "You are eligible to donate blood";
            } else {
                resultMessage = "You are not eligible to donate blood";
            }

            model.addAttribute("donor", donor);
            model.addAttribute("ineligibleReasons", ineligibleReasons);
            model.addAttribute("resultMessage", resultMessage);

            return "results";
        }
    }
}


