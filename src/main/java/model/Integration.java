package model;

public class Integration {

    private String id;
    private String name;
    private String url;
    private String projectName;

    public Integration(String id, String name, String url, String projectName) {
        setId(id);
        setName(name);
        setUrl(url);
        setProjectName(projectName);
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

    public String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        if (url == null) {
            this.url = "NULL";
        } else {
            this.url = url;
        }
    }

    public String getProjectName() {
        return this.projectName;
    }

    private void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        String res = String.format("%s | %s | %s | %s", this.id, this.name, this.url, this.projectName);
        return res;
    }
}
