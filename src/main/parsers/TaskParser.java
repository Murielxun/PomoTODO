package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

// Represents Task parser
public class TaskParser {
    private List tasks = new ArrayList();

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        JSONArray tasksarray = new JSONArray(input);
        for (Object object : tasksarray) {
            JSONObject taskobject = (JSONObject) object;
            try {
                tasks.add(setTask(taskobject));
            } catch (JSONException e) {
                continue;
            }
        }
        return tasks;
    }

    //EFFECTS: iterates over every JSONObject in the JSONArry and parses it as a set of tags
    private Set<Tag> managetags(JSONArray jsonarray) {
        Set<Tag> tags = new HashSet<>();
        for (Object object : jsonarray) {
            JSONObject tagobject = (JSONObject) object;
            String name = tagobject.getString("name");
            tags.add(new Tag(name));
        }
        return tags;
    }

    //EFFECTS: consume a JSONObject and return a due date, if the JSONObject is null then set
    //         the duedate to null
    private DueDate manageduedate(JSONObject jsonobject) {
        DueDate dueDate = new DueDate();
        if (jsonobject == null) {
            dueDate.setDueDate(null);
        } else {
            int year = jsonobject.getInt("year");
            int month = jsonobject.getInt("month");
            int day = jsonobject.getInt("day");
            int hour = jsonobject.getInt("hour");
            int minute = jsonobject.getInt("minute");
            Date d = new Date();
            Calendar c = Calendar.getInstance();
            c.set(year, month, day, hour, minute);
            d.setTime(c.getTimeInMillis());
            dueDate.setDueDate(d);
        }
        return dueDate;
    }

    //EFFECTS: consume a JSONObject and return a priority
    private Priority managepriority(JSONObject jsonObject) {
        Priority priority = new Priority();
        Boolean important = jsonObject.getBoolean("important");
        Boolean urgent = jsonObject.getBoolean("urgent");
        priority.setImportant(important);
        priority.setUrgent(urgent);
        return priority;
    }

    //EFFECTS: consume a JSONObject and return a status
    private Status managestatus(String string) {
        if (string.equals("DONE")) {
            return Status.DONE;
        } else if (string.equals("UP_NEXT")) {
            return Status.UP_NEXT;
        } else if (string.equals("IN_PROGRESS")) {
            return Status.IN_PROGRESS;
        } else {
            return Status.TODO;
        }
    }

    //MODIFIES: task
    //EFFECTS: add a set of tags into the task
    private void settags(Task t, Set<Tag> tags) {
        for (Tag tg : tags) {
            t.addTag(tg);
        }
    }

    //EFFECTS: consume a JSONObject and return a task based on the JSONObject
    private Task setTask(JSONObject taskobject) {
        Task t = new Task(taskobject.getString("description"));
        settags(t, managetags(taskobject.getJSONArray("tags")));
        if (taskobject.get("due-date").equals(JSONObject.NULL)) {
            t.setDueDate(null);
        } else {
            t.setDueDate(manageduedate(taskobject.getJSONObject("due-date")));
        }
        t.setPriority(managepriority(taskobject.getJSONObject("priority")));
        t.setStatus(managestatus(taskobject.getString("status")));
        return t;
    }


}
