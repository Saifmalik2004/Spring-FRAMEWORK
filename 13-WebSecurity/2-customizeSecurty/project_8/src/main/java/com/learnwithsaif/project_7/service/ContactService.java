package com.learnwithsaif.project_7.service;

import org.springframework.stereotype.Service;

import com.learnwithsaif.project_7.model.Contact;


@Service
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


