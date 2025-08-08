package utils;

public enum ProjectStatus {

    PROGRESS("PROGRESS"), STOPPED("STOPPED");

    private final String value;

    ProjectStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
