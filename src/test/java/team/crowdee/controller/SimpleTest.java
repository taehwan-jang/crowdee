package team.crowdee.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static java.util.Base64.*;

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
        String endDate = "2021-09-11";

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        //when
        int days = Period.between(start, end).getDays();
        //then
        Assertions.assertEquals(20,days);
    }



    @Test
    public void 디코드테스트() throws Exception {
        //given
        String token = "eyJhbGciOiJub25lIn0.eyJzdWIiOiJjcm93ZGVlZnVuZGluZ0BnbWFpbC5jb20iLCJhdXRoIjoiIiwiZXhwIjoxNjI5NzEwOTU1fQ.aasdilfhasd";
        int firstDot = token.indexOf(".");
        int lastDot = token.lastIndexOf(".");
        String substring = token.substring(firstDot+1, lastDot);
        Decoder decoder = getDecoder();
        byte[] keyBytes = decoder.decode(substring.getBytes());
        //when
        System.out.println("keyBytes = " + keyBytes);
        String payload = new String(keyBytes);
        String[] strings = payload.split("\"");
        String string = strings[3];
        System.out.println("string = " + string);

        System.out.println("한글이 아니잖아");
        //then
    }


    @Test
    public void 문자열자르기() {
        String testString = "123.456.789";
        String[] split = testString.split("\\.");
        for (String s : split) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void 날짜테스트() throws Exception {
        //given
        String a = "2021-08-31";
        String b = "2021-09-01";
        LocalDate c = LocalDate.parse(a);
        LocalDate now = LocalDate.now();
        LocalDate d = LocalDate.parse(b);
        //when
        System.out.println("a>b  "+(d.compareTo(now)));
        //then
    }
}
