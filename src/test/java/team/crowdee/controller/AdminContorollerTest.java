//package team.crowdee.controller;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//import team.crowdee.domain.Member;
//import team.crowdee.domain.dto.MemberDTO;
//import team.crowdee.repository.MemberRepository;
//import team.crowdee.service.MemberService;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@Transactional
//public class AdminContorollerTest {
//
//    @Autowired
//    AdminController adminController;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private MemberService memberService;
//    @Autowired
//    private EntityManager em;
//
//    @Test
//    public void 회원전체조회() {
//        ResponseEntity<?> responseEntity = adminController.backerView(4L);
//        System.out.println("아이디값으로 데이터"+responseEntity);
//    }
//
//    @Test
//    public void 크리에이터상세조회() {
//        ResponseEntity<?> responseEntity = adminController.creatorView(18L);
//        System.out.println("될까유"+responseEntity);
//    }
//    @Test
//    public void 백커조회() {
//        ResponseEntity<?> backer = adminController.backer();
//        System.out.println("데이터 받아오누"+backer+"흑흑");
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 심사() {
//        ResponseEntity<?> responseEntity = adminController.changeConfirm(35L);
//        System.out.println("ㅡ크크킄ㅋ"+responseEntity);
//
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 거절() {
//        ResponseEntity<?> responseEntity = adminController
//                .changeRefuse(17L);
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 펀딩심사() {
//        ResponseEntity<?> responseEntity = adminController.fundingConfirm(47L);
//        System.out.println("ㅡ크크킄ㅋ"+responseEntity);
//
//    }
//
//    @Test
//    public void 심사들어온거() {
//        ResponseEntity<?> responseEntity = adminController.fundingInspection();
//    }
//
//
//
//}
