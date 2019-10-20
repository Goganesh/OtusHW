package ru.otus.homework.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PhoneDataSet")
@Setter
@NoArgsConstructor
public class PhoneDataSet {


    private Long id;
    private String number;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }
}
