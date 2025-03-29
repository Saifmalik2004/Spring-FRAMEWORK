package com.learnwithsaif.project_5.service;

import org.springframework.stereotype.Service;

import com.learnwithsaif.project_5.model.Contact;


    
@Service
public class ContactService {

    public boolean saveMessageDetail(Contact contact) {
        System.out.println(contact.toString());
        return true;
    }
}

