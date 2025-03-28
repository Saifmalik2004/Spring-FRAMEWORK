package com.learnwithsaif.project_3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learnwithsaif.project_3.model.Contact;
import com.learnwithsaif.project_3.service.ContactService;




@Controller
public class ContactController {

    private final ContactService contactService;

    @RequestMapping("/contact")
    public String showContactForm() {
        return "contact"; // Returns contact.html from templates
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

    @PostMapping(value = "/saveMsg")
    public String saveMessage(Contact contact) {
        contactService.saveMessageDetail(contact);
        return "redirect:/contact";
    }

}