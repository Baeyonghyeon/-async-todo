package com.kurt.asynctodo.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kurt.asynctodo.security.dto.MemberDetails;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@SequenceGenerator(name = "MEMBER_SEQ_GEN", sequenceName = "MEMBER_SEQ", allocationSize = 1)
public class Member extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GEN")
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<MemberRole> memberRoles = new ArrayList<>();

    public static Member of(MemberDetails memberDetails){
        return new Member(
                memberDetails.memberId(),
                memberDetails.username(),
                memberDetails.password(),
                null
        );
    }

    public void addMemberRole(MemberRole memberRole) {
        memberRoles.add(memberRole);
    }
}
