package model;

public class User {

    private String id;
    private String email;
    private String name;
    private String status;
    private String salary;

    public User(String id, String email, String name, String status, String salary) {
        setId(id);
        setEmail(email);
        setName(name);
        setStatus(status);
        setSalary(salary);
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSalary() {
        return salary;
    }

    private void setId(String id) {
        if (id == null) {
            this.id = "NULL";
        } else {
            this.id = id;
        }
    }

    private void setEmail(String email) {
        if (email == null) {
            this.email = "NULL";
        } else {
            this.email = email;
        }
    }

    private void setName(String name) {
        if (name == null) {
            this.name = "NULL";
        } else {
            this.name = name;
        }
    }

    private void setStatus(String status) {
        if (status == null) {
            this.status = "NULL";
        } else {
            this.status = status;
        }
    }

    private void setSalary(String salary) {
        if (salary == null) {
            this.salary = "NULL";
        } else {
            this.salary = salary;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.id)
                .append(" | ")
                .append(this.email)
                .append(" | ")
                .append(this.name)
                .append(" | ")
                .append(this.status)
                .append(" | ")
                .append(this.salary);

        return sb.toString();
    }
}
