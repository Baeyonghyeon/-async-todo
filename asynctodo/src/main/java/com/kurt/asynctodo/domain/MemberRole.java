package com.kurt.asynctodo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@SequenceGenerator(name="MEMBER_ROLE_SEQ_GEN", sequenceName="MEMBER_ROLE_SEQ", allocationSize=1)
public class MemberRole {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="MEMBER_ROLE_SEQ_GEN")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @JsonBackReference
    @ManyToOne
    private Member member;

}
