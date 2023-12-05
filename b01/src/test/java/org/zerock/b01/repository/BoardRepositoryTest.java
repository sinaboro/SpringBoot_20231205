package org.zerock.b01.repository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardListAllDTO;
import org.zerock.b01.dto.BoardListRelpyCountDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyReopsitory replyReopsitory;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Board board = Board.builder()
                    .title("title..."+i)
                    .content("content..."+i)
                    .writer("user"+(i%10))
                    .build();

           Board result = boardRepository.save(board);
           log.info("Bno: " + result.getBno());
        });
    }

    @Test
    public void testPaging(){
       Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

      Page<Board> result = boardRepository.findAll(pageable);

      log.info("total count: " + result.getTotalElements());
      log.info("total getTotalPages: " + result.getTotalPages());
      log.info("total getNumber: " + result.getNumber());
      log.info("total getSize: " + result.getSize());

      result.getContent().forEach(list-> log.info(list));
    }

    @Test
    public void testSearch1(){
        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());
        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll(){

        String[] types = {"t", "c","w"};
        String keyword = "1";

        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        log.info("====> " + result.getTotalPages());

        result.getContent().forEach(board-> log.info(board));
    }

    @Test
    public void testSearchReplyCount(){
        String[] types= {"t","c","w"};
        String keyWord = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<BoardListRelpyCountDTO> result = boardRepository.searchWithReplyCount(types, keyWord, pageable);

        log.info(result.getTotalPages());

        log.info(result.getSize());
        log.info(result.getNumber());
        log.info(result.hasPrevious() + " : " + result.hasNext());
        result.getContent().forEach(board->log.info(board));
    }

    @Test
    public void testInsertWithImage(){
        Board board = Board.builder()
                .title("Image Test")
                .content("첨부파일 테스트")
                .writer("tester")
                .build();

        for(int i=0; i<3 ; i++){
            board.addImage(UUID.randomUUID().toString(), "file" + i + "jpg");
        }

        boardRepository.save(board);
    }

    @Test
    public void testReadWithImage(){
        Board board = boardRepository.findById(1L).orElseThrow();

        log.info(board);
        log.info("-------------------------------");
        log.info(board.getImageSet());
    }

    @Test
    public void testReadWithImages(){
        Board board = boardRepository.findByIdWithImages(1L).orElseThrow();

        log.info(board);
        log.info("-------------------------------");
        log.info(board.getImageSet());
    }

    @Transactional
    @Commit
    @Test
    public void testModifyImage(){
        Board board = boardRepository.findByIdWithImages(1L).orElseThrow();

        board.clearImages();

        for(int i=0; i<3; i++){
            board.addImage(UUID.randomUUID().toString(), "UPDATEFile" + i + ".jpg");
        }

        boardRepository.save(board);
    }

    @Test
    public void testRemoveAll(){
        replyReopsitory.deleteByBoard_Bno(1L);

        boardRepository.deleteById(1L);
    }

    @Test
    public void testInsertAll() {

        for (int i = 1; i <= 100; i++) {

            Board board  = Board.builder()
                    .title("Title.."+i)
                    .content("Content.." + i)
                    .writer("writer.." + i)
                    .build();

            for (int j = 0; j < 3; j++) {

                if(i % 5 == 0){
                    continue;
                }
                board.addImage(UUID.randomUUID().toString(),i+"file"+j+".jpg");
            }
            boardRepository.save(board);

        }//end for
    }


    @Transactional
    @Test
    public void testSearchImageReplyCount() {

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

//        boardRepository.searchWithAll(null, null,pageable);

        Page<BoardListAllDTO> result = boardRepository.searchWithAll(null,null,pageable);

        log.info("---------------------------");
        log.info(">>>>>>>>>> " +result.getContent());
        log.info(result.getTotalElements());

        result.getContent().forEach(boardListAllDTO -> log.info(boardListAllDTO));


    }

}