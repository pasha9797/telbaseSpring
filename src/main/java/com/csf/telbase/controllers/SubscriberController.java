package com.csf.telbase.controllers;

import com.csf.telbase.models.Subscriber;
import com.csf.telbase.models.dto.SubscriberDTO;
import com.csf.telbase.services.SubscriberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService service) {
        subscriberService = service;
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        try {
            subscriberService.delete(name);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @DeleteMapping("/deletePhone/{name}/{numID}")
    public ResponseEntity<?> delete(@PathVariable String name, @PathVariable int numID) {
        try {
            SubscriberDTO subscriber = subscriberService.get(name);
            if (numID >= 0 && subscriber.getPhones().size() >= numID) {
                if(subscriber.getPhones().size() > 1) {
                    subscriber.getPhones().remove(numID - 1);
                    subscriberService.edit(subscriber);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<Object>(
                            "Can not delete the only one number",
                            HttpStatus.BAD_REQUEST
                    );
                }
            } else {
                return new ResponseEntity<Object>(
                        "Number ID out of bounds",
                        HttpStatus.BAD_REQUEST
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/get/{name}")
    public @ResponseBody
    ResponseEntity<?> get(@PathVariable String name) {
        try {
            SubscriberDTO subscriber = subscriberService.get(name);
            return new ResponseEntity<>(subscriber, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping()
    public @ResponseBody
    Iterable<SubscriberDTO> getAll() {
        return subscriberService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> post(@RequestBody SubscriberDTO subscriber) {
        try {
            subscriberService.add(subscriber);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/editName/{oldName}/{newName}")
    public ResponseEntity<?> editName(@PathVariable String newName, @PathVariable String oldName) {
        try {
            SubscriberDTO subscriber = subscriberService.get(oldName);
            newName = newName.replaceAll("\\+", " ");
            subscriberService.editName(subscriber, newName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/editPhone/{name}/{numID}/{newPhone}")
    public ResponseEntity<?> editPhone(@PathVariable String name, @PathVariable String newPhone, @PathVariable int numID) {
        try {
            SubscriberDTO subscriber = subscriberService.get(name);
            subscriberService.editPhone(subscriber, numID, newPhone);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
