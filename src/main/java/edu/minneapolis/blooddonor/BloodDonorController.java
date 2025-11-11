package edu.minneapolis.blooddonor;

import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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



    @PostMapping("/submitBloodForm")
//    public String processDonorForm(Model model, @RequestParam("age") String ageString, @RequestParam("weight") String weightString) {
    public String processDonorForm(@Valid Donor donor,
                                   BindingResult bindingResult,
                                   Model model
                                   ) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());

            System.out.println("");

            List<String> validationErrors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                validationErrors.add(error.getDefaultMessage());
            }

            model.addAttribute("validationErrors", validationErrors);
            return "donor_form";  // automatically gets model ?
        }

        else {
            String resultMessage = "";
            System.out.println(donor);

            if (donor.getAge() >= 17 && donor.getWeight() >= 110) {
                resultMessage = "You are eligible to donate blood";
            } else {
                resultMessage = "You are not eligible to donate blood";
            }

            model.addAttribute("donor", donor);
            model.addAttribute("resultMessage", resultMessage);

            return "results";
        }
    }
}


