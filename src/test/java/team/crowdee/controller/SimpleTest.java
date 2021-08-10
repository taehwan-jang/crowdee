package team.crowdee.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

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
}
