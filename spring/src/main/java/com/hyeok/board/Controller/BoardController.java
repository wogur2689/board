package com.hyeok.board.Controller;

import com.hyeok.board.dto.BoardDto;
import com.hyeok.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;

    /* 게시글 목록*/
    @GetMapping("/")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        //Model 객체를 통해 View에 데이터 전달.
        return "board/list.html";
    }
    /*
        @RequestParam(value = "page", defaultValue = "1") Integer pageNum
        page 이름으로 넘어오면 파라미터를 받아주고, 없으면 기본 값으로 1을 설정합니다.
        페이지 번호는 서비스 계층의 getPageList()함수로 넘겨줍니다.
     */

    @GetMapping("/post")
    public String write() {
        return "board/write.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);
        //dto는 Controller와 Service 사이에서 데이터를 주고 받는 객체.
        return "redirect:/";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);
        //상세조회 페이지
        model.addAttribute("boardDto", boardDTO);
        return "board/detail.html";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);
        //수정 페이지
        model.addAttribute("boardDto", boardDTO);
        return "board/update.html";
    }

    @PutMapping ("/post/edit/{no}")
    public String update(BoardDto boardDto) {
        boardService.savePost(boardDto);
        //수정
        return "redirect:/";
    }

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);
        //삭제
        return "redirect:/";
    }

    @GetMapping("/board/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "board/list.html";
    }
}
/*
  URL을 매핑하고, 비즈니스 로직 함수를 호출하여 view에 뿌려주는 역할을 하는 컨트롤러

@Controller
컨트롤러임을 명시하는 어노테이션, MVC에서 컨트롤러로 명시된 클래스의 메서드들은 반환값으로 템플린 경로 작성 or redirect해야함.
템플릿 경로는 templates 패키지를 기준으로 한 상대경로.

@AllArgsConstructor
Bean 주입 방식과 관련있고, 생성자로 Bean 객체를 받는 방식을 해결해주는 어노테이션
BoardService 객체를 주입 받을때 @Autowired 같은 특별한 어노테이션 부여 X

@GetMapping("/post/{no}")
@PathVariable("no") Long no
유동적으로 변하는 Path Variable를 처리하는 방법
URI매핑하는 부분에서 {변수} 처리를 해주면, 메서드 파라미터로 @PathVariable("변수")이렇게 받을 수 있음.

update()
게시글 추가에서 사용하는 boardService.savePost()메서드를 같이 사용하고 있음.
 */
