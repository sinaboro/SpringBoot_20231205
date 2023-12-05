package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ReplyReopsitoryTest {

    @Autowired
    private ReplyReopsitory replyReopsitory;

    @Test
    public void testInsert(){
        Board board = Board.builder().bno(100L).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyText("댓글....")
                .replyer("replyer1")
                .build();

        replyReopsitory.save(reply);
    }

    @Test
    public void testBoardReplies(){
        Long bno = 100L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());


        log.info(">>>>>>>>>>>> " + pageable);

        Page<Reply> result = replyReopsitory.listOfBoard(bno, pageable);

        result.getContent().forEach( reply -> {
            log.info(reply);
        });
    }
}