package com.hyeok.board.domain.repository;

import com.hyeok.board.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByTitleContaining(String keyword);
}

/*
레포지토리는 인터페이스로 정의, JpaRepository 인터페이스를 상속
JpaRepository
- 제네릭 타입에는 Entity 클래스와 PK의 타입을 명시
- 일반적으로 많이 사용하는 데이터 조작을 다루는 함수가 정의 -> CRUD작업이 편함.

findByTitleContaining()
-검색을 직접적으로 호출하는 메서드
-JpaRepository에서 메서드명의 By이후는 SQL의 where조건 절에 대응되는 것인데, 이렇게 Containing을 붙여주면
Like 검색이 됩니다.
- 해당 함수는 %{keyword}% 이렇게 표현이 됨.

StartsWith
- 검색어로 시작하는 Like 검색
- {keyword}%
EndsWith
- 검색어로 끝나는 Like 검색
- %{keyword}
IgnoreCase
- 대소문자 구분 없이 검색
Not
- 검색어를 포함하지 않는 검색

 */
