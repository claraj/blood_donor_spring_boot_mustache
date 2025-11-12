package edu.minneapolis.blooddonor;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BloodDonorController.class)
class MustacheTemplatesApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testHomePage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("donor_form"))
                .andExpect(model().attributeExists("donor"))
                .andExpect(model().attribute("donor", new Donor()));
    }

    @Test
    public void testDonorResultsEligible() throws Exception {

        List<Donor> eligibleValues = List.of(
                new Donor("Example", 17, 110),
                new Donor ("Example", 17, 150),
                new Donor ("Example", 30, 110),
                new Donor ("Example", 60, 200),
                new Donor ("Example", 60, 200),
                new Donor ("Example", 130, 600),
                new Donor ("Example", 130, 200),
                new Donor ("Example", 50, 600)
        );

        for (Donor eligible: eligibleValues) {
            mvc.perform(post("/submitDonorForm")
                            .param("firstName", eligible.getFirstName())
                            .param("age", eligible.getAge().toString())
                            .param("weight", eligible.getWeight().toString()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("results"))
                    .andExpect(model().attributeExists("donor"))
                    .andExpect(model().attributeDoesNotExist("ineligibleReasons"))
                    .andExpect(model().attribute("resultMessage", "You may be eligible to donate blood"))
                    .andExpect(content().string(containsString("Continue checking your eligibility")));
        }
    }

    @Test
    public void testDonorResultsNotEligible() throws Exception {

        String AGE_TOO_LOW = "Age must be 17 or older";
        String WEIGHT_TOO_LOW = "Weight must be 110 pounds or more";

        class Ineligible {
            List<String> reasons;
            String age;
            String weight;

            public Ineligible(String age, String weight, List<String> reasons) {
                this.reasons = reasons;
                this.age = age;
                this.weight = weight;
            }
        }

        // Values within the allowed ranges but not eligible
        List<Ineligible> eligibleValues = List.of(
                new Ineligible( "16", "110", List.of(AGE_TOO_LOW)),
                new Ineligible( "17", "109", List.of(WEIGHT_TOO_LOW)),
                new Ineligible( "3", "10", List.of(AGE_TOO_LOW, WEIGHT_TOO_LOW)),
                new Ineligible( "0", "0", List.of(AGE_TOO_LOW, WEIGHT_TOO_LOW)),
                new Ineligible( "100", "100", List.of(WEIGHT_TOO_LOW)),
                new Ineligible( "40", "109", List.of(WEIGHT_TOO_LOW)),
                new Ineligible( "130", "100", List.of(WEIGHT_TOO_LOW)),
                new Ineligible( "15", "600", List.of(AGE_TOO_LOW))
        );

        for (Ineligible values: eligibleValues) {
            mvc.perform(post("/submitDonorForm")
                            .param("firstName", "Example name")
                            .param("age", values.age)
                            .param("weight", values.weight))
                    .andExpect(status().isOk())
                    .andExpect(view().name("results"))
                    .andExpect(model().attributeExists("donor"))
                    .andExpect(model().attributeExists("ineligibleReasons"))
                    .andExpect(model().attribute("ineligibleReasons", values.reasons))
                    .andExpect(model().attribute("resultMessage", "You are not eligible to donate blood"));
        }

    }


    @Test
    public void testDonorInvalid () throws Exception {

        List<String[]> invalidValues = List.of(
                new String[] {"example", null, "110"},
                new String[] {"example", null, null},
                new String[] {"example", "40", null},
                new String[] {"example", "cat", "110"},
                new String[] {"example", "17", "pizza"},
                new String[] {"example", "", "110"},
                new String[] {"example", "", ""},
                new String[] {"example", "sixty", "200"},
                new String[] {"example", "sixty", "two hundred"},
                new String[] {"example", "130a", "   "},
                new String[] {"example", "50", "130s"},
                new String[] {"example", "50", "123456789012345678"},
                new String[] {"example", "234567854328134534", "123456789012345678"},
                new String[] {"example", "234567854328134534", "200"},
                new String[] {"example", "sfsd", "sdfsdf"},

                new String[] {null, null, "110"},
                new String[] {null, null, null},
                new String[] {null, "40", null},
                new String[] {null, "cat", "110"},
                new String[] {null, "17", "pizza"},
                new String[] {null, "", "110"},
                new String[] {null, "", ""},
                new String[] {null, "sixty", "200"},
                new String[] {null, "sixty", "two hundred"},
                new String[] {null, "130a", "   "},
                new String[] {null, "50", "130s"},
                new String[] {null, "50", "123456789012345678"},
                new String[] {null, "234567854328134534", "123456789012345678"},
                new String[] {null, "234567854328134534", "200"},
                new String[] {null, "sfsd", "sdfsdf"},

                new String[] {"", null, "110"},
                new String[] {"", null, null},
                new String[] {"", "40", null},
                new String[] {"", "cat", "110"},
                new String[] {"", "17", "pizza"},
                new String[] {"", "", "110"},
                new String[] {"", "", ""},
                new String[] {"", "sixty", "200"},
                new String[] {"", "sixty", "two hundred"},
                new String[] {"", "130a", "   "},
                new String[] {"", "50", "130s"},
                new String[] {"", "50", "123456789012345678"},
                new String[] {"", "234567854328134534", "123456789012345678"},
                new String[] {"", "234567854328134534", "200"},
                new String[] {"", "sfsd", "sdfsdf"}
        );

        for (String[] values: invalidValues) {
            mvc.perform(post("/submitDonorForm")
                            .param("name", values[0])
                            .param("age", values[1])
                            .param("weight", values[2]))
                    .andExpect(status().isOk())
                    .andExpect(view().name("donor_form"))
                    .andExpect(model().attributeExists("donor"))
                    .andExpect(model().attributeExists("validationErrorMap"));
        }

    }


}
