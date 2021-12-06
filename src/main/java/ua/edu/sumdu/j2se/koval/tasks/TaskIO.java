package ua.edu.sumdu.j2se.koval.tasks;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;

/**
 Клас що реалізовує можливість передачі через мережу та збереження на диску списків задач.
 записує задачі із списку у
 потік у бінарному форматі, описаному нижче.
 */
public class TaskIO {

    /**
     Метод що записує задачі із списку у потік у бінарному форматі.
     */
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (DataOutputStream writeTask = new DataOutputStream(out))
        {
            int TasksQuantity =  tasks.size();
            writeTask.writeInt(TasksQuantity);                              // Кількість задач
            for (Task temp: tasks) {
                String taskTitle = temp.getTitle();

                int taskTitleLength = taskTitle.length();
                writeTask.writeInt(taskTitleLength);                        // довжина назви
                writeTask.writeUTF(taskTitle);                              // Назва
                int taskActivity = temp.isActive() ? 1 :0;
                writeTask.writeInt(taskActivity);                           // Активність
                writeTask.writeBoolean(temp.isRepeated());                  // Повторюваніть
                if ( temp.isRepeated()) {
                    LocalDateTime startTime = temp.getStartTime();
                    writeTask.writeInt(startTime.getYear());                // Інтервали 7 + 7
                    writeTask.writeInt(startTime.getMonthValue());
                    writeTask.writeInt(startTime.getDayOfMonth());
                    writeTask.writeInt(startTime.getHour());
                    writeTask.writeInt(startTime.getMinute());
                    writeTask.writeInt(startTime.getSecond());
                    writeTask.writeInt(startTime.getNano());

                    LocalDateTime endTime = temp.getEndTime();
                    writeTask.writeInt(endTime.getYear());
                    writeTask.writeInt(endTime.getMonthValue());
                    writeTask.writeInt(endTime.getDayOfMonth());
                    writeTask.writeInt(endTime.getHour());
                    writeTask.writeInt(endTime.getMinute());
                    writeTask.writeInt(endTime.getSecond());
                    writeTask.writeInt(endTime.getNano());
                    writeTask.writeInt(temp.getRepeatInterval());           // інтервал
                } else {

                    LocalDateTime startTime = temp.getTime();               // час виконання 7
                    writeTask.writeInt(startTime.getYear());
                    writeTask.writeInt(startTime.getMonthValue());
                    writeTask.writeInt(startTime.getDayOfMonth());
                    writeTask.writeInt(startTime.getHour());
                    writeTask.writeInt(startTime.getMinute());
                    writeTask.writeInt(startTime.getSecond());
                    writeTask.writeInt(startTime.getNano());
                }
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    /**
     Метод що зчитує задачі із потоку у даний список задач
     */
    public static void read(AbstractTaskList tasks, InputStream in) {
        try (DataInputStream readTask = new DataInputStream(in))
        {
            tasks.tasksQuantity = readTask.readInt();                        // read Кількість задач
            for (int i = 0; i < tasks.tasksQuantity; i++) {

                tasks.add(new Task("Default",LocalDateTime.MIN,LocalDateTime.MIN,0));

                int taskTitleLength = readTask.readInt();                    // read довжина назви
                tasks.getTask(i).setTitle(readTask.readUTF());               // read Назва
                tasks.getTask(i).setActive(readTask.readInt() == 1);         // read Активність

                if (readTask.readBoolean()) {
                    LocalDateTime startTime;
                    startTime =LocalDateTime.of(readTask.readInt(),readTask.readInt(),readTask.readInt(),               //read Інтервали 7 + 7
                            readTask.readInt(),readTask.readInt(),readTask.readInt(),readTask.readInt()) ;

                    LocalDateTime endTime;
                    endTime =LocalDateTime.of(readTask.readInt(),readTask.readInt(),readTask.readInt(),
                            readTask.readInt(),readTask.readInt(),readTask.readInt(),readTask.readInt()) ;

                    int interval = readTask.readInt();
                    tasks.getTask(i).setTime(startTime, endTime, interval);
                } else {
                    LocalDateTime time;
                    time =LocalDateTime.of(readTask.readInt(),readTask.readInt(),readTask.readInt(),                    // read час виконання 7
                            readTask.readInt(),readTask.readInt(),readTask.readInt(),readTask.readInt()) ;
                    tasks.getTask(i).setTime(time);
                }
            }
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     Метод що записує  задачі із списку у файл.
     */
    public static void writeBinary(AbstractTaskList tasks, File file) throws FileNotFoundException {
        write (tasks, new FileOutputStream(file)) ;
    }

    /**
     Метод що  зчитує задачі із файлу у список задач.
     */
    public static void readBinary(AbstractTaskList tasks, File file) throws FileNotFoundException {
        read (tasks, new FileInputStream(file));
    }

    /**
     Метод що записує задачі зі списку у потік в форматі JSON.
     */
    public static void write(AbstractTaskList tasks, Writer out) {
        try ( BufferedWriter bw = new BufferedWriter (out)) {
            Gson gson = new Gson();
            String jSonString;
            AbstractTaskList listForSerial;

            if (tasks instanceof LinkedTaskList) {
                listForSerial = new ArrayTaskList();
                for (Task temp : tasks) {
                    listForSerial.add(temp);
                }
            } else {
                listForSerial = tasks;
            }
            jSonString =  gson.toJson(listForSerial);
            bw.write(jSonString);
        }
        catch(IOException  ex){

            System.out.println(ex.getMessage());
        }
    }

    /**
     Метод що зчитує задачі із потоку у список.
     */
    public static void read(AbstractTaskList tasks, Reader in) {
        try ( BufferedReader br = new BufferedReader (in)) {
            Gson gson = new Gson();
            String jsonLine = br.readLine();
            AbstractTaskList tempTask;
            tempTask = gson.fromJson(jsonLine, ArrayTaskList.class);
            for (Task temp: tempTask) {
                tasks.add(temp);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
//-----------------------------------------------

    /**
     Метод що записує задачі у файл у форматі JSON.
     */
    public static void writeText(AbstractTaskList tasks, File file) throws IOException {
        write(tasks, new FileWriter(file));
    }

    /**
     Метод що – зчитує задачі із файлу.
     */
    public static void readText(AbstractTaskList tasks, File file) throws IOException {
        read(tasks, new FileReader(file));
    }


}
