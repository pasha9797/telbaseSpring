package com.csf.telbase;

import com.csf.telbase.models.dto.*;
import com.csf.telbase.repsitories.*;
import com.csf.telbase.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TelbaseApplicationTests {
	@Autowired
	public SubscriberRepository subscriberRepository;

	SubscriberService abonentService;
	private Scanner in=new Scanner(System.in);

	@Test
	public void contextLoads() {
		abonentService = new SubscriberService(subscriberRepository);
		char key='c';

		while(key!='q') {
			printAbonents();

			System.out.println("Press one of these symbols:\n a - add item\n d - delete item\n e - edit item\n q - quit");
			key='c';
			String s=in.nextLine();
			if(s.length()==1){
				key=s.charAt(0);
			}
			switch(key){
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


	private void printAbonents(){
		for (SubscriberDTO ab: abonentService.getAll()) {
			System.out.printf("%s",ab.getName());
			for(Object o:ab.getPhones()){
				PhoneNumberDTO phone = (PhoneNumberDTO) o;
				if(phone!=null){
					System.out.print(" | "+phone.getWholeNumber());
				}
			}
			System.out.println();
		}
	}

	private void parseAdd(){
		System.out.println("Write name, then from 1 to 3 phone numbers.");
		String line=in.nextLine();
		String[] parts = line.split(" ");
		if(parts.length<3) {
			System.out.println("Error: Not enough data.");
			return;
		}
		ArrayList<String> phones = new ArrayList<String>();
		for(int i=2;i<parts.length;i++){
			phones.add(parts[i]);
		}

		try {
			SubscriberDTO ab = new SubscriberDTO(parts[0] + " " + parts[1], phones);
			abonentService.add(ab);
		}
		catch(Exception e){
			System.out.println("Error: "+e.getMessage());
		}
	}
	private void parseEdit(){
		String name;
		SubscriberDTO ab;
		if(!abonentService.getAll().iterator().hasNext()){
			System.out.println("Error: database is empty.");
			return;
		}
		System.out.println("Write abonent name");
		name = in.nextLine();
		ab=abonentService.get(name);
		if(ab == null){
			System.out.println("Error: no such abonent in database.");
			return;
		}

		System.out.println("Write option:\n 1 - Change name\n 2 - Change number \n 3 - Add number\n 4 - Delete Number");
		int pid;
		pid=readPositiveInt();
		if(pid==-1) return;

		proceedOption(ab, pid);
	}

	private void parseDelete(){
		String name;
		if(!abonentService.getAll().iterator().hasNext()){
			System.out.println("Error: database is empty.");
			return;
		}
		System.out.println("Write name.");
		name = in.nextLine();
		if(!abonentService.delete(name))
			System.out.println("Error: no such abonent in database.");
	}

	private void proceedOption(SubscriberDTO ab, int option){
		try {
			int index;
			switch (option) {
				case 1:
					System.out.println("Write a name:");
					in.nextLine();
					abonentService.editName(ab, in.nextLine());
					break;
				case 2:
					System.out.println("Write a phone index (1-3):");
					index=readPositiveInt();
					if(index==-1) return;

					System.out.println("Write a new phone:");
					String number = in.next();

					PhoneNumberDTO phone = (PhoneNumberDTO) ab.getPhones().toArray()[index - 1];
					abonentService.editPhone(ab, index, number);
					in.nextLine();
					break;
				case 3:
					if (ab.getPhones().size() > 2) {
						System.out.println("Error: Not more than 3 numbers allowed");
						return;
					}
					System.out.println("Write a phone:");

					PhoneNumberDTO num = new PhoneNumberDTO(in.nextLine());
					ab.getPhones().add(num);
					abonentService.edit(ab);
					in.nextLine();
					break;
				case 4:
					System.out.println("Write a phone index (1-3):");
					index=readPositiveInt();
					if(index==-1) return;

					if (index < 0 || index >= ab.getPhones().size()) {
						System.out.println("Error: Wrong number index");
						return;
					}

					ab.getPhones().remove(index);
					abonentService.edit(ab);
					in.nextLine();
					break;
				default:
					System.out.println("Error: Wrong command.");
					in.nextLine();
			}
		}
		catch(Exception e){
			System.out.println("Error: "+e.getMessage());
		}
	}

	private int readPositiveInt(){
		try {
			int i=in.nextInt();
			if(i>=0)
				return i;
			else{
				System.out.println("Error: Write non-negative digit.");
				in.nextLine();
				return -1;
			}

		} catch (Exception e) {
			System.out.println("Error: Write a digit.");
			in.nextLine();
			return -1;
		}
	}
}
