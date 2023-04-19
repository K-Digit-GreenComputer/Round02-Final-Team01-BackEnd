package shop.donutmarket.donut.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import shop.donutmarket.donut.domain.board.dto.BoardReq.BoardSaveReqDTO;
import shop.donutmarket.donut.domain.board.dto.BoardResp.BoardSaveRespDTO;
import shop.donutmarket.donut.domain.board.model.Board;
import shop.donutmarket.donut.domain.board.model.Event;
import shop.donutmarket.donut.domain.board.repository.BoardRepository;
import shop.donutmarket.donut.domain.board.repository.EventRepository;
import shop.donutmarket.donut.domain.board.repository.TagRepository;
import shop.donutmarket.donut.domain.board.service.BoardService;
import shop.donutmarket.donut.domain.mycategory.CategoryConst;
import shop.donutmarket.donut.domain.review.RateConst;
import shop.donutmarket.donut.domain.user.StatusCodeConst;
import shop.donutmarket.donut.domain.user.model.User;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;
    
    @Mock
    private BoardRepository boardRepository;
    
    @Mock
    private EventRepository eventRepository; 
    
    @Mock
    private TagRepository tagRepository;

    @Spy
    ObjectMapper om;

    @Test
    public void 공고작성_test() throws Exception {
        // given
        User user1 = User.builder().id(1L).username("ssar").password("1234")
        .email("ssar@ssar").name("ssar").rate(new RateConst()).role("user")
        .statusCode(new StatusCodeConst()).createdAt(LocalDateTime.now()).build();

        List<String> comment = new ArrayList<String>();
        comment.add("편의점");
        comment.add("2+1");

        BoardSaveReqDTO boardSaveReqDTO = new BoardSaveReqDTO(
            new CategoryConst(),"제목1",user1,"내용1","img1",null,
            "부산시","부산진구","부전동",139.123123,39.123123,
            3,"직거래",LocalDateTime.now(),1000,comment);
  
            Event event = boardSaveReqDTO.toEventEntity();
            Board board = boardSaveReqDTO.toBoardEntity(event, boardSaveReqDTO.getImg());

            when(eventRepository.save(any())).thenReturn(event);
            when(boardRepository.save(any())).thenReturn(board);

            // when
            BoardSaveRespDTO boardSaveRespDTO = boardService.공고작성(boardSaveReqDTO, user1);
            om.registerModule(new JavaTimeModule());
            String responseBody = om.writeValueAsString(boardSaveRespDTO);
            System.out.println("Test : " + responseBody);

            // then
            assertThat(boardSaveRespDTO.getBoard().getTitle()).isEqualTo("제목1");
        }

}
