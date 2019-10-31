package ru.otus.homework.model;

import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
@Setter
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private int age;
    private AddressDataSet addressDataSet;
    private List<PhoneDataSet> phoneDataSet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "age")
    public int getAge() {
        return age;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    public AddressDataSet getAddressDataSet() {
        return addressDataSet;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<PhoneDataSet> getPhoneDataSet() {
        return phoneDataSet;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(addressDataSet, user.addressDataSet) &&
                Objects.equals(phoneDataSet, user.phoneDataSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, addressDataSet, phoneDataSet);
    }
}
