package models;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

public class Ticket {
    private final int id = new Random().nextInt(500000); // произвольный айди
    private String queue; // очередь
    private String title; // название проблемы
    private String description; // описание проблемы
    private String priority; // приоритет
    private LocalDateTime date; // дата возникновения проблемы
    private File file; // файл
    private String mail; // почта


    public Ticket(){
        queue = "Django Helpdesk";
        title = "problem" + id;
        description = "Description of " + title;
        priority = "3. Normal";
        date = LocalDateTime.now();
        file = new File("images/Screenshot.png");
        mail = "email@gmail.com";
    }

    public String getId() {
        return String.valueOf(id);
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getStringDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return queue.equals(ticket.queue) &&
                Objects.equals(title, ticket.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, queue);
    }
}
