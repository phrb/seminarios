package br.usp.ime.seminarios;

public class Seminar {
    private String id;
    private String title;
    private String presenter;
    private String short_description;

    private boolean attended = false;

    public boolean isAttended() { return attended; }

    public void setAttended(boolean attended) { this.attended = attended; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
