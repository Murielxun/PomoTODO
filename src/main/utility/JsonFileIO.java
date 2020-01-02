package utility;


import model.Task;
import org.json.JSONArray;
import parsers.TaskParser;
import persistence.Jsonifier;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");


    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        TaskParser tp = new TaskParser();
        List tasks = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(jsonDataFile));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            tasks = tp.parse(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        JSONArray ja = Jsonifier.taskListToJson(tasks);
        try {
            FileWriter fw = new FileWriter(jsonDataFile);
            fw.write(ja.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
