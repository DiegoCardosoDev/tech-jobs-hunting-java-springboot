package com.diego.techjobs.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;


import javax.validation.constraints.NotEmpty;

@Entity
public class JobsOportunity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;

    @NotEmpty
    @Column(length = 130)
    private String name;

    @NotEmpty
    @Column(length = 255)
    private String description;

    @NotEmpty
    private String date;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dateCreate;

    @NotEmpty
    private String salary;

    @OneToMany(mappedBy = "jobsOportunity", cascade = CascadeType.REMOVE)
    private List<Candidate> candidates;

    public long getCode() {
        return code;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public List<Candidate> getCandidatos() {
        return candidates;
    }

    public void setCandidatos(List<Candidate> candidates) {
        this.candidates = candidates;
    }


}
