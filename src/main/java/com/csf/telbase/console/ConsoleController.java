package com.csf.telbase.console;

import com.csf.telbase.models.dto.SubscriberDTO;
import com.csf.telbase.models.dto.PhoneNumberDTO;
import com.csf.telbase.repsitories.SubscriberRepository;
import com.csf.telbase.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


//@Component
public class ConsoleController implements CommandLineRunner {
    @Autowired
    public SubscriberRepository subscriberRepository;

    private SubscriberService subscriberService;
    private Scanner in = new Scanner(System.in);

    @Override
    public void run(String... args) {

        subscriberService = new SubscriberService(subscriberRepository);
        char key = 'c';

        while (key != 'q') {
            printAbonents();

            System.out.println("Press one of these symbols:\n a - add item\n d - delete item\n e - edit item\n q - quit");
            key = 'c';
            String s = in.nextLine();
            if (s.length() == 1) {
                key = s.charAt(0);
            }
            switch (key) {
                case 'a':
                    parseAdd();
                    break;
                case 'd':
                    parseDelete();
                    break;
                case 'e':
                    parseEdit();
                    break;
                case 'q':
                    break;
                default:
                    System.out.println("Error: Unknown character.");
            }
        }
    }

    private void printAbonents() {
        for (SubscriberDTO ab : subscriberService.getAll()) {
            System.out.printf("%s", ab.getName());
            for (Object o : ab.getPhones()) {
                PhoneNumberDTO phone = (PhoneNumberDTO) o;
                if (phone != null) {
                    System.out.print(" | " + phone.getWholeNumber());
                }
            }
            System.out.println();
        }
    }

    private void parseAdd() {
        System.out.println("Write name, then from 1 to 3 phone numbers.");
        String line = in.nextLine();
        String[] parts = line.split(" ");
        if (parts.length < 3) {
            System.out.println("Error: Not enough data.");
            return;
        }
        List<PhoneNumberDTO> phones = new ArrayList<PhoneNumberDTO>();

        try {
        for (int i = 2; i < parts.length; i++) {
            phones.add(new PhoneNumberDTO(parts[i]));
        }
            SubscriberDTO ab = new SubscriberDTO(parts[0] + " " + parts[1], phones);
            subscriberService.add(ab);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void parseEdit() {
        try {
            String name;
            SubscriberDTO ab;
            if (!subscriberService.getAll().iterator().hasNext()) {
                System.out.println("Error: database is empty.");
                return;
            }
            System.out.println("Write abonent name");
            name = in.nextLine();
            ab = subscriberService.get(name);
            if (ab == null) {
                System.out.println("Error: no such abonent in database.");
                return;
            }

            System.out.println("Write option:\n 1 - Change name\n 2 - Change number \n 3 - Add number\n 4 - Delete Number");
            int pid;
            pid = readPositiveInt();
            if (pid == -1) return;

            proceedOption(ab, pid);
        }
        catch(Exception e){System.out.println(e.getMessage());}
    }

    private void parseDelete() {
        try {
            String name;
            if (!subscriberService.getAll().iterator().hasNext()) {
                System.out.println("Error: database is empty.");
                return;
            }
            System.out.println("Write name.");
            name = in.nextLine();
            subscriberService.delete(name);
        }
        catch(Exception e){System.out.println(e.getMessage());}
    }

    private void proceedOption(SubscriberDTO ab, int option) {
        try {
            int index;
            switch (option) {
                case 1:
                    System.out.println("Write a name:");
                    subscriberService.editName(ab, in.nextLine());
                    return;
                case 2:
                    System.out.println("Write a phone index (1-3):");
                    index = readPositiveInt();
                    if (index == -1) {
                        return;
                    }

                    if (index < 1 || index > ab.getPhones().size()) {
                        System.out.println("Error: Wrong number index");
                        return;
                    }

                    System.out.println("Write a new phone:");
                    String number = in.nextLine();

                    PhoneNumberDTO phone = (PhoneNumberDTO) ab.getPhones().toArray()[index - 1];
                    subscriberService.editPhone(ab, index, number);
                    break;
                case 3:
                    if (ab.getPhones().size() > 2) {
                        System.out.println("Error: Not more than 3 numbers allowed");
                        return;
                    }
                    System.out.println("Write a phone:");
                    PhoneNumberDTO num = new PhoneNumberDTO(in.nextLine());
                    ab.getPhones().add(num);
                    subscriberService.edit(ab);
                    return;
                case 4:
                    if (ab.getPhones().size() < 2) {
                        System.out.println("Error: Subscriber must have at least 1 number");
                        return;
                    }

                    System.out.println("Write a phone index (1-3):");
                    index = readPositiveInt();
                    if (index == -1) {
                        return;
                    }

                    if (index < 1 || index > ab.getPhones().size()) {
                        System.out.println("Error: Wrong number index");
                        return;
                    }

                    ab.getPhones().remove(index - 1);
                    subscriberService.edit(ab);
                    break;
                default:
                    System.out.println("Error: Wrong command.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int readPositiveInt() {
        try {
            int i = in.nextInt();
            in.nextLine();
            if (i >= 0)
                return i;
            else {
                System.out.println("Error: Write non-negative digit.");
                return -1;
            }

        } catch (Exception e) {
            System.out.println("Error: Write a digit.");
            in.nextLine();
            return -1;
        }
    }
}
