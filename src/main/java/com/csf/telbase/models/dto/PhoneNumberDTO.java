package com.csf.telbase.models.dto;

import com.csf.telbase.other.*;
import com.csf.telbase.exceptions.*;

public class PhoneNumberDTO {
    private long country;
    private long network;
    private long localNumber;

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }

    public long getNetwork() {
        return network;
    }

    public void setNetwork(long network) {
        this.network = network;
    }

    public long getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(long localNumber) {
        this.localNumber = localNumber;
    }

    public String getWholeNumber(){
        return String.format("+%d-%d-%d",country,network,localNumber);
    }

    public void setWholeNumber(String number) throws InvalidNumberFromatException {
        String num=number.replaceAll(" ","");
        if(checkNumber(num)){
            boolean plus = num.startsWith("+");
            num=num.replaceAll("(\\+)|(-)","");

            String[] parts = new String[3];
            int countryLength;
            countryLength = num.length()-10;
            parts[0]=num.substring(0,countryLength);
            parts[1]=num.substring(countryLength,countryLength+3);
            parts[2]=num.substring(countryLength+3,num.length());

            int country = Integer.parseInt(parts[0]);
            if(!plus)
                country--;

            setCountry(country);
            setNetwork(Integer.parseInt(parts[1]));
            setLocalNumber(Integer.parseInt(parts[2]));
        }
        else
            throw new InvalidNumberFromatException("Phone format must be: +(country)-(network)-(number)");
    }

    public static boolean checkNumber(String number){
        return Checker.checkWithRegExp(number,"^(\\+)?\\d{1,3}-?\\d{3}-?\\d{7}$");
    }

    public PhoneNumberDTO(String wholeNumber) throws InvalidNumberFromatException{
        setWholeNumber(wholeNumber);
    }

    public PhoneNumberDTO(long country, long network, long localNumber){
        setCountry(country);
        setNetwork(network);
        setLocalNumber(localNumber);
    }
}
