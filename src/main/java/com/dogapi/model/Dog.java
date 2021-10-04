package com.dogapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "dog_dogsubtye",
            joinColumns = @JoinColumn(name = "dog_id"),
            inverseJoinColumns = @JoinColumn(name = "dogsubtype_id"))
    private List<DogSubType> dogSubTypes;

}
