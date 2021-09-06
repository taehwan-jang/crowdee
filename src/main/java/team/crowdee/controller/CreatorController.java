package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Funding;
import team.crowdee.domain.dto.*;
import team.crowdee.jwt.CustomJWTFilter;
import team.crowdee.repository.CreatorRepository;
import team.crowdee.repository.FundingRepository;
import team.crowdee.service.CreatorService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/creator")
@Slf4j
public class CreatorController {

    private final CreatorService creatorService;
    private final CreatorRepository creatorRepository;
    private final FundingRepository fundingRepository;
    private final CustomJWTFilter customJWTFilter;

    @GetMapping("/isBacker")
    public ResponseEntity<?> isBacker(HttpServletRequest request) {
        boolean creator = customJWTFilter.isCreator(request);
        if (creator) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        boolean backer = customJWTFilter.isBacker(request);
        if (backer) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/validationNickName")
    public ResponseEntity<?> validationNickName(HttpServletRequest request,@RequestBody String nickName) {
        boolean creator = customJWTFilter.isCreator(request);
        if (!creator) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Creator> creatorList = creatorRepository.findByNickName(nickName);
        if (creatorList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    //크리에이터 등록
    @PostMapping("/signCreator")
    public ResponseEntity<?> creatorMember(@RequestBody CreatorDTO creatorDTO, HttpServletRequest request) {
        boolean flag = customJWTFilter.isBacker(request);

        if (!flag) {
            return new ResponseEntity<>("일반회원만 신청할 수 있습니다.", HttpStatus.FORBIDDEN);
        }
        String email = customJWTFilter.findEmail(request);
        Creator joinCreator = creatorService.joinCreator(creatorDTO,email);
        if (joinCreator == null) {
            return new ResponseEntity<>("크리에이터 등록에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("심사목록에 추가되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/project-start")
    public ResponseEntity<?> checkAuthority(HttpServletRequest request) {
        boolean creator = customJWTFilter.isCreator(request);
        boolean admin = customJWTFilter.isAdmin(request);
        if (creator||admin) {
            return new ResponseEntity<>(HttpStatus.OK);//200
        }
        boolean backer = customJWTFilter.isBacker(request);
        if (backer) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//401
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);//403

    }

    @GetMapping("/checkUrl/{projectUrl}")
    public ResponseEntity<?> checkProjectUrl(@PathVariable String projectUrl) {
        List<Funding> fundingList = fundingRepository.findByParam("projectUrl", projectUrl);
        if (!fundingList.isEmpty()) {
            return new ResponseEntity<>("이미 존재하는 Url입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("사용할 수 있는 Url입니다.", HttpStatus.OK);

    }

    @PostMapping("/create/funding/{projectUrl}")
    public ResponseEntity<?> createFunding(@PathVariable String projectUrl, HttpServletRequest request) {
        boolean flag = customJWTFilter.isCreator(request);
        boolean flag2 = customJWTFilter.isAdmin(request);
        if (!(flag||flag2)) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.FORBIDDEN);
        }

        String email = customJWTFilter.findEmail(request);
        FundingViewDTO tempFundingDTO = creatorService.tempFunding(projectUrl, email);
        if (tempFundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tempFundingDTO, HttpStatus.OK);
    }

    /**
     * manageUrl을 통한 funding 객체 찾기
     */
    @PostMapping("/create/thumbNail/{manageUrl}")
    public ResponseEntity<?> createTempThumbNail(@RequestBody ThumbNailDTO thumbNailDTO,
                                                 @PathVariable String manageUrl,
                                                 HttpServletRequest request) {
        boolean flag = customJWTFilter.isCreator(request);
        boolean flag2 = customJWTFilter.isAdmin(request);
        if (!(flag||flag2)) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.FORBIDDEN);
        }

        FundingViewDTO fundingViewDTO = creatorService.tempThumbNail(thumbNailDTO, manageUrl);
        if (fundingViewDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingViewDTO, HttpStatus.OK);
    }

    @PostMapping("/create/fundingPlan/{manageUrl}")
    public ResponseEntity<?> createTempFundingPlan(@RequestBody FundingPlanDTO fundingPlanDTO,
                                                   @PathVariable String manageUrl,
                                                   HttpServletRequest request) {
        boolean flag = customJWTFilter.isCreator(request);
        boolean flag2 = customJWTFilter.isAdmin(request);
        if (!(flag||flag2)) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.FORBIDDEN);
        }
        FundingViewDTO fundingViewDTO = creatorService.tempFundingPlan(fundingPlanDTO, manageUrl);
        if (fundingViewDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingViewDTO, HttpStatus.OK);
    }

    @PostMapping("/create/detail/{manageUrl}")
    public ResponseEntity<?> createTempDetail(@RequestBody DetailDTO detailDTO,
                                              @PathVariable String manageUrl,
                                              HttpServletRequest request) {
        boolean flag = customJWTFilter.isCreator(request);
        boolean flag2 = customJWTFilter.isAdmin(request);
        if (!(flag||flag2)) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.FORBIDDEN);
        }

        FundingViewDTO fundingViewDTO = creatorService.tempDetail(detailDTO, manageUrl);
        if (fundingViewDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingViewDTO, HttpStatus.OK);
    }

    @GetMapping("/create/preview/{manageUrl}")
    public ResponseEntity<?> previewTempFunding(@PathVariable String manageUrl,
                                                HttpServletRequest request) {
        boolean flag = customJWTFilter.isCreator(request);
        boolean flag2 = customJWTFilter.isAdmin(request);
        if (!(flag||flag2)) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.FORBIDDEN);
        }
        FundingViewDTO fundingViewDTO = creatorService.showPreview(manageUrl);
        return new ResponseEntity<>(fundingViewDTO, HttpStatus.OK);
    }

    @GetMapping("/create/{manageUrl}")
    public ResponseEntity<?> askInspection(@PathVariable String manageUrl,
                                           HttpServletRequest request) {
        boolean flag = customJWTFilter.isCreator(request);
        boolean flag2 = customJWTFilter.isAdmin(request);
        if (!(flag||flag2)) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.FORBIDDEN);
        }
        FundingViewDTO fundingViewDTO = creatorService.changeStatus(manageUrl);
        return new ResponseEntity<>(fundingViewDTO, HttpStatus.OK);
    }

    @GetMapping("/create/editingList")
    public ResponseEntity<?> editingFundingList(HttpServletRequest request) {
        boolean flag = customJWTFilter.isCreator(request);
        boolean flag2 = customJWTFilter.isAdmin(request);
        if (!(flag||flag2)) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.FORBIDDEN);
        }
        String email = customJWTFilter.findEmail(request);
        List<EditingListDTO> editingList = creatorService.findEditingList(email);
        if (editingList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(editingList,HttpStatus.OK);
    }


    @GetMapping("/edit/{manageUrl}")
    public ResponseEntity<?> editingFunding(@PathVariable String manageUrl,
                                            HttpServletRequest request) {
        boolean flag = customJWTFilter.isCreator(request);
        boolean flag2 = customJWTFilter.isAdmin(request);
        if (!(flag||flag2)) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.FORBIDDEN);
        }
        String email = customJWTFilter.findEmail(request);
        FundingViewDTO fundingViewDTO = creatorService.findTempFunding(email,manageUrl);
        if (fundingViewDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingViewDTO, HttpStatus.OK);

    }
}
