package userinterface.chat.chatroom;


import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import userinterface.chat.api.ChatCommands;
import userinterface.chat.api.HttpStatusUpdate;
import userinterface.chat.chatarea.ChatAreaController;
import userinterface.chat.commands.CommandsController;
import userinterface.chat.users.UsersListController;

import java.io.Closeable;
import java.io.IOException;

public class ChatRoomMainController implements Closeable{

    @FXML private VBox usersListComponent;
    @FXML private UsersListController usersListComponentController;
    @FXML private VBox actionCommandsComponent;
    @FXML private CommandsController actionCommandsComponentController;
    @FXML private GridPane chatAreaComponent;
    @FXML private ChatAreaController chatAreaComponentController;

    //private ChatAppMainController chatAppMainController;

    @FXML
    public void initialize() {
//        usersListComponentController.setHttpStatusUpdate(this);
//        actionCommandsComponentController.setChatCommands(this);
//        chatAreaComponentController.setHttpStatusUpdate(this);

        chatAreaComponentController.autoUpdatesProperty().bind(actionCommandsComponentController.autoUpdatesProperty());
        usersListComponentController.autoUpdatesProperty().bind(actionCommandsComponentController.autoUpdatesProperty());
    }

//    @Override
//    public void updateHttpLine(String line) {
//        chatAppMainController.updateHttpLine(line);
//    }

    @Override
    public void close() throws IOException {
        usersListComponentController.close();
        chatAreaComponentController.close();
    }

    public void setActive() {
        usersListComponentController.startListRefresher();
        chatAreaComponentController.startListRefresher();
    }

    public void setInActive() {
        try {
            usersListComponentController.close();
            chatAreaComponentController.close();
        } catch (Exception ignored) {}
    }

//    public void setChatAppMainController(ChatAppMainController chatAppMainController) {
//        this.chatAppMainController = chatAppMainController;
//    }

//    @Override
//    public void logout() {
//        chatAppMainController.switchToLogin();
//    }
}
