package model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Ticket {
    @SerializedName("id")
    private int id;
    @SerializedName("due_date")
    private String due_date;
    @SerializedName("title")
    private String title;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;
    @SerializedName("submitter_email")
    private String submitter_email;
    @SerializedName("status")
    private int status;
    @SerializedName("on_hold")
    private boolean on_hold;
    @SerializedName("description")
    private String description;
    @SerializedName("resolution")
    private String resolution;
    @SerializedName("priority")
    private int priority;
    private String last_escalation;
    @SerializedName("secret_key")
    private String secret_key;
    @SerializedName("queue")
    private int queue;
    @SerializedName("assigned_to")
    private int assigned_to;
    @SerializedName("kbitem")
    private int kbitem;
    @SerializedName("merged_to")
    private int merged_to;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getSubmitter_email() {
        return submitter_email;
    }

    public void setSubmitter_email(String submitter_email) {
        this.submitter_email = submitter_email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isOn_hold() {
        return on_hold;
    }

    public void setOn_hold(boolean on_hold) {
        this.on_hold = on_hold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getLast_escalation() {
        return last_escalation;
    }

    public void setLast_escalation(String last_escalation) {
        this.last_escalation = last_escalation;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(int assigned_to) {
        this.assigned_to = assigned_to;
    }

    public int getKbitem() {
        return kbitem;
    }

    public void setKbitem(int kbitem) {
        this.kbitem = kbitem;
    }

    public int getMerged_to() {
        return merged_to;
    }

    public void setMerged_to(int merged_to) {
        this.merged_to = merged_to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return queue == ticket.queue && Objects.equals(title, ticket.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, queue);
    }
}
