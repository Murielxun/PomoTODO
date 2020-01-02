package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsers.TagParser;
import parsers.exceptions.ParsingException;

import java.util.*;

import static model.Status.IN_PROGRESS;
import static model.Status.TODO;
import static org.junit.jupiter.api.Assertions.*;

public class TestTask {

    private Task testTask;
    private Priority testPriority;
    private Status testStatus = TODO;
    private DueDate testDueDate;
    private Date testDate;
    private Calendar testCalendar;
    private Set tags;

    @BeforeEach
    public void setUp() {
        testTask = new Task("tomorrow");
        testDate = new Date();
        testDueDate = new DueDate(testDate);
        testCalendar = Calendar.getInstance();
        testPriority = new Priority(4);
        tags = new HashSet();
    }

    @Test
    public void testConstructor() {
//        assertEquals("tomorrow", testTask.getDescription());
        try {
            testTask.setDescription("today");
        } catch (EmptyStringException e) {
            fail("Wasn't expecting exception.");
        }
    }

    @Test
    public void testConstructorException1() {
        try {
            testTask = new Task("");
            fail("Wasn't expecting to reach here.");
        } catch (EmptyStringException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testConstructorException2() {
        try {
            testTask = new Task(null);
            fail("Wasn't expecting to reach here.");
        } catch (EmptyStringException e) {
            System.out.println("Catch it.");
        }
    }


    @Test
    public void testAddTags() {
        assertFalse(testTask.containsTag("shopping"));
        testTask.addTag("shopping");
        assertTrue(testTask.containsTag("shopping"));
        assertFalse(testTask.containsTag("eating"));
        testTask.addTag("eating");
        assertTrue(testTask.containsTag("eating"));
    }

    @Test
    public void testAddSameTags() {
        assertFalse(testTask.containsTag("shopping"));
        testTask.addTag("shopping");
        assertTrue(testTask.containsTag("shopping"));
        testTask.addTag("shopping");
        assertTrue(testTask.containsTag("shopping"));
    }

    @Test
    public void testAddNoExceptions() {
        try {
            testTask.addTag("shopping");
        } catch (EmptyStringException e) {
            fail("Wasn't expecting exception.");
        }
    }

    @Test
    public void testAddException1() {
        try {
            testTask.addTag("");
            fail("Wasn't expecting to reach here.");
        } catch (EmptyStringException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testAddException2() {
        try {
            testTask.addTag(new Tag(null));
            fail("Wasn't expecting to reach here.");
        } catch (EmptyStringException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testRemoveTags() {
        testTask.addTag("shopping");
        assertTrue(testTask.containsTag("shopping"));
        testTask.removeTag("shopping");
        assertFalse(testTask.containsTag("shopping"));
    }

    @Test
    public void testRemoveNoException() {
        testTask.addTag("shopping");
        try {
            testTask.removeTag("shopping");
        } catch (EmptyStringException e) {
            fail("Wasn't expecting exception.");
        }
    }

    @Test
    public void testRemoveException1() {
        testTask.addTag("shopping");
        try {
            testTask.removeTag("");
            fail("Wasn't expecting to reach here.");
        } catch (EmptyStringException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testRemoveException2() {
        testTask.addTag("shopping");
        try {
            testTask.removeTag(new Tag(null));
            fail("Wasn't expecting to reach here.");
        } catch (EmptyStringException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testSetPriority() {
        try {
            testTask.setPriority(testPriority);
        } catch (NullArgumentException e) {
            fail("Wasn't expecting exception");
        }
        assertFalse(testTask.getPriority().isImportant());
        assertFalse(testTask.getPriority().isUrgent());
    }


    @Test
    public void testPriorityException() {
        try {
            testTask.setPriority(null);
            fail("Wasn't expecting to reach here.");
        } catch (NullArgumentException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testSetStatus() {
        try {
            testTask.setStatus(testStatus);
        } catch (NullArgumentException e) {
            fail("Wasn't expecting exception");
        }
        assertEquals(TODO, testTask.getStatus());
    }

    @Test
    public void testStatusException() {
        try {
            testTask.setStatus(null);
            fail("Wasn't expecting to reach here.");
        } catch (NullArgumentException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testSetDescription() {
        try {
            testTask.setDescription("Read something");
        } catch (EmptyStringException e) {
            fail("Wasn't expecting exception.");
        }
//        assertEquals("Read something", testTask.getDescription());
    }

    @Test
    public void testDescriptionException() {
        try {
            testTask.setDescription(null);
            fail("Wasn't expecting to reach here.");
        } catch (EmptyStringException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testDescriptionException2() {
        try {
            testTask.setDescription("");
            fail("Wasn't expecting to reach here.");
        } catch (EmptyStringException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testProgress() {
        assertEquals(testTask.getProgress(),0);
        testTask.setProgress(30);
        assertEquals(testTask.getProgress(),30);
    }

    @Test
    public void testTag() {
        Tag t = new Tag ("1");
        assertTrue(t.equals(new Tag("1")));
        assertFalse(t.equals(new Tag("2")));
        System.out.println(t.getName());
        t.addTask(testTask);
        t.getTasks();
        System.out.println(t.toString());
        t.removeTask(testTask);
        try {
            t.containsTask(null);
        } catch (NullArgumentException e) {
            System.out.println("catchit.");
        }
        Tag tg = null;
        try {
            testTask.containsTag(tg);
        } catch (NullArgumentException e) {
            System.out.println("c");
        }

        try {
            testTask.containsTag("nothing");
        } catch (NullArgumentException e) {
            System.out.println("c");
        }
    }

    @Test
    public void testTask() {
        testTask.getTags();
        testTask.removeTag(new Tag("nothing"));

        DueDate d = new DueDate(new Date());
        testTask.setDueDate(d);
        System.out.println(testTask.getDueDate().toString());
        try {
            testTask.containsTag("");
        } catch (EmptyStringException e) {
            System.out.println("catchit");
        }

        try {
            String t = null;
            testTask.containsTag(t);
        } catch (EmptyStringException e) {
            System.out.println("catchit");
        }

        System.out.println(testTask.toString());
        try {
            testTask.setProgress(-1);
        } catch (InvalidProgressException e) {
            System.out.println("catchit");
        }

        try {
            testTask.setEstimatedTimeToComplete(-1);
        } catch (NegativeInputException e) {
            System.out.println("catchit");
        }

        Task testTask2 = new Task("tomorrow");
        testTask2.setDueDate(d);
        assertTrue(testTask.equals(testTask2));

        try {
            Tag t = null;
            testTask.containsTag(t);
        } catch (NullArgumentException e) {
            System.out.println("catch it.");
        }

    }
}