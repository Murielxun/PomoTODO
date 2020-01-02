package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {
    
    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.getName());
        return tagJson;
    }
    
    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();
        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());
        return priorityJson;
    }
    
    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject duedateJson = new JSONObject();
        if (dueDate == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            Date d = dueDate.getDate();
            c.setTime(d);
            duedateJson.put("year", c.get(Calendar.YEAR));
            duedateJson.put("month", c.get(Calendar.MONTH));
            duedateJson.put("day", c.get(Calendar.DAY_OF_MONTH));
            duedateJson.put("hour", c.get(Calendar.HOUR_OF_DAY));
            duedateJson.put("minute", c.get(Calendar.MINUTE));
            return duedateJson;
        }
    }
    
    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();
        JSONArray tagsArray = new JSONArray();
        for (Tag t: task.getTags()) {
            tagsArray.put(tagToJson(t));
        }
        taskJson.put("description",task.getDescription());
        taskJson.put("tags",tagsArray);
        taskJson.put("due-date",dueDateToJson(task.getDueDate()) == null
                ? JSONObject.NULL : dueDateToJson(task.getDueDate()));
        taskJson.put("priority",priorityToJson(task.getPriority()));
        taskJson.put("status",task.getStatus().getDescription().replace(" ","_"));
        return taskJson;
    }
    
    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray tasksArray = new JSONArray();
        for (Task t: tasks) {
            tasksArray.put(taskToJson(t));
        }
        return tasksArray;
    }
}
