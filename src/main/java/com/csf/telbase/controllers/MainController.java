package com.csf.telbase.controllers;

import com.csf.telbase.models.dto.PhoneNumberDTO;
import com.csf.telbase.models.dto.SubscriberDTO;
import com.csf.telbase.models.forms.AddSubscriberForm;
import com.csf.telbase.models.forms.ChangeNameForm;
import com.csf.telbase.models.forms.ChangePhoneForm;
import com.csf.telbase.services.SubscriberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private final SubscriberService subscriberService;

    public MainController(SubscriberService service){
        subscriberService = service;
    }

    @RequestMapping(value = {"/", "/index" }, method = RequestMethod.GET)
    public String getIndexPage(Model model) {

        model.addAttribute("subscribers", subscriberService.getAll());

        return "index";
    }

    @RequestMapping(value = { "/addSubscriber" }, method = RequestMethod.GET)
    public String getAddSubscriberPage(Model model) {

        AddSubscriberForm form = new AddSubscriberForm();
        model.addAttribute("addForm", form);

        return "addSubscriber";
    }

    @RequestMapping(value = { "/addSubscriber" }, method = RequestMethod.POST)
    public String saveSubscriber(Model model, @ModelAttribute("addForm") AddSubscriberForm form) {
        try {
            List<PhoneNumberDTO> phones = new ArrayList<>();
            if (form.getPhone1().length() > 0) {
                phones.add(new PhoneNumberDTO(form.getPhone1()));
            }
            if (form.getPhone2().length() > 0) {
                phones.add(new PhoneNumberDTO(form.getPhone2()));
            }
            if (form.getPhone3().length() > 0) {
                phones.add(new PhoneNumberDTO(form.getPhone3()));
            }

            SubscriberDTO subscriber = new SubscriberDTO(form.getName(), phones);
            subscriberService.add(subscriber);
            return "redirect:/index";
        }
        catch(Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "addSubscriber";
        }
    }

    @RequestMapping(value = { "/subscriberProfile" }, method = RequestMethod.GET)
    public String getProfile(Model model, @ModelAttribute("name") String name) {
        try{
        SubscriberDTO subscriber =subscriberService.get(name);
            model.addAttribute("subscriber", subscriber);
            return "subscriberPage";
        }
        catch (Exception e){
            return "redirect:/index";
        }
    }


}
