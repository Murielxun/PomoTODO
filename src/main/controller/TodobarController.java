package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.ListView;
import ui.PomoTodoApp;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ui.PomoTodoApp.setScene;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todobarOptionPopUpFxmlFile = new File(todoOptionsPopUpFXML);
    private File todoActionPopUpFxmlFile = new File(todoActionsPopUpFXML);

    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup todoOptionPopUp;
    private JFXPopup todoActionPopUp;
    private Task task;

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadtodoOptionPopUp();
        loadtodoOptionPopUpActionListener();
        loadtodoActionPopUp();
        loadtodoActionPopUpActionListener();
    }

    // EFFECTS: load todooption selector pop up (edit, delete)
    private void loadtodoOptionPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todobarOptionPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodobarOptionPopUpController());
            todoOptionPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: load todoaction selector pop up (todos, inprogress, upnext, done, pomodoro)
    private void loadtodoActionPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodobarActionPopUpController());
            todoActionPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show actions pop up when its icon is clicked
    private void loadtodoActionPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoActionPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // EFFECTS: show options pop up when its icon is clicked
    private void loadtodoOptionPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoOptionPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // Inner class: todobar action pop up controller
    class TodobarActionPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                default:
                    Logger.log("TodobarActionsPopUpController", "No action is implemented for the selected option");
            }
            todoActionPopUp.hide();
        }
    }

    // Inner class: todobar option pop up controller
    class TodobarOptionPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarOptionsPopUpController", "Edit the task.");
                    setScene(new EditTask(task));
                    break;
                case 1:
                    Logger.log("TodobarOptionsPopUpController", "Delete the task.");
                    PomoTodoApp.getTasks().remove(task);
                    PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
                    break;
                default:
                    Logger.log("TodobarOptionsPopUpController", "No action is implemented for the selected option");
            }
            todoOptionPopUp.hide();
        }
    }
}
