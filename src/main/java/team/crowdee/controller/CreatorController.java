package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Funding;
import team.crowdee.domain.dto.*;
import team.crowdee.jwt.CustomJWTFilter;
import team.crowdee.repository.FundingRepository;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.CreatorService;
import team.crowdee.service.FundingService;
import team.crowdee.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/creator")
@Slf4j
public class CreatorController {

    private final CreatorService creatorService;
    private final FundingRepository fundingRepository;

    //크리에이터 등록
    @PostMapping("/signCreator")
    public ResponseEntity<?> creatorMember(@RequestBody CreatorDTO creatorDTO,HttpServletRequest request) {
        boolean flag = CustomJWTFilter.isBacker(request);
        if (!flag) {
            return new ResponseEntity<>("일반회원만 신청할 수 있습니다.", HttpStatus.FORBIDDEN);
        }
        Creator joinCreator = creatorService.joinCreator(creatorDTO);
        if(joinCreator == null) {
            return new ResponseEntity<>("크리에이터 등록에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("심사목록에 추가되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/checkUrl/{projectUrl}")
    public ResponseEntity<?> checkProjectUrl(@PathVariable String projectUrl) {
        List<Funding> fundingList = fundingRepository.findByParam("projectUrl", projectUrl);
        if (!fundingList.isEmpty()) {
            return new ResponseEntity<>("이미 존재하는 Url입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("사용할 수 있는 Url입니다.", HttpStatus.OK);

    }

    @GetMapping("/create/funding/{projectUrl}")
    public ResponseEntity<?> createFunding(@PathVariable String projectUrl, HttpServletRequest request) {
        boolean flag = CustomJWTFilter.isCreator(request);
        if (!flag) {
            return new ResponseEntity<>("크리에이터만 펀딩을 등록할 수 있습니다.", HttpStatus.FORBIDDEN);
        }
        String email = CustomJWTFilter.findEmail(request);

        //토큰으로부터 멤버를 꺼내와서 creatorId 를 받아온다? ㅇㅇ 받아오는편이 좋을듯 먼저 확인 후 일단 클라에서 전송받는다 가정
        FundingDTO tempFundingDTO = creatorService.tempFunding(projectUrl,email);
        if (tempFundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tempFundingDTO, HttpStatus.OK);
    }

    /**
     * manaageUrl을 통한 funding 객체 찾기
     */
    @PostMapping("/create/thumbNail/{manageUrl}")
    public ResponseEntity<?> createTempThumbNail(@RequestBody ThumbNailDTO thumbNailDTO,
                                                 @PathVariable String manageUrl,
                                                 HttpServletRequest request) {
        boolean flag = CustomJWTFilter.isCreator(request);
        if (!flag) {
            return new ResponseEntity<>("크리에이터만 펀딩을 등록할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        FundingDTO fundingDTO = creatorService.tempThumbNail(thumbNailDTO,manageUrl);
        if (fundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingDTO,HttpStatus.OK);
    }



    @PostMapping("/create/fundingPlan/{manageUrl}")
    public ResponseEntity<?> createTempFundingPlan(@RequestBody FundingPlanDTO fundingPlanDTO,
                                                   @PathVariable String manageUrl,
                                                   HttpServletRequest request) {
        boolean flag = CustomJWTFilter.isCreator(request);
        if (!flag) {
            return new ResponseEntity<>("크리에이터만 펀딩을 등록할 수 있습니다.", HttpStatus.FORBIDDEN);
        }
        FundingDTO fundingDTO = creatorService.tempFundingPlan(fundingPlanDTO,manageUrl);
        if (fundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingDTO,HttpStatus.OK);
    }

    @PostMapping("/create/detail/{manageUrl}")
    public ResponseEntity<?> createTempDetail(@RequestBody DetailDTO detailDTO,
                                              @PathVariable String manageUrl,
                                              HttpServletRequest request) {
        boolean flag = CustomJWTFilter.isCreator(request);
        if (!flag) {
            return new ResponseEntity<>("크리에이터만 펀딩을 등록할 수 있습니다.", HttpStatus.FORBIDDEN);
        }
        FundingDTO fundingDTO = creatorService.tempDetail(detailDTO,manageUrl);
        if (fundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingDTO,HttpStatus.OK);
    }

    @GetMapping("/create/{manageUrl}")
    public ResponseEntity<?> askInspection(@PathVariable String manageUrl) {

        return null;
    }



}
