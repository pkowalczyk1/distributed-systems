package pl.agh.edu.chat.common;

public record Message(MessageType messageType, String messageContent, Integer senderId, Integer receiverId) {

    public static final Message EMPTY = new Message(MessageType.EMPTY, "", -1, -1);
}
