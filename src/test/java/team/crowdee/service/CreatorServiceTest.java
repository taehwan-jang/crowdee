package team.crowdee.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CreatorServiceTest {

    @Autowired
    private CreatorService creatorService;


    @Test
    public void 트랜잭션_2개_영속성_테스트() throws Exception {
        //given
        //14e2e26b6431477caa5eb024bd09bc75
        //추후 설계해볼것
        //when

        //then
    }

}