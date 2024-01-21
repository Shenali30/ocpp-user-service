package com.poc.techvoice.userservice.domain.entities;

import com.poc.techvoice.userservice.domain.enums.Role;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 6)
    private Role role;
    private String activeSessionId;
    private String name;
    private String profileDescription;
    private String countryOfOrigin;

}
