package pl.agh.edu.chat.common;

public enum MessageType {
    CONNECT("C"),
    DISCONNECT("D"),
    MESSAGE("M"),
    UNKNOWN("UN"),
    EMPTY("E");

    private final String typeShortcut;

    MessageType(String typeShortcut) {
        this.typeShortcut = typeShortcut;
    }

    public String getTypeShortcut() {
        return typeShortcut;
    }

    public static MessageType shortcutToMessageType(String typeShortcut) {
        return switch (typeShortcut) {
            case "C" -> CONNECT;
            case "D" -> DISCONNECT;
            case "M" -> MESSAGE;
            case "E" -> EMPTY;
            default -> UNKNOWN;
        };
    }
}
