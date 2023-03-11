package pl.agh.edu.chat.client;

import pl.agh.edu.chat.common.Message;
import pl.agh.edu.chat.common.MessageType;

import static pl.agh.edu.chat.common.MessageType.EMPTY;

public abstract class MessageHandler implements Runnable {

    protected void handleMessage(Message message, int clientId) {
        if (!EMPTY.equals(message.messageType()) && message.senderId() != clientId) {
            if (message.messageType() == MessageType.MESSAGE) {
                System.out.println("New message from user " + message.senderId() + ":\n " + message.messageContent());
            } else {
                System.out.println("Received unknown message type or this message type shouldn't be received");
            }
        }
    }
}
