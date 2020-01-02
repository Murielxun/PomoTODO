package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {

    private Project testProject;
    private List<Task> taskList;
    private Task t1, t2, t3;


    @BeforeEach
    public void setUp() {
        testProject = new Project("210project");
        t1 = new Task("structure");
        t2 = new Task("test");
        t3 = new Task("implement");
        taskList = new ArrayList<Task>();
    }

    @Test
    public void testConstructor() {
        assertEquals("210project", testProject.getDescription());
        try {
            Project p = new Project("1");
        } catch (EmptyStringException e) {
            fail("Wasn't expecting exceptions.");
        }
    }

    @Test
    public void testConstructorException() {
        try {
            Project p = new Project(null);
            fail("Wasn't expecting to reach here.");
        } catch (EmptyStringException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testConstructorException2() {
        try {
            Project p = new Project("");
            fail("Wasn't expecting to reach here.");
        } catch (EmptyStringException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testAdd() {
        assertEquals(testProject.getNumberOfTasks(), 0);
        testProject.add(t1);
        assertEquals(testProject.getNumberOfTasks(), 1);
    }

    @Test
    public void testAddNoneException() {
        try {
            testProject.add(t1);
        } catch (NullArgumentException e) {
            fail("Wasn't expecting exceptions.");
        }
    }

    @Test
    public void testAddException() {
        try {
            testProject.add(null);
            fail("Wasn't expecting to reach here");
        } catch (NullArgumentException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testRemove() {
        testProject.add(t1);
        testProject.add(t2);
        assertTrue(testProject.contains(t1));
        testProject.remove(t1);
        assertFalse(testProject.contains(t1));
        testProject.remove(t2);
        assertFalse(testProject.contains(t2));
    }

    @Test
    public void testRemoveNoneException() {
        testProject.add(t1);
        try {
            testProject.remove(t1);
        } catch (NullArgumentException e) {
            fail("Wasn't expecting exceptions");
        }
    }

    @Test
    public void testRemoveException() {
        testProject.add(t1);
        try {
            testProject.remove(null);
            fail("Wasn't expecting to reach here");
        } catch (NullArgumentException e) {
            System.out.println("Catch it.");
        }
    }


    @Test
    public void testDescription() {
        assertEquals(testProject.getDescription(), "210project");
    }


    @Test
    public void testProgressHalf() {
        t1.setProgress(100);
        t2.setProgress(60);
        testProject.add(t1);
        testProject.add(t2);
        assertEquals(80, testProject.getProgress());
        assertFalse(testProject.isCompleted());
    }

    //
    @Test
    public void testProgressDone() {
        t1.setProgress(100);
        t2.setProgress(100);
        testProject.add(t1);
        testProject.add(t2);
        assertEquals(100, testProject.getProgress());
        assertTrue(testProject.isCompleted());
    }

    //
    @Test
    public void testProgressNotStart() {
        t1.setStatus(Status.IN_PROGRESS);
        t2.setStatus(Status.UP_NEXT);
        testProject.add(t1);
        testProject.add(t2);
        assertEquals(0, testProject.getProgress());
        assertFalse(testProject.isCompleted());
    }

    //
    @Test
    public void testProgressNothing() {
        assertEquals(0, testProject.getProgress());
        assertFalse(testProject.isCompleted());
        assertEquals(0,testProject.getEstimatedTimeToComplete());
    }

    //
    @Test
    public void testProgressRound() {
        t1.setProgress(30);
        t1.setEstimatedTimeToComplete(1);
        t2.setEstimatedTimeToComplete(2);
        t3.setEstimatedTimeToComplete(3);
        t2.setProgress(100);
        t3.setProgress(30);
        testProject.add(t1);
        testProject.add(t2);
        testProject.add(t3);
        assertEquals(53, testProject.getProgress());
        assertEquals(6,testProject.getEstimatedTimeToComplete());
        assertFalse(testProject.isCompleted());
    }


    //
    @Test
    public void testNumberOfTasks() {
        testProject.add(t1);
        testProject.add(t2);
        assertEquals(2, testProject.getNumberOfTasks());
    }

    @Test
    public void testContainsNoneException() {
        testProject.add(t1);
        try {
            testProject.contains(t1);
        } catch (NullArgumentException e) {
            fail("Wasn't expecting exceptions.");
        }
    }

    @Test
    public void testContainsException() {
        testProject.add(t1);
        try {
            testProject.contains(null);
            fail("Wasn't expecting to reach here.");
        } catch (NullArgumentException e) {
            System.out.println("Catch it.");
        }
    }

    @Test
    public void testaddself() {
        testProject.add(testProject);
        assertEquals(testProject.getNumberOfTasks(),0);
        testProject.add(t1);
        testProject.add(t1);
    }

    @Test
    public void testAddProject() {
        Project pj = new Project("1");
        t1.setEstimatedTimeToComplete(3);
        t2.setEstimatedTimeToComplete(3);
        t1.setProgress(100);
        t2.setProgress(20);
        t3.setEstimatedTimeToComplete(3);
        pj.add(t1);
        pj.add(t2);
        testProject.add(pj);
        testProject.add(t3);
        assertEquals(testProject.getEstimatedTimeToComplete(),9);
        assertEquals(testProject.getProgress(),30);
    }


    @Test
    public void testIterator() {
        t1.setPriority(new Priority(4));
        t1.setDescription("5");
        t2.setPriority(new Priority(4));
        t2.setDescription("6");
        t3.setPriority(new Priority(3));
        t3.setDescription("3");
        Task t4 = new Task("4");
        t4.setPriority(new Priority(3));
        Project pj = new Project("2");
        pj.setPriority(new Priority(2));
        Task t5 = new Task("1");
        t5.setPriority(new Priority(1));
        testProject.add(t3);
        testProject.add(pj);
        testProject.add(t1);
        testProject.add(t5);
        testProject.add(t2);
        testProject.add(t4);
        for (Todo t: testProject) {
            System.out.println(t.getDescription());
        }
    }


    @Test
    public void testIterator2() {
        t1.setPriority(new Priority(4));
        t1.setDescription("5");
        t2.setPriority(new Priority(4));
        t2.setDescription("6");
        t3.setPriority(new Priority(3));
        t3.setDescription("3");
        Task t4 = new Task("4");
        t4.setPriority(new Priority(3));
        Project pj = new Project("2");
        pj.setPriority(new Priority(2));
        Task t5 = new Task("1");
        t5.setPriority(new Priority(1));
        testProject.add(pj);
        testProject.add(t1);
        testProject.add(t5);
        testProject.add(t2);
        testProject.add(t4);
        for (Todo t: testProject) {
            System.out.println(t.getDescription());
        }
    }

    @Test
    public void testIterator3() {
        t1.setPriority(new Priority(4));
        t1.setDescription("5");
        t2.setPriority(new Priority(4));
        t2.setDescription("6");
        t3.setPriority(new Priority(3));
        t3.setDescription("3");
        Task t4 = new Task("4");
        t4.setPriority(new Priority(3));
        Project pj = new Project("2");
        pj.setPriority(new Priority(2));
        Task t5 = new Task("1");
        t5.setPriority(new Priority(1));
        testProject.add(t3);
        testProject.add(pj);
        testProject.add(t1);
        testProject.add(t2);
        testProject.add(t4);
        for (Todo t: testProject) {
            System.out.println(t.getDescription());
        }
    }

    @Test
    public void testIteratorNoElement() {
        testProject = new Project("1");
        try {
            System.out.println(testProject.iterator().next().getDescription());
        } catch (NoSuchElementException e) {
            System.out.println("catchit.");
        }

        try {
            testProject.getTasks();
        } catch (UnsupportedOperationException e) {
            System.out.println("c");
        }
    }

    @Test
    public void testTodo() {
        Todo t = new Task("1");
        System.out.println(t.getDescription());
    }

}