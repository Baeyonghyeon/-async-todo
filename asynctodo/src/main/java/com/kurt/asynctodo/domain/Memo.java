package com.kurt.asynctodo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@SequenceGenerator(
        name="MEMO_SEQ_GEN",      //시퀀스 제너레이터 이름
        sequenceName="MEMO_SEQ",  //시퀀스 이름
        allocationSize=1            //메모리를 통해 할당할 범위 사이즈
)
public class Memo extends AuditingFields {

    @Id
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
            generator="MEMO_SEQ_GEN" //식별자 생성기를 설정해놓은  MEMBER_SEQ_GEN으로 설정
    )
    private Long id;

    @ManyToOne
    private Member member;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    public static Memo of(Long id, Member member, String title, String content) {
        return new Memo(id, member, title, content);
    }

    public void writer(Member member) {
        this.member = member;
    }

    public void updateTitle(String s) {
        this.title = s;
    }

    public void updateContent(String s) {
        this.content = s;

    }
}
