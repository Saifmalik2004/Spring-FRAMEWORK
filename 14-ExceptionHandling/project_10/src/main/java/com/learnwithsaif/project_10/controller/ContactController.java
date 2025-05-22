package com.learnwithsaif.project_10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learnwithsaif.project_10.model.Contact;
import com.learnwithsaif.project_10.service.ContactService;

import jakarta.validation.Valid;




@Controller
public class ContactController {

    private final ContactService contactService;

    @RequestMapping("/contact")
    public String displayContactPage(Model model) {
        model.addAttribute("contact",new Contact());
        return "contact.html";
    }

    
    // @PostMapping(value = "/saveMsg")
    // public ModelAndView saveMessage(
    //     @RequestParam String name,
    //     @RequestParam String mobileNum,
    //     @RequestParam String email,
    //     @RequestParam String subject,
    //     @RequestParam String message
    // ) {
    //     System.out.println("Name : " + name);
    //     System.out.println("Mobile Number : " + mobileNum);
    //     System.out.println("Email : " + email);
    //     System.out.println("Subject : " + subject);
    //     System.out.println("Message : " + message);

    //     return new ModelAndView("redirect:/contact");
    // }



    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    @PostMapping(value="/saveMsg")
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {
        if(errors.hasErrors()){
            System.out.println("Contact form validation failed due to : " + errors.toString());
            return "contact.html";
        }
        
        contactService.saveMessageDetail(contact);
        contactService.setCounter(contactService.getCounter()+1);
        System.out.println("Number of times the Contact form is submitted : "+contactService.getCounter());
        return "redirect:/contact";
    }

}