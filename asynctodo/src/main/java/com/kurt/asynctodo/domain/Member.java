package com.kurt.asynctodo.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kurt.asynctodo.security.dto.MemberDetails;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Column(nullable = false, length = 60)
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<MemberRole> memberRoles;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Memo> memo;

    public static Member of(MemberDetails memberDetails, PasswordEncoder passwordEncoder){
        return new Member(
                memberDetails.memberId(),
                memberDetails.username(),
                passwordEncoder.encode(memberDetails.password()),
                new ArrayList<>(),
                null
        );
    }

    public static Member of(Long id) {
        return new Member(
                id,
                null,
                null,
                null,
                null
        );
    }

    public void addMemberRole(MemberRole memberRole) {
        memberRoles.add(memberRole);
    }
}
