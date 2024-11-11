package com.hyeok.board.dto;

import com.hyeok.board.domain.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BoardEntity toEntity() {
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
        return boardEntity;
    }

    @Builder
    public BoardDto(Long id, String writer, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
/*
toEntity()
-필요한 엔티티는 이런식으로 추가
-dto에서 필요한 부분을 빌더패턴을 통해 엔티티로 만듭니다.

dto는 컨트롤러 <-> 서비스 <-> 레포지토리 간에 필요한 데이터를 캡슐화한 데이터 전달 객체
-서비스에서 레포지토리 메서드를 호출할 때, 엔티티를 전달한 이유는 JpaRepository에 정의된 함수들은 미리 정의되어 있기 때문
-그래서 엔티티를 전달할 수 밖에 없었는데, 요점은 각 계층에서 필요한 객체전달은 엔티티 객체가 아닌 dto 객체를 통해 주고받는것이 좋음.
 */