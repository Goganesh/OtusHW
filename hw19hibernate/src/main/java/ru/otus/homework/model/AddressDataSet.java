package ru.otus.homework.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "AddressDataSet")
@Setter
@NoArgsConstructor
public class AddressDataSet {

    private Long id;
    private String street;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public Long getId() {
        return id;
    }
    @Column(name = "street")

    public String getStreet() {
        return street;
    }
    @OneToOne(mappedBy = "addressDataSet",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public AddressDataSet(String street) {
        this.street = street;
    }
}
