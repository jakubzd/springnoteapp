package com.example.springproject.role;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "role_name"))
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role_name")
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
