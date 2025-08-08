package model;

public class Project {

    private String id;
    private String name;
    private String status;

    public Project(String id, String name, String status) {
        setId(id);
        setName(name);
        setStatus(status);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        if (id == null) {
            this.id = "NULL";
        } else {
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null) {
            this.name = "NULL";
        } else {
            this.name = name;
        }
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        if (status == null) {
            this.status = "NULL";
        } else {
            this.status = status;
        }
    }

    @Override
    public String toString() {
        String res = String.format("%s | %s | %s", this.id, this.name, this.status);
        return res;
    }
}
