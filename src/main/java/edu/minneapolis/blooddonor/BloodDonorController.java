package edu.minneapolis.blooddonor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BloodDonorController {


    @GetMapping("/")
    public String showDonorForm(Model model) {

//        model.addAttribute("content", "donor_form");

        return "donor_form";
//        return "layout";
    }



    @PostMapping("/submitBloodForm")
    public String processDonorForm(Model model, @RequestParam("age") String ageString, @RequestParam("weight") String weightString) {

        // Assume not eligible unless checks show otherwise
        boolean validData = false;
        String errorMessage = "";
        String resultMessage = "";
//        boolean isError = true;

        try {
            double age = Double.parseDouble(ageString);
            double weight = Double.parseDouble(weightString);

            if (age < 0 || weight < 0) {
                errorMessage = "Age and weight must be positive";
            }
            else {
                validData = true;
            }


            if (age >= 17 && weight >= 110) {
                resultMessage = "You are eligible to donate blood";
//                isError = false;
            } else {
                resultMessage = "You are not eligible to donate blood";
            }

        } catch (NumberFormatException e) {
            errorMessage = "Age and weight must be numbers";
        }

        model.addAttribute("age", ageString);
        model.addAttribute("weight", weightString);
        model.addAttribute("validData", validData);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("resultMessage", resultMessage);
//        model.addAttribute("isError", isError);

        return "results";
    }
}


