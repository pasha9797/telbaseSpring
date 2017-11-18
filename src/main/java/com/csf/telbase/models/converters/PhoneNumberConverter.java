package com.csf.telbase.models.converters;

import com.csf.telbase.models.*;
import com.csf.telbase.models.dto.*;

public class PhoneNumberConverter {
    public static PhoneNumberDTO convertToDTO(PhoneNumber number){
        return new PhoneNumberDTO(number.getCountry(),number.getNetwork(), number.getLocalNumber());
    }
    public static PhoneNumber convertToEntity(PhoneNumberDTO dto){
        PhoneNumber number = new PhoneNumber();
        number.setCountry(dto.getCountry());
        number.setNetwork(dto.getNetwork());
        number.setLocalNumber(dto.getLocalNumber());
        return number;
    }
}
