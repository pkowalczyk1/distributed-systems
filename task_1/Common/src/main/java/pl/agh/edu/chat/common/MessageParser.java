package pl.agh.edu.chat.common;

import org.apache.commons.lang3.StringUtils;

public final class MessageParser {

    private MessageParser() {
    }

    public static String messageToString(Message message) {
        return message.messageType().getTypeShortcut() + ";" +
                message.receiverId() + ";" +
                message.senderId() + ";" +
                message.messageContent();
    }

    public static Message stringToMessage(String messageString) {
        if (StringUtils.isBlank(messageString)) {
            return Message.EMPTY;
        }

        String[] splitMessage = StringUtils.split(messageString, ";");
        MessageType messageType = MessageType.shortcutToMessageType(splitMessage[0]);
        int receiverId = Integer.parseInt(splitMessage[1]);
        int senderId = Integer.parseInt(splitMessage[2]);
        String messageContent = splitMessage[3];
        return new Message(messageType, messageContent, senderId, receiverId);
    }
}
