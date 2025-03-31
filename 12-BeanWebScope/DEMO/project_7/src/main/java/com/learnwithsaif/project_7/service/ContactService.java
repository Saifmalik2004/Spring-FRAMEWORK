package com.learnwithsaif.project_7.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import com.learnwithsaif.project_7.model.Contact;


@Service
// @RequestScope
// @SessionScope
@ApplicationScope
public class ContactService{
    private int counter=0;
    
    public boolean saveMessageDetail(Contact contact) {
         
        System.out.println(contact.toString());
        
        return true;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }



}


