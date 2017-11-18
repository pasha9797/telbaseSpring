package com.csf.telbase.services;

import com.csf.telbase.exceptions.InvalidNameFormatException;
import com.csf.telbase.exceptions.InvalidNumberFromatException;
import com.csf.telbase.repsitories.SubscriberRepository;
import org.springframework.stereotype.Service;
import com.csf.telbase.models.*;
import com.csf.telbase.models.dto.*;
import com.csf.telbase.models.converters.*;

import java.util.ArrayList;

@Service
public class SubscriberService {
    public final SubscriberRepository repository;

    public SubscriberService(SubscriberRepository repository) {
        this.repository = repository;
    }

    public void add(SubscriberDTO subscriberDTO) {
        Subscriber subscriber = SubscriberConverter.convertToEntity(subscriberDTO);
        for(PhoneNumber num : subscriber.getPhones()){
            num.setSubscriber(subscriber);
        }
        repository.save(subscriber);
    }

    public void edit(SubscriberDTO dto) {
        delete(dto.getName());
        add(dto);
    }

    public void editName(SubscriberDTO dto, String name) throws InvalidNameFormatException{
        String oldName=dto.getName();
        dto.setName(name);
        delete(oldName);
        add(dto);
    }

    public void editPhone(SubscriberDTO dto, int phoneIndex, String phone)throws InvalidNumberFromatException{
        PhoneNumberDTO oldPhone =  (PhoneNumberDTO) dto.getPhones().toArray()[phoneIndex-1];
        oldPhone.setWholeNumber(phone);
        delete(dto.getName());
        add(dto);
    }

    public boolean delete(String name) {
        Subscriber subscriber = repository.findSubscriberByName(name);
        if (subscriber !=null) {
            repository.delete(subscriber);
            return true;
        }
        else
            return false;
    }

    public SubscriberDTO get(String name) {
        Subscriber subscriber = repository.findSubscriberByName(name);
        if (subscriber !=null)
            return SubscriberConverter.convertToDTO(subscriber);
        else
            return null;
    }

    public Iterable<SubscriberDTO> getAll() {
        Iterable<Subscriber> subscribers= repository.findAll();
        ArrayList<SubscriberDTO> subscriberDTOS =new ArrayList<>();
        for(Subscriber ab:subscribers){
            subscriberDTOS.add(SubscriberConverter.convertToDTO(ab));
        }
        return subscriberDTOS;
    }
}
