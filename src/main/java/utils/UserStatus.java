package utils;

public enum UserStatus {
    WORKING("WORKING"), CHILLING("CHILLING");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
