package com.hyeok.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
//access는 생성자의 접근 권한을 설정하는 속성, protected BoardEntity() {}와 동일.
//파라미터가 없는 기본 생성자를 추가하는 어노테이션(JPA 사용을 위해 기본생성자 생성은 필수)
//Entity생성을 외부에서 할 필요가 없기 때문에 Protected로 함.
@Getter
//모든 필드에 getter을 자동생성 해주는 어노테이션
@Entity
// 객체를 테이블과 매핑 할 엔티티라고 JPA에게 알려주는 역할을 하는 어노테이션
// @Entity가 붙은 클래스는 JPA가 관리하며, 이를 엔티티 클래스라 함.
@Table(name = "board")
// 엔티티 클래스와 매핑되는 테이블 정보를 명시하는 어노테이션
// name 속성으로 테이블 명을 작성 가능, 생략시 엔티티 이름이 테이블명으로 자동 매핑
public class BoardEntity extends TimeEntity{

    @Id //테이블의 기본키임을 명시. Id를 대체키로 사용하는것이 좋고, Long타입을 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키로 대체키를 사용할때, 기본키 값 생성 전략을 명시
    private Long id;

    @Column(length = 10, nullable = false) //컬럼을 매핑하는 어노테이션.
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder //빌더패턴 클래스를 생성해주는 어노테이션, @Setter사용대신 사용하면 안정성이 보장됨.
    public BoardEntity(Long id, String title, String content, String writer) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
/*
DB 테이블과 매핑되는 객체를 정의하는 Entity를 구현
-Entity 클래스는 테이블과 관련이 있음.
-비즈니스 로직은 Entity를 기준으로 돌아가기 때문에 Entity를 Request, Response 용도로 사용하지 않기.
-이런 이유로 데이터 전달목적을 갖는 dto 클래스를 정의하여 사용.
 */
