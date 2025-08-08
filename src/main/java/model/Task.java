package model;

import java.util.Date;

public class Task {
    private String id;
    private String name;
    private String description;
    private String status;
    private Date deadline;
    private Date createdAt;
    private String projectName;
    private String implementerEmail;

    public Task(String id, String name, String description, String status, Date deadline, Date createdAt, String projectName, String implementerEmail) {
        setId(id);
        setName(name);
        setDescription(description);
        setStatus(status);
        setDeadline(deadline);
        setCreatedAt(createdAt);
        setProjectName(projectName);
        setImplementerEmail(implementerEmail);
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

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        if (description == null) {
            this.description = "NULL";
        } else {
            this.description = description;
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

    public Date getDeadline() {
        return deadline;
    }

    private void setDeadline(Date deadline) {
        if (deadline == null) {
            throw new NullPointerException("Deadline is null, cannot be!");
        }
        this.deadline = deadline;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    private void setCreatedAt(Date createdAt) {
        if (createdAt == null) {
            throw new NullPointerException("Created at date cannot be null");
        }
        this.createdAt = createdAt;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        if (projectName == null) {
            this.projectName = "NULL";
        } else {
            this.projectName = projectName;
        }
    }

    public String getImplementerEmail() {
        return implementerEmail;
    }

    public void setImplementerEmail(String implementerEmail) {
        if (implementerEmail == null) {
            this.implementerEmail = "NULL";
        } else {
            this.implementerEmail = implementerEmail;
        }
    }

    @Override
    public String toString() {
        String res = String.format("%s | %s | %s | %s | %s | %s | %s | %s",
                this.id,
                this.name,
                this.description,
                this.status,
                this.deadline,
                this.createdAt,
                this.projectName,
                this.implementerEmail);

        return res;
    }
}
