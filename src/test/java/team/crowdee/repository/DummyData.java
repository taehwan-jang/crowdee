package team.crowdee.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.*;
import team.crowdee.domain.dto.CreatorDTO;
import team.crowdee.domain.dto.MemberDTO;
import team.crowdee.service.AdminService;
import team.crowdee.service.CreatorService;
import team.crowdee.service.MemberService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class DummyData {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CreatorService creatorService;
    @Autowired
    private AdminService adminService;

    @Autowired
    private CreatorRepository creatorRepository;
    @Autowired
    private FundingRepository fundingRepository;

    @Test
    @Rollback(value = false)
    public void 더미데이터() throws Exception {
        MemberDTO memberDTO1 = new MemberDTO();
        memberDTO1.setEmail("admin@crowdee.com");
        memberDTO1.setPassword("1q2w3e4r!");
        memberDTO1.setEmailCert("TAWOETHD");
        memberDTO1.setMobile("010-1231-1231");
        memberDTO1.setNickName("크라우디관리자");
        memberDTO1.setRegistDate(LocalDateTime.now());
        memberDTO1.setUserName("크라우디관리자");
        Long adminId = memberService.join(memberDTO1);
        adminService.makeAdmin(adminId);


        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail("forMember@gmail.com");
        memberDTO.setPassword("1q2w3e4r!");
        memberDTO.setEmailCert("TAWOETHD");
        memberDTO.setMobile("010-1231-1231");
        memberDTO.setNickName("테스트닉네임");
        memberDTO.setRegistDate(LocalDateTime.now());
        memberDTO.setUserName("크라우디");

        memberService.join(memberDTO);

        MemberDTO memberDTO2 = new MemberDTO();
        memberDTO2.setEmail("forCreator@gmail.com");
        memberDTO2.setPassword("1q2w3e4r!");
        memberDTO2.setEmailCert("WEFADFWE");
        memberDTO2.setMobile("010-1231-1231");
        memberDTO2.setNickName("크리에이터");
        memberDTO2.setRegistDate(LocalDateTime.now());
        memberDTO2.setUserName("창작자");

        memberService.join(memberDTO2);

//
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 크리에이터_더미데이터() {
        CreatorDTO creatorDTO = new CreatorDTO();
        creatorDTO.setAccountNumber("123-123123-123");
        creatorDTO.setBankName("신한");
        creatorDTO.setBusinessNumber("122-11122-11222");
        creatorDTO.setCreatorNickName("Berioza");
        creatorDTO.setAboutMe("피아노 듀오 베리오자(Berioza)는 피아니스트 전현주, 전희진 두 자매가 결성한 팀으로, 1997년 팀 결성 후, 2005년 St.Petersburg 피아노 듀오 협회에 회원으로 등록되었으며, 협회에 소속된 최연소이자 첫 외국인 연주자이다.");
        creatorDTO.setCareer("독일 뮌헨 Herkulessaal 에서 Bavarian Symphony Orchestra (지휘: Christoph Poppen)과 협연했으며, 체코 Smetana Hall, 독일 Leipzig Gewandhaus, Weimar, Dortmund, 러시아에서 다수의 리사이틀과 협연을 가졌다. 우리나라에서는 2011년 금호문화재단의 초청으로 금호아트홀에서 첫 리사이틀을 가졌으며, 2011-2012 독일 모차르트 협회 최초 한국인 장학생으로 선정되었다. 이들 듀오는 바로크 음악부터 현대음악까지 폭넓은 레파토리로 활동 중이며, 2014년에는 의 음악파트너로 선정되어 “러시안 로망스”라는 주제로 차이코프스키, 라흐마니노프, 쇼스타코비치, 아렌스키 등의 프로그램을 선보였으며 2014년 클래식FM라디오 “2014 한국의 클래식 내일의 주역들”에 선정되어 2015년 1월 음반이 발매되었다.");
        creatorDTO.setBankBookImageUrl("none");
        creatorDTO.setProfileImgUrl("https://www.jnilbo.com//2019/09/09/2019090916395293988_l.jpg");
        creatorDTO.setMemberId(5L);

        Creator creator = creatorService.joinCreator(creatorDTO);

//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 권한두개부여() {
        MemberDTO memberDTO3 = new MemberDTO();
        memberDTO3.setEmail("memberCreator@gmail.com");
        memberDTO3.setPassword("1q2w3e4r!");
        memberDTO3.setEmailCert("WETSVZK");
        memberDTO3.setMobile("010-1231-1231");
        memberDTO3.setNickName("크리에이터");
        memberDTO3.setRegistDate(LocalDateTime.now());
        memberDTO3.setUserName("창작자");

        memberService.join(memberDTO3);

        MemberDTO memberDTO6 = new MemberDTO();
        memberDTO6.setEmail("jth0602@gmail.com");
        memberDTO6.setPassword("1q2w3e4r!");
        memberDTO6.setEmailCert("TAWOETHD");
        memberDTO6.setMobile("010-2395-9602");
        memberDTO6.setNickName("부팀장");
        memberDTO6.setRegistDate(LocalDateTime.now());
        memberDTO6.setUserName("문병욱");

        memberService.join(memberDTO6);
//
//    }
////
//    @Test
//    @Rollback(value = false)
//    public void 펀딩더미데이터() {
        List<Creator> byEmail = creatorRepository.findByEmail("forCreator@gmail.com");

        Funding funding = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("summerNight")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("한여름밤의 꿈")
                .summary("서울예술대학교 공연 포스터")
                .thumbNailUrl("https://cdn.notefolio.net/img/5a/af/5aaf36082b60a519aac5db918f67fabd809ee35def6cfd2020855da5e6565db0_v1.jpg")
                .category("concert")
                .tag("#공연,#연주,#졸업,#음대")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(500000)
                .minFundraising(250000)
                .rateOfAchievement(0)
                .startDate("2021-08-28")
                .endDate("2021-09-15")
                .maxBacker(2)
                .restTicket(2)
                .postDate(LocalDateTime.now())
                .visitCount(4)
                .totalFundraising(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding);

        Funding funding1 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("dummyProject")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("창작집단 시파티만99번째의 창작뮤지컬<JUST ALICE>")
                .summary("“다 너를 위해서”\n" +
                        "\n" +
                        "“아름쁜한 꽃이 흩날리는 기분을 느낄 수 있을거야.”\n" +
                        "\n" +
                        "“내 기분은 내가 정해”")
                .thumbNailUrl("https://tumblbug-pci.imgix.net/f85c7e07d406f25fd3ea73c668eaa69a8712eaa8/8623a894f67815de2d3c823a7335ff4fea53a174/5fad5278aa1a8048437e7dfa7c849c532142c8d5/8e10d7d6-598c-4eac-9398-c9ecfb9bc472.jpeg?ixlib=rb-1.1.0&w=1240&h=930&auto=format%2Ccompress&lossless=true&fit=crop&s=5377c034ec7618d2e3b479fb3113c2b5")
                .category("theater")
                .tag("#연극,#연기,#공연")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(300000)
                .minFundraising(10000)
                .startDate("2021-08-19")
                .endDate("2021-09-11")
                .maxBacker(30)
                .restTicket(30)
                .postDate(LocalDateTime.now())
                .visitCount(3)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding1);

        Funding funding3 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("seoulNanum")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("서울 나눔 클라리넷 앙상블")
                .summary("제 11회 정기연주회")
                .thumbNailUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdBsiBk2tiAU3ckoHhgMnD8ef1ZoQFbD3meA&usqp=CAU")
                .category("concert")
                .tag("#클라리넷,#연주,#정기연주회,#콘서트,#콘서트홀")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(1000000)
                .minFundraising(20000)
                .startDate("2021-08-27")
                .endDate("2021-09-22")
                .maxBacker(50)
                .restTicket(50)
                .postDate(LocalDateTime.now())
                .visitCount(2)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding3);

        Funding funding4 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("bangbangCon")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("방방콘-The Live")
                .summary("방방콘서트")
                .thumbNailUrl("https://img6.yna.co.kr/etc/inner/KR/2020/06/14/AKR20200614028500005_01_i_P4.jpg")
                .category("consert")
                .tag("#콘서트,#무대,#연주,#라이브")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(500000)
                .minFundraising(5000)
                .startDate("2021-08-10")
                .endDate("2021-09-20")
                .maxBacker(100)
                .restTicket(100)
                .postDate(LocalDateTime.now())
                .visitCount(10)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding4);

        Funding funding5 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("notwithStanding")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("그럼에도 불구하고")
                .summary("네가 이미 알고 있던 행복에게로 데려다 줄게")
                .thumbNailUrl("https://pbs.twimg.com/media/EksrkH4U0AoAqn-.jpg")
                .category("theater")
                .tag("#공연,#대학로,#자유극장,#극장")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(1000000)
                .minFundraising(25000)
                .startDate("2021-08-11")
                .endDate("2021-09-15")
                .maxBacker(40)
                .restTicket(40)
                .postDate(LocalDateTime.now())
                .visitCount(22)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding5);

        Funding funding6 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("universalBallet")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("유니버설발레단")
                .summary("해설이 있는 발레 갈라")
                .thumbNailUrl("https://m.upinews.kr/data/upi/image/20190716/p1065593685838068_776_thum.jpg")
                .category("dance")
                .tag("#발레,#해설,#예술의전당")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(800000)
                .minFundraising(8000)
                .startDate("2021-08-11")
                .endDate("2021-09-30")
                .maxBacker(100)
                .restTicket(100)
                .postDate(LocalDateTime.now())
                .visitCount(12000)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding6);

        Funding funding7 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("futureBase")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("퓨쳐베이스")
                .summary("서울예술대학교 음악학부 응용음악")
                .thumbNailUrl("https://cdn.notefolio.net/img/e9/9e/e99ea83a067eee2469e5a3d218b91d1ebc6f75dce810558db22e7e9b1062e48a_v1.jpg")
                .category("concert")
                .tag("#공연,#연주,#졸업,#예술대")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(500000)
                .minFundraising(25000)
                .startDate("2021-08-30")
                .endDate("2021-09-15")
                .maxBacker(40)
                .restTicket(40)
                .postDate(LocalDateTime.now())
                .visitCount(20)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding7);

        Funding funding8 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("normalPure")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("평범한 순정씨")
                .summary("감성 충전 우리 음악극")
                .thumbNailUrl("https://cdn.notefolio.net/img/5a/af/5aaf36082b60a519aac5db918f67fabd809ee35def6cfd2020855da5e6565db0_v1.jpg")
                .category("theater")
                .tag("#연극,#음악극,#바라예술성장연구소,#세종아트센터")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(700000)
                .minFundraising(7000)
                .startDate("2021-08-25")
                .endDate("2021-09-14")
                .maxBacker(100)
                .restTicket(100)
                .postDate(LocalDateTime.now())
                .visitCount(20)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding8);

        Funding funding9 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("seoSamuel")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("서사무엘 라이브")
                .summary("서사무엘 Live in 홍대")
                .thumbNailUrl("http://design-meraki.com/web/product/big/20200216/5b19b0c40b0d407a74c77ec0ebe8a61f.jpg")
                .category("concert")
                .tag("#공연,#홍대,#라이브,#하나투어,#v-hall")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(500000)
                .minFundraising(25000)
                .startDate("2021-08-14")
                .endDate("2021-09-30")
                .maxBacker(20)
                .restTicket(20)
                .postDate(LocalDateTime.now())
                .visitCount(12)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding9);

        Funding funding10 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("antiGone")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("안티고네")
                .summary("team소시민 서울예대")
                .thumbNailUrl("https://cdn.notefolio.net/img/54/4d/544d8b6b6a0f4d6c2c2e35bebf519995bf01e93c2447758ee051879e1e6a8bdc_v1.jpg")
                .category("theater")
                .tag("#서울예대,#안티고네,#연극")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(20000)
                .minFundraising(20000)
                .startDate("2021-08-27")
                .endDate("2021-09-15")
                .maxBacker(1)
                .restTicket(1)
                .postDate(LocalDateTime.now())
                .visitCount(1)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding10);

        Funding funding11 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("salonTwist")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("미용실 트위스트")
                .summary("밀양 미용인들과 함께하며 지친 당신을 쿨하게 해주는 미용실판타지")
                .thumbNailUrl("http://www.anewsa.com/news_images/2021/05/10/mark/20210510154324.jpg")
                .category("theater")
                .tag("#연극,#신춘문예작가,#우리동네극장,#이유진,#밀양")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(650000)
                .minFundraising(15000)
                .startDate("2021-08-12")
                .endDate("2021-09-10")
                .maxBacker(55)
                .restTicket(55)
                .postDate(LocalDateTime.now())
                .visitCount(1)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding11);

        Funding funding12 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("theHelmet")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("THE HELMET")
                .summary("연극 더 헬멧, Rooms vol.1")
                .thumbNailUrl("https://img2.yna.co.kr/etc/inner/KR/2019/01/15/AKR20190115160800005_03_i_P2.jpg")
                .category("theater")
                .tag("#연극,#헬멧,#유한예술,#세종씨어터")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(550000)
                .minFundraising(14000)
                .startDate("2021-08-30")
                .endDate("2021-09-01")
                .maxBacker(150)
                .restTicket(150)
                .postDate(LocalDateTime.now())
                .visitCount(1)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding12);

        Funding funding13 = Funding.builder()
                .creator(byEmail.get(0))
                .projectUrl("rhinoceros")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("코뿔소")
                .summary("날 좀 봐! 내가 안보이나 내 소리가 안들려?")
                .thumbNailUrl("https://cdn.imweb.me/upload/S201812305c28b04b2f895/5c90ed89b3ccc.jpg")
                .category("theater")
                .tag("#연극,#전주대학교,#아트홀,#연기학과")
                .content("<h1>프로젝트 소개에 관한 글</h1>")
                .budget("<h1>예산 계획에 관한 글</h1>")
                .schedule("<h1>프로젝트 일정에 관한 글</h1>")
                .aboutUs("<h1>크리에이터 소개에 관한 글</h1>")
                .goalFundraising(300000)
                .minFundraising(3000)
                .startDate("2021-08-02")
                .endDate("2021-09-01")
                .maxBacker(100)
                .restTicket(100)
                .postDate(LocalDateTime.now())
                .visitCount(1)
                .totalFundraising(0)
                .rateOfAchievement(0)
                .status(Status.progress)
                .sendMail(false)
                .result(false)
                .build();
        fundingRepository.save(funding13);





    }





}
