package com.csf.telbase.services;

import com.csf.telbase.exceptions.InvalidNameFormatException;
import com.csf.telbase.exceptions.InvalidNumberFromatException;
import com.csf.telbase.repsitories.SubscriberRepository;
import org.springframework.stereotype.Service;
import com.csf.telbase.models.*;
import com.csf.telbase.models.dto.*;
import com.csf.telbase.models.converters.*;

import java.util.ArrayList;
import java.util.Comparator;

@Service
public class SubscriberService {
    public final SubscriberRepository repository;

    public SubscriberService(SubscriberRepository repository) {
        this.repository = repository;
    }

    public void add(SubscriberDTO subscriberDTO) throws Exception {
        if (repository.findSubscriberByName(subscriberDTO.getName()) == null) {
            Subscriber subscriber = SubscriberConverter.convertToEntity(subscriberDTO);
            for (PhoneNumber num : subscriber.getPhones()) {
                num.setSubscriber(subscriber);
            }
            repository.save(subscriber);
        } else {
            throw new Exception("Subscriber with name " + subscriberDTO.getName() + " already exists in database");
        }
    }

    public void edit(SubscriberDTO dto) throws Exception {
        delete(dto.getName());
        add(dto);
    }

    public void editName(SubscriberDTO dto, String name) throws Exception {
        String oldName = dto.getName();
        dto.setName(name);
        delete(oldName);
        add(dto);
    }

    public void editPhone(SubscriberDTO dto, int phoneIndex, String phone) throws Exception {
        if (dto.getPhones().size() >= phoneIndex) {
            PhoneNumberDTO oldPhone = (PhoneNumberDTO) dto.getPhones().toArray()[phoneIndex - 1];
            oldPhone.setWholeNumber(phone);
        } else {
            dto.getPhones().add(new PhoneNumberDTO(phone));
        }
        delete(dto.getName());
        add(dto);
    }

    public void delete(String name) throws Exception {
        Subscriber subscriber = repository.findSubscriberByName(name);
        if (subscriber != null) {
            repository.delete(subscriber);
        } else
            throw new Exception("Can not delete subscriber with name " + name + ": not found");
    }

    public SubscriberDTO get(String name) throws Exception {
        Subscriber subscriber = repository.findSubscriberByName(name);
        if (subscriber != null)
            return SubscriberConverter.convertToDTO(subscriber);
        else
            throw new Exception("Can not get subscriber with name " + name + ": not found");
    }

    public Iterable<SubscriberDTO> getAll() {
        Iterable<Subscriber> subscribers = repository.findAll();
        ArrayList<SubscriberDTO> subscriberDTOS = new ArrayList<>();
        for (Subscriber ab : subscribers) {
            subscriberDTOS.add(SubscriberConverter.convertToDTO(ab));
        }
        subscriberDTOS.sort(Comparator.comparing(SubscriberDTO::getName));
        return subscriberDTOS;
    }
}
