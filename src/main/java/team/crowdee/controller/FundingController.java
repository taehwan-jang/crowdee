package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.*;
import team.crowdee.jwt.CustomJWTFilter;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.FundingService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("contents")
@RequiredArgsConstructor
@CrossOrigin
public class FundingController {

    private final FundingService fundingService;
    private final MemberRepository memberRepository;
    private final CustomJWTFilter customJWTFilter;

    /**
     * token 유무 확인 후 header 값 전달
     */
    @GetMapping
    public ResponseEntity<?> showAllThumbNail() {
        //main 요청시 cache 사용 설정 추가
        CacheControl cacheControl = CacheControl
                .maxAge(60, TimeUnit.SECONDS)
                .mustRevalidate();
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(cacheControl);
        List<List<ThumbNailDTO>> thumbNail = fundingService.mainThumbNail();
        return new ResponseEntity<>(thumbNail,headers, HttpStatus.OK);
    }

    @GetMapping("/{projectUrl}")
    public ResponseEntity<?> showFundingDetail(@PathVariable String projectUrl, HttpServletRequest request) {
        String email = customJWTFilter.findEmail(request);
        FundingViewDTO fundingViewDTO = fundingService.findOneFunding(projectUrl);
        if (fundingViewDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!fundingViewDTO.getMemberList().isEmpty() && StringUtils.hasText(email)) {
            if (fundingViewDTO.getMemberList().contains(email)) {
                fundingViewDTO.setWish(true);
            }
        } else {
            fundingViewDTO.setWish(false);
        }
        return new ResponseEntity<>(fundingViewDTO, HttpStatus.OK);
    }

    /**
     * 검색 가져오기
     * 1.tag--ok
     * 2.제목 --ok
     * 3.nickName--ok
     * 3.카테고리--ok
     * 4.creatorNickName
     */
    @PostMapping("/search")
    public ResponseEntity<?> searchTag(@RequestBody SearchDTO searchDTO) {
        List<ThumbNailDTO> thumbNailDTO = fundingService.searchFunding(searchDTO);
        if (thumbNailDTO.isEmpty()) {
            return new ResponseEntity<>("검색결과가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(thumbNailDTO, HttpStatus.OK);
    }
    @PostMapping("/menuList")
    public ResponseEntity<?> selectMenu(@RequestBody String menu) {

        List<ThumbNailDTO> thumbNailDTOList = fundingService.selectedMenu(menu);
        if (thumbNailDTOList.isEmpty()) {
            return new ResponseEntity<>("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(thumbNailDTOList,HttpStatus.OK);
    }


    /**
     * 찜하기 로직
     */
    @PostMapping("/wishOrUnWish")
    public ResponseEntity<?> wishList(@RequestBody Long fundingId,HttpServletRequest request){
        String email = customJWTFilter.findEmail(request);
        if (!StringUtils.hasText(email)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean result = fundingService.addOrRemoveMemberToWishFunding(email, fundingId);
        WishDTO wishDTO = new WishDTO(result);
        return new ResponseEntity<>(wishDTO, HttpStatus.OK);
    }


    /**
     * 참여하기 로직
     * 0. 사용자가 입력했던 기존 정보들을 바탕으로 먼제 값을 내려준다?
     * 1. projectUrl? fundingId?인덱스로 찾는게 더 나으니 인덱스를 보내라 하자
     * 2. paymentDTO 통해 결제에 필요한 정보들 수집
     * 4. 결제금액 , 최소금액 비교
     * 3. fundingId 를 통해 funding 객체를 가져오고 Order 객체 생성
     * 5.
     */
    @PostMapping("/preOrder")
    public ResponseEntity<?> responseBasicInfo(HttpServletRequest request) {
        boolean flag = customJWTFilter.isBacker(request);
        if (!flag) {
            return new ResponseEntity<>("로그인이 필요합니다.",HttpStatus.BAD_REQUEST);
        }
        String email = customJWTFilter.findEmail(request);
        List<Member> memberList = memberRepository.findByEmail(email);
        if (memberList.isEmpty()) {
            return new ResponseEntity<>("로그인이 필요합니다.",HttpStatus.BAD_REQUEST);
        }
        Member member = memberList.get(0);
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setBuyer_email(member.getEmail());
        paymentDTO.setBuyer_name(member.getUserName());
        paymentDTO.setBuyer_tel(member.getMobile());
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }
    @PostMapping("/participation")
    public ResponseEntity<?> participation(@RequestBody PaymentDTO paymentDTO,
                                           HttpServletRequest request) throws Exception {
        //https://smujihoon.tistory.com/103 결제 관련 참고 로직
        boolean flag = customJWTFilter.isBacker(request);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        fundingService.participation(paymentDTO.getFundingId(),paymentDTO,paymentDTO.getBuyer_email());
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
