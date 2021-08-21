package team.crowdee.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class SimpleTest {

    @Test
    public void 랜덤문자만들기() {
        char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'N', 'M', 'O', 'P', 'Q', 'S', 'T', 'U', 'W', 'X', 'Y', 'Z'};
        System.out.println(chars.length);
        String str = "";
        for (int i = 0; i < 10; i++) {
            str += chars[(int) (Math.random() * chars.length)];
        }
        System.out.println(str);

    }

    @Test
    public void 날짜계산() throws Exception {
        //given
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //when
        //then

        //남은 시간이 포멧이 잘못됨 Date끼리 연산이 가능하다면?

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.plusDays(50);

        long restDays = Duration.between(now, next).toDays();
        System.out.println("restDays = " + restDays);


    }

    @Test
    public void 달성률_문자열_출력() throws Exception {
        //given
        int participant = 3022000;
        int maxParticipant = 300000;
        //when
        double i = ((double)participant) / ((double) maxParticipant)*100;
        String result = String.valueOf(i);

        int last = result.lastIndexOf(".")+2;
        String finalResult = result.substring(0, last)+"%";
        //then
        System.out.println("finalResult = " + finalResult);
    }

    @Test
    public void 문자열에서_날짜로변환_남은날짜() throws Exception {
        //given
        String startDate = "2021-08-21";
        String endDate = "2021-09-11";

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        //when
        int days = Period.between(start, end).getDays();
        //then
        Assertions.assertEquals(21,days);
    }

}
