package edu.minneapolis.blooddonor;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class BloodDonorController {

    @GetMapping("/")
    public String showDonorForm(Model model) {
        model.addAttribute("donor", new Donor());
        return "donor_form";
    }

    @PostMapping("/submitDonorForm")
    public String processDonorForm(@Valid Donor donor, BindingResult bindingResult, Model model) {

        // Are there validation errors?
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrorMap = ErrorUtil.createErrorMap(bindingResult);
            model.addAttribute("validationErrorMap", validationErrorMap);
            return "donor_form";  // automatically receives model
        }

        // No validation errors
        else {

            String resultMessage;
            List<String> ineligibleReasons = new ArrayList<>();

            // Check criteria
            if (donor.getAge() < 16) {
                ineligibleReasons.add("Age must be 16 or older");
            }

            if (donor.getWeight() < 110) {
                ineligibleReasons.add("Weight must be 110 pounds or more");
            }

            // Decide if user is eligible by checking the size of the list of ineligible reasons.
            // if there are no ineligible reasons, the user must be eligible.
            if (ineligibleReasons.isEmpty()) {
                resultMessage = "You may be eligible to donate blood";
            } else {
                resultMessage = "You are not eligible to donate blood";
                model.addAttribute("ineligibleReasons", ineligibleReasons);
            }

            // the donor information is part of the model
            model.addAttribute("resultMessage", resultMessage);
            return "results";
        }
    }
}


