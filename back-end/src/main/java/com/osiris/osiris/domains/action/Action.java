package com.osiris.osiris.domains.action;

import javax.persistence.*;

import com.osiris.osiris.domains.schedule.Schedule;

@Entity
@Table(name = "action")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @Column(columnDefinition = "text")
    private String data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}