package com.csf.telbase.repsitories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.csf.telbase.models.*;

@Repository
public interface PhoneNumberRepository extends CrudRepository<PhoneNumber,Long>{
}
