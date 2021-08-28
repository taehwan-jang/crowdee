package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.dto.FundingDTO;
import team.crowdee.domain.dto.PaymentDTO;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.jwt.CustomJWTFilter;
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
    public ResponseEntity<?> showFundingDetail(@PathVariable String projectUrl) {
        FundingDTO fundingDTO = fundingService.findOneFunding(projectUrl);
        if (fundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingDTO, HttpStatus.OK);
    }

    /**ㄴㅇㄹ
     * 검색 가져오기
     * 1.tag--okㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㅇㄹㅁ
     * 2.제목
     * 3.카테고리--ok
     * 4.creatorNickName
     */
    @PostMapping("/tag")
    public ResponseEntity<?> searchTag(@RequestBody String tag) {
        List<ThumbNailDTO> thumbNailDTO = fundingService.tagView(tag);
        if (thumbNailDTO.isEmpty()) {
            return new ResponseEntity<>("검색결과가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(thumbNailDTO, HttpStatus.OK);
    }
    @PostMapping("/category")
    public ResponseEntity<?> searchCategory(@RequestBody String category) {
        List<ThumbNailDTO> thumbNailDTO = fundingService.categoryView(category);
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


    /**
     * 참여하기 로직
     *
     * 1. projectUrl? fundingId?인덱스로 찾는게 더 나으니 인덱스를 보내라 하자
     * 2. paymentDTO 통해 결제에 필요한 정보들 수집
     * 4. 결제금액 , 최소금액 비교
     * 3. fundingId 를 통해 funding 객체를 가져오고 Order 객체 생성
     * 5.
     */
    @PostMapping("/participation")
    public ResponseEntity<?> participation(@RequestBody Long fundingId,
                                           @RequestBody PaymentDTO paymentDTO,
                                           HttpServletRequest request) {
        //https://smujihoon.tistory.com/103 결제 관련 참고 로직
        boolean flag = customJWTFilter.isBacker(request);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String email = customJWTFilter.findEmail(request);
        fundingService.participation(fundingId,paymentDTO,email);


        return null;
    }



}
