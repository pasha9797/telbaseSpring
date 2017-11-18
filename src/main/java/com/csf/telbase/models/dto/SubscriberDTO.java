package com.csf.telbase.models.dto;

import java.util.HashSet;
import java.util.List;
import com.csf.telbase.other.*;
import java.util.ArrayList;
import com.csf.telbase.exceptions.*;

public class SubscriberDTO {
    private static final int MinNameLength=1;
    private static final int MaxNameLength=15;

    private static final int MinSurnameLength=1;
    private static final int MaxSurnameLength=20;

    private String name;
    private List phones;


    public String getName(){
        return name;
    }

    public void setName(String name) throws InvalidNameFormatException{
        if(checkName(name)) {
            this.name = name;
        }
        else {
            throw new InvalidNameFormatException(
                    String.format("Name must have format: 'Name Surname'. Name from %d to %d symbols, surname from %d to %d symbols.",
                            MinNameLength, MaxNameLength, MinSurnameLength, MaxSurnameLength));
        }
    }

    public List getPhones(){
        return phones;
    }

    public void setPhones(ArrayList<String> phones) throws NumberIndexOutOfBoundsException, InvalidNumberFromatException{
        if(phones.size()==0 || phones.size()>3)
            throw new NumberIndexOutOfBoundsException("Amount of numbers must be from 1 to 3");

        List<PhoneNumberDTO> nums=new ArrayList<>();
        for(int i=0;i<phones.size();i++) {
            nums.add(new PhoneNumberDTO(phones.get(i)));
        }
        this.phones=nums;
    }

    public void setPhones(List<PhoneNumberDTO> phones) throws NumberIndexOutOfBoundsException{
        if(phones.size()==0 || phones.size()>3)
            throw new NumberIndexOutOfBoundsException("Amount of numbers must be from 1 to 3");

        this.phones=phones;
    }

    private static boolean checkName(String name){
        return Checker.checkWithRegExp(name,String.format("^[a-zA-Zа-яА-Я]{%d,%d} [a-zA-Zа-яА-Я]{%d,%d}$",
                MinNameLength,MaxNameLength,MinSurnameLength,MaxSurnameLength));
    }

    public SubscriberDTO(String name, List<PhoneNumberDTO> numbers) throws NumberIndexOutOfBoundsException, InvalidNameFormatException{
        setName(name);
        setPhones(numbers);
    }

    public SubscriberDTO(String name, ArrayList<String> numbers) throws NumberIndexOutOfBoundsException, InvalidNameFormatException, InvalidNumberFromatException{
        setName(name);
        setPhones(numbers);
    }
}
