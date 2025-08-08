package utils;

public enum TaskStatus {

    DONE("DONE"), UNDONE("UNDONE");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
