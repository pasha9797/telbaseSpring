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

    public SubscriberController(SubscriberService service){
        subscriberService = service;
    }

    @DeleteMapping("/delete/{name}")
    public void delete(@PathVariable String name) {
        subscriberService.delete(name);
    }

    @GetMapping("/get/{name}")
    public @ResponseBody ResponseEntity<SubscriberDTO> get(@PathVariable String name) {
        SubscriberDTO subscriber = subscriberService.get(name);
        if (subscriber != null) {
            return new ResponseEntity<>(subscriber, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public @ResponseBody Iterable<SubscriberDTO> getAll() {
        return subscriberService.getAll();
    }

    @PostMapping("/add")
    public void post(@RequestBody SubscriberDTO subscriber) {
        subscriberService.add(subscriber);
    }
}
