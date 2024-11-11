package com.hyeok.board.service;

import com.hyeok.board.domain.entity.BoardEntity;
import com.hyeok.board.domain.repository.BoardRepository;
import com.hyeok.board.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor //Repository를 주입하기 위해 사용.
@Service //서비스 계층임을 명시하는 어노테이션.
public class BoardService {
    private BoardRepository boardRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 5; //블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 4; //한 페이지에 존재하는 게시글 수


    @Transactional
    public List<BoardDto> getBoardlist(Integer pageNum) {
        //Controller와 Service간에 데이터 전달은 dto 객체로 하기 위해, Repository에서 가져온 Entity를 반복문을 통해 dto로 변환하는 작업
        Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));;

        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for( BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(boardEntity));
        }
        return boardDtoList;
    }

    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        //총 게시글 개수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());
        //총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));
        //현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum +BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;
        //페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum -2;
        //페이지 번호 할당
        for(int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }

    @Transactional //선언적 트랜잭션이라 부르며, 트랜잭션을 적용하는 어노테이션 (필수사용 아님/ 필요한 부분에만 적용)
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
        //save() JpaRepository에 정의된 메서드로 DB에 INSERT,  UPDATE를 담당.
        //매개변수는 Entity
    }

    @Transactional
    public BoardDto getPost(Long id) {
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        BoardDto boardDTO = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();

        return boardDTO;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public List<BoardDto> searchPosts(String keyword) {
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(boardEntity));
        }

        return boardDtoList;
    }

    private BoardDto convertEntityToDto(BoardEntity boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
}
/*
findById()
PK 값을 where 조건으로 하여, 데이터를 가져오기 위한 메서드. JpaRepository 인터페이스에 정의
반환 값은 Optional 타입, 엔티티를 쏙 빼오려면 boardEntityWrapper.get(); 이렇게 get() 메서드를 사용해 가져옴.

deleteById()
PK 값을 where 조건으로 하여, 데이터를 삭제하기 위한 메서드. JpaRepository 인터페이스에 정의.

searchPosts()
- Repository에서 검색 결과를 받아와 비즈니스 로직을 실행하는 함수
- Controller <--> Service 간에는 Dto 객체로 전달하는 것이 좋으므로 이와 관련된 로직만 있음

convertEntityToDto()
- 시리즈의 이전 게시물에서 Entity를 Dto로 변환하는 작업이 중복해서 발생했었는데, 이를 함수로 처리하도록 개선

boardRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));
- repository의 find()관련 메소드를 호출할 때 Pageable 인터페이스를 구현한 클래스 PageRequest.of()를 전달하면 페이징을 할 수 있음.
- 첫 번째 인자 limit : "현재 페이지 번호 - 1"을 계산한 값이며, 실제 페이지 번호와 SQL 조회시 사용되는 limit은 다르기 때문.
- 두번째 인자 offset : 몇 개를 가져올 것인가
- 세번째 인자 정렬방식 : createDate 컬럼을 기준으로 오름차순으로 정렬하여 가져옴.
- 반환된 Page 객체의 getContent()메서드를 호출하면, 엔티티를 리스트로 꺼낼 수 있습니다.
- getBoardCount() 메서드는 신규로 추가한 메서드이며, 프론트에 노출시킬 페이지 번호 리스트를 계산하는 로직입니다.
  - 하나의 페이지에는 4개의 게시글을 가져옴.
  - 총 5개의 번호를 노출한다.
  - 번호를 5개 채우지 못하면(= 게시글이 20개가 안되면), 존재하는 번호까지만 노출
  - UI를 어떻게 구현하냐에 따라 페이징 로직이 조금씩 다른데요, 제가 구현한 방식은 다음과 같음

 페이징 알고리즘을 구현하는 부분이 복잡해서 그렇지, JPA에서 페이징하여 조회하는 부분은 간단합니다.
 페이징은 각자가 원하는 UI가 있으므로 상황에 맞게 구현하시면 되고, 참고만 하시길 바랍니다.
   - 다양한 방법
      - 최초 첫 번째 블럭 페이지 번호: 1 ~ 5
      - 오른쪽 버튼 클릭시, 두 번째 블럭 페이지 번호 : 6 ~ 10
      - 블럭 내의 번호 클릭 시, 번호 변동없음
      - 한 블럭에 페이지 번호는 5개로 고정하고, 양 끝에 화살표 버튼을 위치시켜 5개가 한 셋트씩 움직이도록 할 수도 있음.
 */
