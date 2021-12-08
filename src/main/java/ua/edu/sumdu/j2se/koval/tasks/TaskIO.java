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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

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
            writeTask.writeInt(TasksQuantity);
            for (Task temp: tasks) {
                String taskTitle = temp.getTitle();

                int taskTitleLength = taskTitle.length();
                writeTask.writeInt(taskTitleLength);
                writeTask.writeUTF(taskTitle);
                int taskActivity = temp.isActive() ? 1 :0;
                writeTask.writeInt(taskActivity);
                writeTask.writeBoolean(temp.isRepeated());
                if ( temp.isRepeated()) {
                    LocalDateTime startTime = temp.getStartTime();
                    writeTask.writeLong(startTime.atZone(ZoneId.systemDefault()).toEpochSecond());
                    LocalDateTime endTime = temp.getEndTime();
                    writeTask.writeLong(endTime.atZone(ZoneId.systemDefault()).toEpochSecond());
                    writeTask.writeInt(temp.getRepeatInterval());
                } else {
                    LocalDateTime startTime = temp.getTime();
                    writeTask.writeLong(startTime.atZone(ZoneId.systemDefault()).toEpochSecond());

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
            tasks.tasksQuantity = readTask.readInt();
            for (int i = 0; i < tasks.tasksQuantity; i++) {
                Task task;
                int taskTitleLength = readTask.readInt();
                String  title = readTask.readUTF();
                int activity = readTask.readInt();

                if (readTask.readBoolean()) {
                    LocalDateTime startTime =
                            LocalDateTime.ofInstant(Instant.ofEpochSecond(readTask.readLong()),ZoneId.systemDefault());

                    LocalDateTime endTime =
                            LocalDateTime.ofInstant(Instant.ofEpochSecond(readTask.readLong()),ZoneId.systemDefault());

                    int interval = readTask.readInt();
                    task= new Task(title,startTime,endTime,interval);
                } else {
                    LocalDateTime time =
                            LocalDateTime.ofInstant(Instant.ofEpochSecond(readTask.readLong()),ZoneId.systemDefault());
                    task= new Task(title,time);
                }
                task.setActive(activity == 1);
                tasks.add(task);

            }
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     Метод що записує  задачі із списку у файл.
     */
    public static void writeBinary(AbstractTaskList tasks, File file)  {
        try (FileOutputStream fos = new FileOutputStream(file))
        {
            write(tasks,fos);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     Метод що  зчитує задачі із файлу у список задач.
     */
    public static void readBinary(AbstractTaskList tasks, File file)  {
        try ( FileInputStream fis = new FileInputStream(file))
        {
            read(tasks,fis);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
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
            bw.flush();
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

    /**
     Метод що записує задачі у файл у форматі JSON.
     */
    public static void writeText(AbstractTaskList tasks, File file)  {
        try (FileWriter fw = new FileWriter(file))
        {
            write(tasks,fw);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     Метод що – зчитує задачі із файлу.
     */
    public static void readText(AbstractTaskList tasks, File file) {
        try ( FileReader fr = new FileReader(file))
        {
            read(tasks,fr);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
