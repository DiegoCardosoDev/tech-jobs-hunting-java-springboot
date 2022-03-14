package com.diego.techjobs.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Jobs implements Serializable {

    @Transient
    private final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long code;

    @Column(nullable = false, length = 130)
    private String name;

    @Column(nullable = false, length = 130)
    private String description;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy, HH:mm:ss")
    private LocalDateTime date;

    private Double salary;

    @OneToMany(mappedBy = "jobs", cascade = CascadeType.REMOVE)
    private List<Candidate> candidates;
}
