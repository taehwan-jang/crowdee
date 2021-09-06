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
        memberDTO2.setNickName("Berioza");
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
                .projectUrl("namoo")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("Project namoo, 나무뎐 근본")
                .summary("뿌리깊게 자라나는 수 천년을 묵은 고목나무에서 지고 피는 수 많은 잎사귀와 열매처럼,")
                .thumbNailUrl("https://tumblbug-pci.imgix.net/d05a1d64f756ec69917d918d04d51173c6bcdef9/191ecbe82a56901122fb2935b1359161a78f973b/bf9ed049c25a4b0095e490a76fdd091f3e16fa19/0f825650-0e6c-4920-9366-10c41d6a34cd.jpeg?ixlib=rb-1.1.0&w=1240&h=930&auto=format%2Ccompress&lossless=true&fit=crop&s=eb5a034708b327e8d9aba4615b6c3ab1")
                .category("theater")
                .tag("#나무,#연극,#무용단,#현대무용")
                .content("<p style=\"margin-left:0em;\">&nbsp;BTS를 시작으로 전 세계적으로 퍼져가는 한류의 바람은 거세지고 있습니다. 그리고 지금 돌풍을 일으키는 한류의 바람은 지금까지의 결과 다릅니다.&nbsp;&nbsp;기존 한류의 결은 '한국의 트렌드'였다면 현재의 한류의 결은 '한국 전통문화만의 고유함'이라 할 수 있습니다.&nbsp;&nbsp;가장 한국적인것이 가장 세계적이다, 라는 진부하기까지도 한 이야기를 많이 들어보셨을 것 입니다. 한국전통문화만의 강점은 그대로 지닌 채, 트렌드에 맞춰 각 예술가의 개성으로 소화시키는 현재의 한류는 감히 두번째 한류라고 지칭하고 싶습니다.&nbsp;&nbsp;&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;그렇기에 지금까지 잘 알려지지 않았던 한국전통문화 중에서, 한국무용의 수려함을 알리고자합니다.&nbsp;&nbsp;무용이라는 장르가 일상에서 그렇게 가까운 분야는 아닙니다. 하지만 모든 사람들은 발레, 현대무용, 실용무용등을 많이 접하기도, 직접 배우기도, 익히 알고들 있습니다.&nbsp;&nbsp;하지만 한국무용은 어떨까요? 한국무용이라고 하면 옛 선조들의 느린 장단에 맞춰 곱게 움직이는 자태가 떠오릅니다.&nbsp;&nbsp;&nbsp;프로젝트 댄스 시어터 나무는 한국전통무용의 기본기를 탄탄하게 다져 현재 두 번째 한류에 걸 맞는 한국창작무용을 선보이고 실험하고 연구하는 무용단입니다.&nbsp;</p><p>&nbsp;</p><figure class=\"image image_resized image-style-align-center\" style=\"width:497px;\"><img src=\"https://tumblbug-psi.imgix.net/d05a1d64f756ec69917d918d04d51173c6bcdef9/191ecbe82a56901122fb2935b1359161a78f973b/bf9ed049c25a4b0095e490a76fdd091f3e16fa19/4fef27ec-a084-46f3-b93f-4cf5664b1d84.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=709ed51dcef16ef2538320b5991cbd6e\"></figure><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<i><u>&lt;한국전통무용의 호흡과 기본기를 바탕으로 새롭게 창작, 연출된 작품 \"천연\"&gt;</u></i></p><p>&nbsp;</p><p style=\"margin-left:0em;\">가장 한국적인 수려함, 움직임이 들어가있는 한국창작무용의 발전으로 우리나라에 단단히 뿌리내린 한국무용의 역사가 아름드리 꽃 피우는 고목나무로 성장 할 수 있게끔, 이 두 번째 한류가 스무번째 한류까지 이어질 수 있게 프로젝트 댄스 시어터 나무가 그 출사표를 던지고자 합니다.</p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\"><i><strong>Q. 무엇을 만들기 위한 프로젝트인가요?</strong></i></p><p style=\"margin-left:0em;\">&nbsp;시간이 흐름에 따라 변화되는 것들이 있습니다. 그것은 발전이 되었다, 퇴보가 되었다 라는 평가를 할 수 있는 따위의 개념들이 아닙니다. 그저 그 시대를 반영한 흐름을 보여줍니다.&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;계속해서 변화해가는 흐름과 시대상에 맞춰 전통은 어떤 방향을 모색해야할까요? 온고지신이라는 단어의 가장 적절한 사례는 무엇이 될 수 있을까요?&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;세계적으로 한국만의 고유한 멋과 전통이 각광을 받고 있는 시대입니다. 이러한 시대 속에서 한국전통무용으로 탄탄하게 기본기를 다진 무용수들의 새로운 시도와 도전을 장려하기 위해 구상되고 기획된 프로젝트입니다.&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\"><i><strong>Q. 프로젝트를 간단히 소개한다면?</strong></i></p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;2021년 2월, 프로젝트 무용단 나무가 창설되었습니다. 나무 무용단의 \"나무\"는 그물 나(羅)자에 굳셀 무(武)자를 사용한 굳센 그물이라는 뜻을 가지고 있습니다. 또 한 불리워지는 이름에서처럼 나무라는 형상을 심볼로 한 프로젝트성 무용단입니다.</p><p style=\"margin-left:0em;\">&nbsp;</p><figure class=\"image\"><img src=\"https://tumblbug-psi.imgix.net/d05a1d64f756ec69917d918d04d51173c6bcdef9/191ecbe82a56901122fb2935b1359161a78f973b/bf9ed049c25a4b0095e490a76fdd091f3e16fa19/9bb4a37e-17b4-4fff-a4bc-6db4ab8d0888.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=02994bac59ab97d965d4075b5325f7fd\"></figure><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<i><u>&lt;전통한국무용으로 기본기를 다져, 창작한국무용을 연구하는 무용수들&gt;</u></i></p><p>&nbsp;</p><p style=\"margin-left:0em;\">뿌리깊게 자라나는 수 천년을 묵은 고목나무에서 지고 피는 수 많은 잎사귀와 열매처럼, 그리고 그 곳에 공존하는 많은 생명체들처럼. 탄탄한 근본에서 뻗어져나가는 새로운 시도들은 흔들림이 없습니다. 프로젝트 그룹 나무의 경우도 마찬가지입니다. 탄탄한 전통 기본기로 다져진 한국무용수들은 많은 무용의 장르들을 접해가며 가장 한국무용스러운 자신만의 움직임들을 개척해나갑니다.&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;수 많은 문화와 역사들이 쏟아져나오는 현대화, 세계화의 시대. 가장 한국적이면서도 가장 세계적인 움직임들로 개성있는 한국무용들을 선보이고자 합니다.&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\"><i><strong>Q. 이 프로젝트가 왜 의미있나요?</strong></i></p><p style=\"margin-left:0em;\">&nbsp;세계화, 지구촌 이라는 개념이 확장되가는 속도는 점점 겉잡을 수 없이 빨라져갑니다. 거세져가는 다양화의 흐름 속에서 \"한국\"이 주목받고 있습니다. 이날치의 판소리에서 방탄소년단의 한복에서, 그리고 올림픽에서&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;다양화와 세계화에서 가장 독보적이고 개성적인 것은 견고한 자신만의 것입니다. 우리에게는 오천년 역사를 지닌 대한민국만이 가진 문화가 있습니다.&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;물 들어올 때 노를 저어야만 합니다. 밀려오는 파도 속에서 앞서 노를 저어주고 물길을 만들었던 사람들에게 힘입어 한국전통문화 가치 중 하나인 한국무용의 아름다움을 보여주고자 합니다. 그리고 이 프로젝트는 훗날 이어지는 한국문화의 또 다른 뱃길이자 물길이 될 것입니다.&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\"><i><strong>Q. 현재 어느 정도 진행되었고, 진행 과정은 어땠나요?</strong></i></p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;2021.&nbsp;<br>&nbsp;2월, 프로젝트 무용단 나무 창립<br>&nbsp;3월~4월, 공연계획 수립, 공연장 섭외<br>&nbsp;5월, 서강대학교 메리홀 대극장 계약 완료<br>&nbsp;6월, 서강대학교 메리홀 공연 기획, 프로그램 순서, 작품의도 완성<br>&nbsp;7월, 각 작품 별 개별 / 단체 연습<br>&nbsp;7월 16일, 서강대학교 메리홀 대극장 \"나무뎐\" 공연<br>&nbsp;8월, 세종문화예술회관 M시어터 공연 기획, 프로그램 순서, 작품의도 완성<br>&nbsp;9월, 각 작품 별 개별 / 단체 연습<br><u>&nbsp;9월 22일 세종문화예술회관 \"나무뎐\" 공연 (예정) , 나무전 관련 굿즈 증정 (예정)</u></p><p style=\"margin-left:0em;\">순서로 진행될 예정입니다.<br><br>&nbsp;프로젝트 무용단 나무의 시작과 출발점을 알리는 \"나무뎐\"의 경우 한국전통무용과 한국창작무용의 융복화가 주로 이뤄졌었다면, 이번 세종문화예술회관 M시어터에서 진행 예정일 \"나무뎐\" 의 경우 우리의 뿌리를 찾아가는 과정에 대한 내용을 다룰 예정입니다.&nbsp;<br>&nbsp;첫 번째 공연 \"나무뎐\"에서는 나무라는 이름에 대한 정체성, 정의성, 정립성을 확립시키고 앞으로의 포부를 다뤘다면 두 번째 이야기인 “나무뎐”은 근본이자 뿌리이자 태어남의 대한 이야기를 한국무용으로 풀어갈 예정입니다. 각 무용수들만의 창작품들과 연출작들로 이뤄져있는 총 9개의 작품들은 한국무용수로서의 근본과 앞으로의 방향성에 대해 고민하며 목표성을 더 굳건하게 세워가는 과정의 이야기들입니다.</p><p style=\"margin-left:0em;\">&nbsp;</p><h3 style=\"margin-left:0em;text-align:center;\"><strong><u>환립 2.0</u></strong></h3><p style=\"margin-left:0em;text-align:center;\"><br>출연: 이정민, 이웅빈</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><h3 style=\"margin-left:0em;text-align:center;\"><strong><u>이매방류 살풀이춤</u></strong></h3><p style=\"margin-left:0em;text-align:center;\"><br>출연: 이현강</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><h3 style=\"margin-left:0em;text-align:center;\"><strong><u>비긴어게인</u></strong></h3><p style=\"margin-left:0em;text-align:center;\"><br>출연: 김민지, 권혜지</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><h3 style=\"margin-left:0em;text-align:center;\"><strong><u>정재만류 태평무</u></strong></h3><p style=\"margin-left:0em;text-align:center;\"><br>출연: 윤수안</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><h3 style=\"margin-left:0em;text-align:center;\"><strong><u>천연</u></strong></h3><p style=\"margin-left:0em;text-align:center;\"><br>출연진: 여혜연, 성현겸, 이웅빈, 김유빈, 이정민, 윤수안, 최민석, 김사랑, 김서영</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><h3 style=\"margin-left:0em;text-align:center;\"><strong><u>수풀 림</u></strong></h3><p style=\"margin-left:0em;text-align:center;\"><br>출연: 전예원, 이현강</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><h3 style=\"margin-left:0em;text-align:center;\"><strong><u>Blackboard</u></strong></h3><p style=\"margin-left:0em;text-align:center;\"><br>출연진: 성현겸, 윤수안, 이웅빈</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><h3 style=\"margin-left:0em;text-align:center;\"><strong><u>소고 및 진도북</u></strong></h3><p style=\"margin-left:0em;text-align:center;\"><br>출연: 김민지, 권혜지, 전예원, 이현강</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><h3 style=\"margin-left:0em;text-align:center;\"><strong><u>아이덴티티</u></strong></h3><p style=\"margin-left:0em;text-align:center;\"><br>출연: 김민지, 권혜지,<br>전예원, 이현강<br>김유빈, 성현겸, 여혜연, 윤수안, 이웅빈, 이정민,&nbsp;<br>김사랑, 김서영</p><p style=\"margin-left:0em;\"><br>&nbsp;3번정도 공연을 올렸었던 환립은 환립 2.0으로 더 발전을 시켰으며, U-DANCE 페스티벌 참가작이였던 정재만류 태평무 역시 한 번 더 올리게 되었습니다. 또 한 댄스브릿지페스티벌 초청작품이었던 Blackboard 역시 세종문화예술회관에서 한 번 더 공연을 하게 되었습니다.</p><p style=\"margin-left:0em;text-align:center;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/d05a1d64f756ec69917d918d04d51173c6bcdef9/191ecbe82a56901122fb2935b1359161a78f973b/bf9ed049c25a4b0095e490a76fdd091f3e16fa19/bed020ae-8d21-42f4-a93d-7960bad2b2ee.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=4da138660adb9071b5a778481ef22347\"><br><i><u>&lt;Dance Brige Festival 초청 공연사진&gt;</u></i></p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/d05a1d64f756ec69917d918d04d51173c6bcdef9/191ecbe82a56901122fb2935b1359161a78f973b/bf9ed049c25a4b0095e490a76fdd091f3e16fa19/02b4a1cd-cf54-46a7-b62b-2702600f2b5f.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=28c932de15effdfe72c9f77bf13f76ea\"><i><u>&lt;Black Board 공연 사진&gt;</u></i></p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/d05a1d64f756ec69917d918d04d51173c6bcdef9/191ecbe82a56901122fb2935b1359161a78f973b/bf9ed049c25a4b0095e490a76fdd091f3e16fa19/6b318026-c199-4c40-bd6a-930f39af100e.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=252ac286d1b6c6449e68bb4e1ab5e0ec\"><br><i><u>&lt;U-Dance Festival 에서 선보였던 정재만류 태평무 공연 사진&gt;</u></i></p><p style=\"margin-left:0em;\"><br>&nbsp;새로 선 보이는 신작과 연출가들의 정체성이라 할 수 있는 안무작들의 재연이 섞여있는 이번 공연은 \"나무뎐\" 이라는 작품 제목에 걸 맞게 자신의 근본이자 첫 출발인 신작들과 앞으로의 굳혀나가고 싶은 예술, 무용, 움직임의 방향성까지 한 번에 볼 수 있는 공연입니다.&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;단순히 신진예술가들의 일반적인 공연이 아닌, 한국문화의 미래를 이끌어 나갈 차세대 예술가들, 그 중에서도 한국전통무용의 호흡과 기본기를 탄탄하게 다져놓은 무용수들의 각자만의 방향성과 넘치는 끼를 제대로 선 보여주고 본 보여줄 공연으로 자리잡게 될 것입니다.</p><p style=\"margin-left:0em;\">&nbsp;나무전 굿즈의 경우, 고목나무의 계절감을 대표하는 캐릭터들로 구성 될 예정입니다.&nbsp;<br>여름 ver,으로는 애벌레, 복사꽃, 초록 잎사귀 등을 이용한 캐릭터들로 굿즈 제작중이며 가을 ver,으로는 도토리, 다람쥐, 단풍잎 등을 이용한 굿즈 제작중입니다.&nbsp;<br>&nbsp;우선 가을 ver의 경우 대표적으로 다람쥐 캐릭터가 완성이 되었으며 이 굿즈는 텀블러와 키링 마스크패치등으로 사용될 예정입니다. 현 페이지 하단부분에 예정될 목업 이미지 올려놓았습니다.&nbsp;</p><p>&nbsp;</p>\n")
                .budget("<blockquote><h2 style=\"margin-left:0em;text-align:center;\"><span style=\"background-color:hsl(30, 75%, 60%);\"><strong>실제 목표 펀딩금액보다 훨씬 더 작게 잡았습니다.</strong></span></h2><h2 style=\"margin-left:0em;text-align:center;\"><span style=\"background-color:hsl(30, 75%, 60%);\"><strong>부디 목표금액을 넘었더라도</strong></span></h2><h2 style=\"margin-left:0em;text-align:center;\"><span style=\"background-color:hsl(30, 75%, 60%);\"><strong>후원을 멈추지 마시기를 간절히 부탁드립니다.</strong></span></h2></blockquote><p>&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;</p><p>&nbsp;</p><p style=\"margin-left:0em;\">* 모금액 이외의 예산은<br>&lt;프로젝트 나무&gt; 2021년 공연제작예산에서 사용할 수 있게 충당할 것입니다.</p><p style=\"margin-left:0em;\">인건비용<i> (총 1,300,000원)</i></p><ul><li>공연 굿즈 제작 디자이너 인건비: 200,000원</li><li>공연 극장 셋업 인건비: 1,100,000원&nbsp;<br><i>조명 셋업 (행잉, 리깅) 비용: 130,000 X 5명</i><br><i>조명 디자이너, 감독 비용: 450,000</i></li></ul><p style=\"margin-left:0em;\">공연 대관비용 <i>(총 3,759,800원)</i></p><ul><li>세종문화예술회관 M시어터 9월 22일 당일 대관비: 2,062,500원</li><li>M시어터 추가 부대시설 대여비용: &nbsp;1,697,300\u202C원<br><br><i>포그머신_ 5,500</i><br><i>PAR64_ 8,800</i><br><i>Source Four_ 275,000</i><br><i>Moving Light Spot 1.2kw_ 330,000</i><br><i>Moving Light Wash 1.2kw_ 440,000</i><br><i>SR(확성)_ 165,000</i><br><i>C/R 믹싱콘솔_ 110,000</i><br><i>무선용 인터컴_ 33,000</i><br><i>무용용 고무판_ 330,000</i></li></ul><p style=\"margin-left:0em;\">굿즈 제작 재료비용<i> (총 500,000원)</i></p><ul><li>아크릴 키링 제작비용: 150,000 원</li><li>투명 텀블러 제작비용: 150,000 원</li><li>마스크 패치 제작비용: 200,000 원</li></ul><p style=\"margin-left:0em;\">기타<i> (총 140,200원)</i></p><ul><li>수수료: 140,200 원</li></ul><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><hr><h2 style=\"margin-left:0em;text-align:center;\"><strong><u>총 목표 금액, 5,700,000</u></strong></h2><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\"><i>텀블벅 특징 상 목표 금액을 도달하지 못하면</i><br><i>후원해주신 분들의 성의조차 받을 수 없게끔 됩니다.</i><br><i>그렇기에 부득이하게 최소금액으로만 설정해놓았으니</i><br><i>여러분들의 많은 관심을 간곡하게 부탁드립니다.&nbsp;</i></p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\">모금되는 예산에 따라 공연 준비과정이 순차적으로 진행이 될 것이며<br>그렇기에 현재 진행이 되어있는 부분들이 매우 더딘 상황입니다,<br>실시간으로 모이는 금액에 따라 단계별로 공연이 진행될 수 있습니다!<br>여러분들의 관심이 모여야 계획했던 공연방식대로 진행이 될 수 있습니다.<br>금액이 모이는대로&nbsp;<br><i><strong><u>1. 대관비</u></strong></i><br><i><strong><u>2. 추가 부대설비비용</u></strong></i><br><i><strong><u>3. 인건비</u></strong></i><br><i><strong><u>4. 굿즈제작비용</u></strong></i><br>으로 차차 진행 될 예정입니다.&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;</p>\n")
                .schedule("<ul><li>21.02.27. 나무 무용단 창설</li><li>21.03.05~21.03.30 1차 프리프로덕션 (공연계획 수립, 공연기획의도 구상, 공연장 섭외)</li><li>21.04.12~21.05.08 2차 프리프로덕션 (작품구상, 세부작품 선정, 출연진 확정)</li><li>21.05.15~21.05.30 3차 프리프로덕션 (스태프 섭외완료, 작품별 연출의도, 기획의도 확정)</li><li>21.05.30~21.06.15 스태프 회의 (극장내 홍보, 무대계획 등 논의와 수립후 최종 확정)</li><li>21.06.15~21.07.14 작품완성 및 안무연습 (작품별 캐스팅, 동선체크)</li><li><u>21.07.15~21.07.16 서강대학교 메리홀 대극장 \"나무뎐\" 첫번째 공연</u><br><br>&nbsp;</li></ul><figure class=\"image image_resized image-style-align-center\" style=\"width:580px;\"><img src=\"https://tumblbug-psi.imgix.net/d05a1d64f756ec69917d918d04d51173c6bcdef9/191ecbe82a56901122fb2935b1359161a78f973b/bf9ed049c25a4b0095e490a76fdd091f3e16fa19/240b4df5-8d72-4fea-8ed2-ed95e48bfffb.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=99b07fe415a637c50536d86dcda0ec8c\"></figure><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<i><u>&lt;서강대학교 메리홀 대극장 나무뎐 공연사진, Survive&gt;</u></i></p><p><br><br><br>&nbsp;</p><ul><li>21.07.30~21.08.04 <strong>세종문화예술회관 M시어터 프로덕션 인원 재구성</strong></li><li>21.08.05~21.08.12 1차 프리프로덕션 (공연프로그램 수정, 공연기획의도 수정)</li><li>21.08.13~21.08.20 2차 프리프로덕션 (작품구상, 세부작품 1차 선별, 출연진 확정)</li><li>21.08.21~21.08.28 3차 프리프로덕션 (스태프 섭외완료, 출연진들 시연회 준비)</li><li><u>21.08.29 내부 시연회, 세종문화예술회관 M시어터 최종 프로그램 순서 확정&nbsp;</u><br><br>&nbsp;</li><li>21.08.30~21.09.08 스태프 회의 (극장내 홍보, 무대계획 등 논의와 수립후 최종 확정)</li><li>21.09.02 텀블벅 펀딩 오픈</li><li>21.09.09~21.09.21 확정된 계획에 따라 작품완성 및 안무연습</li><li>21.09.15 펀딩 마감 및 후원자 리워드 상품 제작 시작</li><li>21.09.13~21.09.19 리워드 제작완료 및 발주 시작</li><li>21.09.20~21.09.21 리워드 후가공 처리 및 포장작업</li><li><u>21.09.22 세종문화예술회관 M시어터 \"나무뎐,</u><span style=\"font-family:Arial, Helvetica, sans-serif;\"><u> 根本</u></span><u>\" 공연 </u>펀딩 리워드 전달</li></ul>")
                .aboutUs("<p style=\"margin-left:0em;text-align:center;\">&lt;미션 및 비전&gt;</p><p style=\"margin-left:0em;text-align:center;\">Project dance theater, namoo<br>는 한국전통무용을 기반으로 훈련된 무용수들의 단체입니다.&nbsp;</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;한국무용을 기반으로 새로운 창작무용을 선보이고 있으며<br>실용, 현대, 타전통춤 등을 융,복합하여 한국창작무용을 연구하고있습니다.&nbsp;</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\">&lt;공연 이력&gt;</p><p style=\"margin-left:0em;text-align:center;\">2021. 07. 16 서강대학교 메리홀 나무뎐 공연&nbsp;<br>2021. 07. 23 양재동 M극장 댄스브릿지페스티벌 초청 공연<br>2021. 08. 28-19 성균소극장 서울프린지페스티벌 차이-사이 찬조 출연<br>2021. 09. 22 세종문화예술회관 M시어터 나무뎐, <span style=\"font-family:Arial, Helvetica, sans-serif;\">根本</span> 공연 (예정)<br>2021. 10. 02 남산 국악당 크라운해태홀 초청 공연 (예정)</p><p style=\"margin-left:0em;text-align:center;\">주최 및 주관: 춤사 손정연, 프로젝트 무용단 나무<br><br>&nbsp;</p><p style=\"margin-left:0em;text-align:center;\">&lt;프로덕션 인원 소개&gt;</p><p style=\"margin-left:0em;text-align:center;\">예술감독: 손정연<br>총괄PD: 송은혜<br>조명감독: 양가영<br>영상디자인: 조나은<br>무대감독: 홍준영<br>기획팀장: 박민수<br>디자인: 원채연<br>지도: 김다희<br>사진: 이현준<br>분장: 이재형<br>영상: 한필름</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><p style=\"margin-left:0em;text-align:center;\">출연<br>: 권혜지,김민지,<br>&nbsp; 이현강, 전예원,<br>&nbsp; 김유빈,성현겸,여혜연,윤수안,이웅빈,이정민,최민석,<br>&nbsp; 김사랑,김서영</p><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><h2 style=\"margin-left:0em;text-align:center;\"><strong><u>&lt;연습 사진&gt;</u></strong></h2><p style=\"margin-left:0em;text-align:center;\">&nbsp;</p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/d05a1d64f756ec69917d918d04d51173c6bcdef9/191ecbe82a56901122fb2935b1359161a78f973b/bf9ed049c25a4b0095e490a76fdd091f3e16fa19/db5556d3-b2af-4642-a087-fdf06e5156eb.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=8b11a4ee8a2bfedb1d28e93c1668a719\"></p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/d05a1d64f756ec69917d918d04d51173c6bcdef9/191ecbe82a56901122fb2935b1359161a78f973b/bf9ed049c25a4b0095e490a76fdd091f3e16fa19/a90f4bcd-f32f-4e28-87f1-566b8b317a5a.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=2c8dbe7a6209b5aa854748d2f8a3d4c6\"></p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/d05a1d64f756ec69917d918d04d51173c6bcdef9/191ecbe82a56901122fb2935b1359161a78f973b/bf9ed049c25a4b0095e490a76fdd091f3e16fa19/35c0b9e3-e2e2-4fe9-8af8-faedfbc2d4e0.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=e3b72458bc4d605d1f9e9a51717c5d73\"><br>&nbsp;</p>")
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
                .projectUrl("kickthebucket")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("과감한뮤지컬 : Kick The Bucket")
                .summary("2019년 가을의 한강.\n" +
                        "저마다의 상황과 이야기로\n" +
                        "한강에서의 저녁을 채우는 사람들로 북적인다. \n" +
                        "그들을 유유히 바라보는 여유로운 표정의 남자.\n" +
                        "금방이라도 한강물로 뛰어들 것 같은 인주를 발견하는데..")
                .thumbNailUrl("https://tumblbug-pci.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/87c5af96-6ada-4d8a-96f9-69fc772c7196.jpg?ixlib=rb-1.1.0&w=1240&h=930&auto=format%2Ccompress&lossless=true&fit=crop&s=f03f03e488856acea5fab196413c88df")
                .category("musical")
                .tag("#뮤지컬,#과감한,#공연")
                .content("<figure class=\"image image_resized\" style=\"width:620px;\"><img src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/04dee357-c686-4ca9-9b4b-c11b8ea5441a.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=a505771b2501b8463fe3e87d1003ea0d\"></figure><p>&nbsp;</p><p style=\"margin-left:0em;\">여기 자살이 버킷리스트인 사람이 있습니다.<br><u>버킷리스트란 목을 매달아 죽기 전,</u><br><u>해보고 싶은 것들을 적은 리스트</u>를 말합니다.<br>그런데 자살 자체가 버킷리스트라고...?</p><p style=\"margin-left:0em;\">여러분의 버킷리스트는 무엇인가요?<br>안녕하세요 저희는 버킷리스트를 함께 이뤄나가는 팀<br><strong>&lt;과감한인생&gt;</strong> 입니다!</p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/037cb806-e88a-41df-8cde-a3d4d5c6fa40.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=728f5a62751dfc9b95d73de282744a89\"></p><p style=\"margin-left:0em;\">모르는 사람과 함께 여행 떠나기, 남 눈치 안 보고 펑펑 울어보기, 출근길 지하철 버스킹 하기, 길거리 꽃 장사 해보기 등 사람들의 버킷리스트를 함께 실현해 나가고 있습니다.</p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/138b21fc-9743-465c-ad0a-161201818727.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=215f2b54c37af50ed25b72755d542d80\"></p><p style=\"margin-left:0em;\">무대 위에서 연기하고 노래하는 자신을 상상해본 적이 있나요?</p><p style=\"margin-left:0em;\">연필을 입에 물고 대본과 시나리오를 고민하는<br>작가로서의 내 모습은 어떨까요?</p><p style=\"margin-left:0em;\">동선을 짜고 배우들을 서포트하며 함께 호흡하는<br>연출가는 얼마나 멋있을까요?</p><p style=\"margin-left:0em;\">내가 만든 포스터, 티켓을 들고 입장하는<br>관객들을 보면 뿌듯하지 않을까요?</p><p style=\"margin-left:0em;\">여러분의 모든 버킷리스트를 한 번에 해소시켜 줄 단 하나의 프로젝트<br><strong>&lt;과감한뮤지컬 : KICK THE BUCKET&gt;</strong>을 소개합니다.</p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/d320dd23-1cb2-4bdf-9182-33dd63d52454.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=678aab85c0ea0759a18068cebcead2c5\"></p><h2 style=\"margin-left:0em;\"><strong>1. 실화를 바탕으로 제작한 에피소드</strong></h2><p style=\"margin-left:0em;\">사람들의 버킷리스트를 함께 이루는 <strong>&lt;과감한인생&gt;</strong>은<br>그동안 다양한 버킷리스트를 함께 이뤘습니다.</p><p style=\"margin-left:0em;\">그리고 <strong>&lt;킥더버킷&gt;</strong>은 그 과정에서 겪었던 감동적인 에피소드를<br>각색해서 만든 시나리오입니다.</p><p style=\"margin-left:0em;\">실제 현대인들, 청년들이 겪고 있는 소소한 고민과 세대갈등<br>그리고 소중한 꿈에 대한 현실적인 이야기를 뮤지컬로 풀어,<br>나이와 성별에 구애받지 않고 공감할 수 있는<br>유쾌하고도 감동적인 이야기입니다.</p><h2 style=\"margin-left:0em;\"><strong>2. 현직 뮤지컬 종사자가 멘토로?!</strong></h2><p style=\"margin-left:0em;\">과감한인생의 영상을 보고 한 걸음에 달려와 준 멘토단들.<br>실제 현업에서 활동하는 연출가, 작곡가, 배우들이 멘토로 참여하여&nbsp;<br>뮤지컬 <strong>&lt;킥더버킷&gt;</strong>을 만들었습니다.</p><h2 style=\"margin-left:0em;\"><strong>3. 수제뮤지컬</strong></h2><p style=\"margin-left:0em;\">총 <strong>500번</strong>의 수정을 거쳐 완성한 대본,<br>매주 전문가들의 도움을 받으며 5달 동안 주말마다 모여 연습하는 배우들<br>그리고 이 배우들이 자신의 마음을 담아 직접 작사에도 참여했습니다.<br>전문가들의 도움을 받아 시나리오 작가부터, 가사, 안무, 동선, 무대, 조명, 음향 등을<br>참여자 모두가 직접 참여하여 만든 <strong>순도 100%의 수제 뮤지컬입니다.</strong></p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/53a319a7-e428-4a88-a16e-f5cb8b4b55f9.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=c14dc01d748109677f084c741e2d9c2d\"></p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/98f57d5c-7835-4076-bd95-8bed3e177d1f.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=22829be364a5050420ebab6e30bc124e\"><br>&nbsp;</p><blockquote><p><i><strong>2019년 가을의 한강.</strong></i><br><i><strong>저마다의 상황과 이야기로</strong></i><br><i><strong>한강에서의 저녁을 채우는 사람들로 북적인다.&nbsp;</strong></i><br><i><strong>그들을 유유히 바라보는 여유로운 표정의 남자.</strong></i><br><i><strong>금방이라도 한강물로 뛰어들 것 같은 인주를 발견하는데..</strong></i><br><br><i><strong>(중략)</strong></i></p></blockquote><blockquote><p><i><strong>그리고 다시, 흘러가는 저녁.</strong></i><br><i><strong>도대체 뭘 하는 사람들인지 모르겠다가도,</strong></i><br><i><strong>누군가의 일상에 툭 끼어드는 것이 밉지 않은 사람들, 과감한 인생.</strong></i><br><i><strong>죽음이며 사랑, 일이며 꿈, 가족. 중요해서 문제가 되는 것들 사이에서 허덕이는,</strong></i><br><i><strong>어쩌면 우리의 이야기는 늘 어떻게든 흘러간다.</strong></i><br><i><strong>멈춰있는 게 아닌 것만으로 이어지는 무언가.&nbsp;</strong></i><br><i><strong>마냥 혼자인 것 같다가도 아무렇지 않게 끼어드는</strong></i><br><i><strong>누군가가 있다.</strong></i></p></blockquote><blockquote><p><i><strong>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</strong></i></p></blockquote><p><img class=\"image_resized\" style=\"width:572px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/bf6c8adf-73c7-48aa-85ec-59a0a828dce4.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=f6366ce2db65702003202f00ca653bdb\"></p><h2 style=\"margin-left:0em;\"><strong><u>과감한 배우들</u></strong></h2><p style=\"margin-left:0em;\"><strong>강한비 김경민 김상규 김여경 김원태 박강우 박상균 박주영 박지연 배정빈</strong><br><strong>서예진 서지은 양중은 엄인수 이효주 임시현 최휘수 하은서 황성찬 황인애</strong></p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/153fb794-0c5a-420a-96f0-0a0f1a85b506.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=1b4683b5e5f3336340096871e2efd584\"></p><p style=\"margin-left:0em;\"><strong>총괄</strong> 과감한 인생<br><strong>대본</strong> 김닿아<br><strong>소품</strong> 박희원 <strong>음향</strong> 김현지 김해온 <strong>조명</strong>&nbsp; 박시영 배가영 안승현&nbsp;<br><strong>무대</strong> <strong>디자인</strong> 김유진 김원빈 그린 강민규<br><strong>디자인 및 홍보</strong> 래미 김수연<br><strong>도와주신 분들 </strong><u>김수현</u><strong><u>(음향)</u></strong><u> 문성진</u><strong><u>(작곡/연출)</u></strong><u> 황희원 </u><strong><u>(연출) </u></strong><u>전재현</u><strong><u>(연기)</u></strong><u>&nbsp; 구준모</u><strong><u>(연기)</u></strong><u> 송유택</u><strong><u>(연기)</u></strong><u> 남지영</u><strong><u>(작곡)</u></strong><u> 박슬기</u><strong><u>(작곡)</u></strong><u> 김중원</u><strong><u>(대본)</u></strong></p>")
                .budget("<p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/ee13f7a2-baa7-4cfe-bbe7-c4434fd2ee01.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=bb078bdb2b9a15360932df304bd118c3\"></p><p style=\"margin-left:0em;\">청년들의 <strong>‘자살’</strong>은 현재진행형 입니다.</p><p style=\"margin-left:0em;\"><strong>&lt;과감한뮤지컬 : Kick The Bucket&gt;</strong>은 이러한 청년들의<br>고민과 아픔이 반영된 뮤지컬인 만큼, 청년들에게 어떤 식으로<br>힘을 줄 수 있을지 고민했습니다.</p><p style=\"margin-left:0em;\">그 결과 운영비, 제작비를 제외한 순수익 전액을,<br>청년들의 자살을 예방할 수 있는 <u>자살상담 프로젝트</u>를<br>기획하는데 사용하기로 결정했습니다.</p><p style=\"margin-left:0em;\"><strong>[펀딩목표]</strong>&nbsp;</p><p style=\"margin-left:0em;\">뮤지컬을 연습을 위한 연습실 대관비, 식비, 회의비를 포함한 최소한의 운영비를 고려하여 500,000원을 목표 금액으로 정했습니다. 목표 금액을 초과 달성할 경우에는 추가 운영비를 제외한 나머지 금액을 공연의 퀄리티를 높이기 위해 사용할 예정입니다.</p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;\">&nbsp;</p><p style=\"margin-left:0em;\"><img class=\"image_resized\" style=\"width:620px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/779db05d-6ef1-40be-9842-0cbf9db3640d.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=f14f144b225ba0d14855dcf1d09f27b8\"></p><blockquote><p><strong>* 한 회당 270석</strong></p></blockquote>")
                .schedule(" <p><img class=\"image_resized\" style=\"width:572px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/7356503c-3a8c-4097-b7d2-4f14ba09d69d.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=74fe5b0ef89bb135d824e99aeaa14290\"><strong>[환불정책]</strong></p><blockquote><p><strong>1. 프로젝트 펀딩 기간 중에는 자유롭게 환불(후원 취소)이 가능합니다.</strong><br><br><strong>2. 모든 리워드는 배송해드리지 않으며, 공연 당일 내 공연장에 방문하시어</strong><br><strong>&nbsp; &nbsp;수령해주시기 바랍니다.</strong><br><br><strong>3. 공연 당일 내 리워드를 수령하시지 않을 경우 환불이 불가합니다.</strong><br><br>&nbsp;</p></blockquote><h2 style=\"margin-left:0em;\"><strong>[NOTICE]</strong></h2><blockquote><p><strong>1. 티켓 구매 전, 커뮤니티 혹은 '창작자에게 문의하기'를 통해</strong><br><strong>&nbsp; &nbsp;남은 좌석수를 확인해 주시기 바랍니다.</strong><br>&nbsp;</p></blockquote><blockquote><p><strong>2. 리워드는 sms를 통해 모바일 티켓으로 먼저 전송됩니다.</strong><br><strong>&nbsp;&nbsp;<u> </u></strong><i><strong><u>*핸드폰 번호를 정확히 작성했는지 확인 부탁드립니다.</u></strong></i><br>&nbsp;</p></blockquote><blockquote><p><strong>3. 티켓 수령시 전송받은 모바일 티켓과 신분증을 통한 본인확인을 거치셔야 합니다.</strong><br><strong>&nbsp;<u> &nbsp;</u></strong><i><strong><u>*티켓을 타인에게 양도할 경우 별도의 확인을 요청할 수 있습니다.</u></strong></i><br>&nbsp;</p></blockquote><blockquote><p><strong>4. 티켓수령은 1시간 전부터 가능합니다. 티켓에 적혀있는 입장 순서를 숙지 해 주세요.</strong></p></blockquote><blockquote><p><strong>5. 수령한 티켓에 있는 번호 순서대로 공연장에 입장하여</strong><br><strong>&nbsp; &nbsp;좌석을 선착순으로 선택하실 수 있습니다.&nbsp;</strong></p></blockquote><blockquote><p><strong>6. 원활한 관람을 위해 내부 관계자가 임의로 좌석을 지정할 수 있습니다.</strong></p></blockquote><blockquote><p><strong>7. 제작비 및 운영비를 제외한 순이익은 향후 자살을 예방할 수 있는</strong><br><strong>&nbsp; &nbsp;상담 프로젝트를 기획하는데 사용할 예정입니다.</strong></p></blockquote><h2 style=\"margin-left:0em;\"><strong>[공연관람 안내]</strong><br><br>&nbsp;</h2><blockquote><p><strong>1. 본 공연은 8세 이상 관람가입니다. 8세 미만의 어린이는 보호자 동반 시 입장이 가능&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;합니다.</strong></p></blockquote><blockquote><p><strong>2. 본 공연은 인터미션이 없이 러닝타임 100분으로 진행되며 공연 중 퇴장하실 경우 재입&nbsp; &nbsp; &nbsp;장은 불가합니다.&nbsp;</strong></p></blockquote><blockquote><p><strong>3. 본 공연은 커튼콜을 제외한 사전에 협의되지 않은 사진 및 영상 촬영, 녹음은 절대 불가&nbsp; &nbsp; &nbsp;합니다</strong><br><strong>&nbsp; &nbsp;<u> </u></strong><i><strong><u>*적발 시, 현장에서 확인 후 삭제 조치합니다.</u></strong></i></p></blockquote><blockquote><p><strong>4. 공연장 내에서는 투명한 생수 이외의 음료 및 음식물 섭취, 꽃다발과 같은 공연에 방해&nbsp; &nbsp; &nbsp; 가&nbsp; 될 수 있는 물품의 반입은 불가합니다.</strong></p></blockquote><blockquote><p><strong>5. 캐스팅은 배우 및 제작사의 사정에 따라 사전 공지 없이 변동될 수 있습니다.</strong></p></blockquote><blockquote><p><strong>6. 공연 당일 잔여석이 발생할 경우 현장 판매를 실시하며, 현장 판매에 관한 별도의 안내&nbsp; &nbsp; &nbsp; 는 공지하지 않습니다.</strong></p></blockquote><blockquote><p><strong>7. 로비 입장은 공연 1시간 전부터 가능하며, 객석 입장은 공연 시작 20분 전 부터 가능</strong><br><strong>&nbsp; &nbsp;합니다.</strong><br><i><strong><u>&nbsp; &nbsp;*지연 입장은 정해진 시간 1회에 한 하여 가능합니다.</u></strong></i></p></blockquote><blockquote><p><strong>8. 티켓 수령은 공연 시작 1시간 전부터 해당 공연장 티켓부스에서 가능합니다.</strong></p></blockquote><blockquote><p><strong>9. 공연 당일 및 취소 마감시간 이후에는 취소, 변경, 환불이 불가합니다.</strong></p></blockquote><blockquote><p><strong>10. 공연 시작 후에는 입장이 제한될 수 있으며, 예매하신 좌석이 아닌 지연석으로 안내받&nbsp; &nbsp; &nbsp; &nbsp;으실 수 있습니다.</strong><br><strong>&nbsp;&nbsp;<u> </u></strong><i><strong><u>*지연 관객석은 예매하신 좌석과 다를 수 있으며 이에 따른 환불은 불가합니다.</u></strong></i></p></blockquote><blockquote><p><strong>11. 광화문아트홀 일대의 교통이 혼잡한 관계로 가급적 대중교통을 이용해 주시기</strong><br><strong>&nbsp; &nbsp; &nbsp;바랍니다.</strong><br><strong>&nbsp;<u> &nbsp;</u></strong><i><strong><u>*주차 및 교통난으로 인해 당일 공연 관람이 불가하거나 관람을 포기한 경우 예매 취소,</u></strong></i><br><i><strong><u>&nbsp; &nbsp; 환불 및 변경이 불가합니다.</u></strong></i></p></blockquote>")
                .aboutUs("<p><img class=\"image_resized\" style=\"width:572px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/1809114b-7e3c-4847-8f9d-15f0d4bb4989.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=a502345baca39f229d954e3a5b969695\"><strong>P.S</strong></p><p style=\"margin-left:0em;\"><i><strong>시나리오를 위해 하루에도 수 십 번 글자를 지웠다 반복하고</strong></i><br><i><strong>학교 다니면서, 직장 생활하면서 틈틈이 대본과 안무를 외웠고</strong></i><br><i><strong>더 좋은 연출, 무대를 만들기 위해 밤새도록</strong></i><br><i><strong>레퍼런스와 아이디어 회의를 나눴고</strong></i><br><i><strong>우리의 모든 노력이 티켓, 포스터, 리플렛 하나하나에</strong></i><br><i><strong>담길 수 있도록 정성을 다해 만들었습니다.</strong></i></p><p style=\"margin-left:0em;\">&nbsp;</p><p><img class=\"image_resized\" style=\"width:572px;\" src=\"https://tumblbug-psi.imgix.net/a38ccb32c41bf3d6ce9c403f5ab612d814bc3ce3/b83681fdb4beb14d5cb7655445cf667cd291db8d/9039602dec340fe092a59c595d2a82d3cca5176e/9e2154bd-96a9-4760-b6c2-74ce7256be0a.jpg?ixlib=rb-1.1.0&amp;w=1240&amp;auto=format%2C%20compress&amp;lossless=true&amp;ch=save-data&amp;s=fa66567fe19e1c1adaae25e6286ec3a9\"></p>")
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
                .projectUrl("BERIOZA")
                .manageUrl(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("피아노듀오 베리오자 〈BERIOZA FESTIVAL〉 - 서울")
                .summary("피아노 듀오 베리오자 페스티벌")
                .thumbNailUrl("https://www.jnilbo.com//2019/09/09/2019090916395293988_l.jpg")
                .category("consert")
                .tag("#피아노듀오,#무대,#연주,#클래식")
                .content("<h3 style=\"margin-left:0px;\"><strong>공연상세 / 출연진정보</strong></h3><p style=\"margin-left:0px;\"><img class=\"image_resized\" style=\"width:750px !important;\" src=\"https://ticketimage.interpark.com/Play/image/etc/19/19012359-03.jpg\" alt=\"\"></p>")
                .budget("<br/>")
                .schedule("<h3 style=\"margin-left:0px;\"><strong>공연시간 정보</strong></h3><p style=\"margin-left:0px;\">예매가능시간: 전일17시(월~토 관람 시)까지/전일 11시(일요일 관람 시)까지</p><p style=\"margin-left:0px;\">&nbsp;</p><h3 style=\"margin-left:0px;\"><strong>공지사항</strong></h3><p style=\"margin-left:0px;\">피아노 듀오 베리오자 페스티벌<br>투어 공연 일정 안내<br><br>［제주］2019/9/24(화) 오후 7시 30분 서귀포 예술의전당 대극장 〈Dance with The BERIOZA〉<br>［광주］2019/9/28(토) 오후 3시 광주 문화예술회관 소극장 〈Colors of Russia〉<br>［서울］2019/10/5(토) 오후 3시 서울 JCC아트센터 콘서트홀 〈Q_ARD〉(해설 : 송현민 음악평론가)</p>")
                .aboutUs("<p style=\"margin-left:0px;\">피아노 듀오 베리오자(Berioza)는 피아니스트 전현주, 전희진 두 자매가 결성한 팀으로, 1995년부터 함께 러시아에서 유학했다. 1997년 팀 결성 후, 2005년 St.Petersburg 피아노 듀오 협회에 회원으로 등록되었으며, 협회에 소속된 최연소이자 첫 외국인 연주자이다. 2010년 독일 최고 권위의 ARD 국제 콩쿠르의 ‘피아노 듀오 부문’에서 1등 없는 2위로 우승하며 이름을 알리기 시작했다. 최근 러시아 생활을 마치고 귀국하며 팀 이름을 ‘램넌트’에서 ‘베리오자’로 개명하며 본격적인 국내 활동을 시작했다. 베리오자는 러시아의 상징인 자작나무(러시아어로 “베료자”)를 뜻하며, 러시아 정통의 피아니즘을 선보이겠다고 각오가 옅보인다.</p><p style=\"margin-left:0px;\">&nbsp;</p><p style=\"margin-left:0px;\">피아니스트 전현주는 1984년생으로 3살 때부터 피아노 시작하여 1995년 러시아, 상트-페테르부르크로 유학, 상트-페테르부르크 국립 영재음악학교에 5학년으로 입학을 하였다. 2002년에 수석으로 졸업, 같은 해 상트-페테르부르크 국립음악원에 수석 입학하였으며, 2007년 졸업 후 같은 해 12월 박사과정 입학하여, 2010년 최고점수로 음악박사학위를 취득하였다.</p><p style=\"margin-left:0px;\">&nbsp;</p><p style=\"margin-left:0px;\">피아니스트 전희진은 1987년생으로 4살 때부터 피아노를 배우기 시작하여 1995년 러시아, 상트-페테르부르크로 유학, 상트-페테르부르크 국립 영재음악학교에 3학년으로 입학하고 2004년 수석으로 졸업하였다. 같은 해 9월 상트-페테르부르크 국립음악원에 입학하여 2009년 수석으로 졸업했으며, 동 대학원 음악박사학위도 수료하였다.</p><p style=\"margin-left:0px;\">&nbsp;</p><p style=\"margin-left:0px;\">이들은 2010년 제 59회 독일 최고권위의 “뮌헨 ARD 국제콩쿠르” 피아노 듀오 부문의 우승 (1등없는 2등 수상) 및 관객상 (Publikumsprise)을 수상했으며, 그에 앞서 2009년 체코 슈베르트 피아노 듀오 국제 콩쿠르에서 1등 및 슈베르트 작곡가 특별상을, 2006년 러시아 Mariya Udina 피아노 듀오 국제 콩쿠르 1등, 2006년 리투아니아 Kaunas 피아노 듀오 국제 콩쿠르 1등을 수상했다.</p><p style=\"margin-left:0px;\">&nbsp;</p><p style=\"margin-left:0px;\">독일 뮌헨 Herkulessaal 에서 Bavarian Symphony Orchestra (지휘: Christoph Poppen)과 협연했으며, 체코 Smetana Hall, 독일 Leipzig Gewandhaus, Weimar, Dortmund, 러시아에서 다수의 리사이틀과 협연을 가졌다. 우리나라에서는 2011년 금호문화재단의 초청으로 금호아트홀에서 첫 리사이틀을 가졌으며, 2011-2012 독일 모차르트 협회 최초 한국인 장학생으로 선정되었다. 이들 듀오는 바로크 음악부터 현대음악까지 폭넓은 레파토리로 활동 중이며, 2014년에는 의 음악파트너로 선정되어 “러시안 로망스”라는 주제로 차이코프스키, 라흐마니노프, 쇼스타코비치, 아렌스키 등의 프로그램을 선보였으며 2014년 클래식FM라디오 “2014 한국의 클래식 내일의 주역들”에 선정되어 2015년 1월 음반이 발매되었다.</p><p style=\"margin-left:0px;\">2016년 1월에는 월간 객석에서 차세대 젊은 연주자로 선정되었으며 표지에 소개 되었다.</p><p style=\"margin-left:0px;\">2016년 2월 KBS 더 콘서트 출연</p><p style=\"margin-left:0px;\">2017년 8월 디토카니발 세종문화회관 이상한 나라의 디토 협연</p>")
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
