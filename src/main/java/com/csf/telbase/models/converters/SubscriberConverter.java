package com.csf.telbase.models.converters;

import com.csf.telbase.models.*;
import com.csf.telbase.models.dto.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubscriberConverter {
    public static SubscriberDTO convertToDTO(Subscriber subscriber){
        List<PhoneNumberDTO> numbers= new ArrayList<PhoneNumberDTO>();
        Iterator iterator = subscriber.getPhones().iterator();
        while (iterator.hasNext()) {
            PhoneNumber number = (PhoneNumber) iterator.next();
            numbers.add(PhoneNumberConverter.convertToDTO(number));
        }
        SubscriberDTO dto=null;
        try { dto = new SubscriberDTO(subscriber.getName(), numbers);
        }
        catch(Exception e){
            System.out.println("Failed to convert subscriber entity to DTO: "+e.getMessage());
        }
        return dto;
    }

    public static Subscriber convertToEntity(SubscriberDTO dto){
        List<PhoneNumber> numbers = new ArrayList<>();
        Iterator iterator = dto.getPhones().iterator();
        while (iterator.hasNext()) {
            PhoneNumberDTO number = (PhoneNumberDTO) iterator.next();
            numbers.add(PhoneNumberConverter.convertToEntity(number));
        }

        Subscriber subscriber = new Subscriber();
        subscriber.setName(dto.getName());
        subscriber.setPhones(numbers);
        return subscriber;
    }
}
