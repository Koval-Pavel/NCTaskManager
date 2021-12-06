package ua.edu.sumdu.j2se.koval.tasks;



import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Hello");


// ------------------------- GSON
		System.out.println("Работа с Gson");
//----------------------------------------------------------- проверка для методов из TaskIO
//		AbstractTaskList beforeSerial = new LinkedTaskList();
//		beforeSerial.add(new Task("Test f", LocalDateTime.now()));
//
//		AbstractTaskList afterSerial = new LinkedTaskList();
//
//		TaskIO.write(beforeSerial, new FileWriter("test.json"));
//		TaskIO.read(afterSerial, new FileReader("test.json"));
//		System.out.println(afterSerial);

//---------------------------------------------------------- Ручная работа
//		AbstractTaskList beforeSerial = new ArrayTaskList();
//		beforeSerial.add(new Task("Test f", LocalDateTime.now()));
//
//		Gson gson = new Gson();
//		String jsonLine =  gson.toJson(beforeSerial);
//
//		AbstractTaskList afterSerial = gson.fromJson(jsonLine, ArrayTaskList.class);
//
//		System.out.println(afterSerial);

	}

}
