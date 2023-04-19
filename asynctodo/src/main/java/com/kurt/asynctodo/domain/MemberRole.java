package com.kurt.asynctodo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@SequenceGenerator(name = "MEMBER_ROLE_SEQ_GEN", sequenceName = "MEMBER_ROLE_SEQ", allocationSize = 1)
public class MemberRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_ROLE_SEQ_GEN")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @JsonBackReference
    @ManyToOne
    private Member member;

    @Override
    public String getAuthority() {
        return this.role.getAuthority();
    }

    public static MemberRole of(RoleType role, Member member) {
        return new MemberRole(
                null,
                role,
                member
        );
    }
}
