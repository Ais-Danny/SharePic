package top.aisdanny.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Pic {
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;

    private String src;

    public Pic(Integer id, Date time, String src) {
        this.id = id;
        this.time = time;
        this.src = src;
    }

    public Pic() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}