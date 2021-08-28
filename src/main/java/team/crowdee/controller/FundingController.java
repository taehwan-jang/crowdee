package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.dto.FundingDTO;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.service.FundingService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("contents")
@RequiredArgsConstructor
@CrossOrigin
public class FundingController {

    private final FundingService fundingService;

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

    /**
     * 검색 가져오기
     * 1.tag--ok
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

    /*
    1. 신규등록펀딩(startdate)
    2. 방문자가많은펀딩(visitcount)
    3. 마감임박펀딩(enddate)
    4. 성공임박(달성률)(80<rateofachvement<100)
    5. 초과달성펀딩(ROA>100%)
    */





    /**
     * 찜하기 로직
     */


    /**
     * 참여하기 로직
     */



}
