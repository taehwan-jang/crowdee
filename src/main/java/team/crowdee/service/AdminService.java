package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.*;
import team.crowdee.domain.dto.*;
import team.crowdee.repository.CreatorRepository;
import team.crowdee.repository.FundingRepository;
import team.crowdee.repository.MemberRepository;
import team.crowdee.util.MimeEmailService;
import team.crowdee.util.Utils;

import javax.mail.MessagingException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
@Transactional(readOnly = true)
public class AdminService {
    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;
    private final MimeEmailService mimeEmailService;
    private final FundingRepository fundingRepository;

    //어드민로그인(필요없다 생각되어 주석)
    public void makeAdmin(Long memberId) {
        Member member = memberRepository.findById(memberId);
        Set<Authority> authorities = new HashSet<>();
        Authority admin = Authority.builder().authorityName("admin").build();
        authorities.add(admin);
        member.setAuthorities(authorities);
    }

    //백커 전체조회
    public List<BackerAllDTO> backerAll() {
        List<Member> backer = memberRepository.findByStatus(Status.member);
        List<Member> creator = memberRepository.findByStatus(Status.confirm);

        if (backer.isEmpty()) {
            return null;
        }
        List<BackerAllDTO> list = new ArrayList<>();
        for(Member member : backer) {
            BackerAllDTO backerAllDTO = Utils.allBackEToD(member);
            list.add(backerAllDTO);
        }
        for(Member member : creator) {
            BackerAllDTO backerAllDTO = Utils.allBackEToD(member);
            list.add(backerAllDTO);
        }
        return list;
    }

    //크리에이터 전체조회
    public List<CreatorAllDTO> creatorAll() {
        List<Creator> creator = creatorRepository.findByStatus(Status.confirm);

        if (creator.isEmpty()) {
            return null;
        }
        List<CreatorAllDTO> list = new ArrayList<>();
        for(Creator creator1 : creator) {
            CreatorAllDTO creatorAllDTO = Utils.allCreatorEToD(creator1);
            list.add(creatorAllDTO);
        }
        return list;
    }

    //백커 상세조회
    public BackerViewDTO oneBacker(Long backerId) {
        if (backerId == null) {
            return null;
        }
        Member member = memberRepository.findById(backerId);
        return Utils.backViewEToD(member);
    }


    //크리에이터 상세조회
    public CreatorViewDTO oneCreator(Long createId) {
        List<Creator> creatorList = creatorRepository.findByIdMember(createId);

        if (creatorList.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 크리에이터입니다.");
        }
        return Utils.creatorViewEToD(creatorList.get(0));
    }

    //크리에이터 심사중 전체조회
    public List<CreatorViewDTO> inspectionAll() {
        List<Creator> creatorList = creatorRepository.findByStatus(Status.inspection);

        if(creatorList.isEmpty()){
            return null;
        }
        List<CreatorViewDTO> list = new ArrayList<>();
        for(Creator creator : creatorList) {
            CreatorViewDTO inspectionDTO = Utils.inspectionEToD(creator);
            list.add(inspectionDTO);
        }
        return list;
    }

    //크리에이터심사 승인
    @Transactional
    public Member confirmChange(Long creatorId) {
        //쿼리 수정 findById->findByIdWithMember
        List<Creator> creatorList = creatorRepository.findByIdMember(creatorId);

        if (creatorList.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 크리에이터 입니다.");
        }
        Creator creator = creatorList.get(0);
        if (!(creator.getStatus().equals(Status.inspection))) {
            return null;
        }
        Member member = creator.getMember();
        Authority authority = Authority.builder()
                .authorityName("creator")
                .build();
        member.acceptCreator(authority);
        return member;
    }

    //크리에이터심사 거절
    @Transactional
    public Member rejectCreator(Long creatorId, RejectionDTO rejectionDTO) throws MessagingException {
        List<Creator> creatorList = creatorRepository.findByIdMember(creatorId);

        Creator creator = creatorList.get(0);
        if (!(creator.getStatus().equals(Status.inspection))) {
            return null;
        }
        Member member = creator.getMember();
        mimeEmailService.rejectCreatorMail(member, rejectionDTO);
        member.rejectCreator();
        return member;
    }

    //펀딩 전체조회
    public List<FundingAllDTO> fundingAll() {
        List<Funding> fundingList = fundingRepository.findAll();

        if (fundingList.isEmpty()) {
            return null;
        }
        List<FundingAllDTO> list = new ArrayList<>();
        for(Funding funding : fundingList) {
            if(!(funding.getStatus().equals(Status.inspection))){
                FundingAllDTO fundingAllDTO = Utils.allFundingEToD(funding);
                list.add(fundingAllDTO);
            }
        }
        return list;
    }

    //펀딩 심사중 전체조회
    public List<FundingAllDTO> inspectionFunding() {
        List<Funding> fundingList = fundingRepository.findByStatus(Status.inspection);

        if(fundingList.isEmpty()){
            return null;
        }
        List<FundingAllDTO> list = new ArrayList<>();
        for(Funding funding : fundingList) {
            FundingAllDTO inspectionDTO = Utils.allFundingEToD(funding);
            list.add(inspectionDTO);
        }
        return list;
    }

    //펀딩심사 승인
    @Transactional
    public Funding confirmFunding(Long fundingId) {
        Funding funding = fundingRepository.findById(fundingId);
        if (!(funding.getStatus().equals(Status.inspection) && funding.getCreator().getStatus().equals(Status.confirm))) {
            return null;
        }

        funding.acceptFunding();
        return funding;
    }

    //펀딩심사 거절(프론트랑 같이 테스트했을때 되면 OK)
    @Transactional
    public Funding rejectFunding(Long fundingId, RejectionDTO rejectionDTO)
            throws MessagingException {
        Funding funding = fundingRepository.findById(fundingId);
        if (!(funding.getStatus().equals(Status.inspection))) {
            return null;

        }

        String email = funding.getCreator().getMember().getEmail();
        List<Member> memberList = memberRepository.findByEmail(email);
        Member member = memberList.get(0);
        mimeEmailService.rejectFundingMail(member, funding, rejectionDTO);
        funding.rejectFunding();
        return funding;
    }




///////////////////////아래는이메일테스트



    //크리에이터심사 거절테스트용
    @Transactional
    public Member rejectCreatorTest(Long creatorId) throws MessagingException {
        List<Creator> creatorList = creatorRepository.findByIdMember(creatorId);
        Creator creator = creatorList.get(0);
        if (!(creator.getStatus().equals(Status.inspection))) {
            return null;
        }
        Member member = creator.getMember();
        mimeEmailService.rejectFundingMailTest(member);
        member.rejectCreator();
        return member;



    }



    //펀딩심사 거절 수정중 미완성..
    @Transactional
    public Funding rejectFundingTest(Long fundingId)
            throws MessagingException {
        Funding funding = fundingRepository.findById(fundingId);
        if (!(funding.getStatus().equals(Status.inspection))) {
            return null;
        }
        String email = funding.getCreator().getMember().getEmail();
        List<Member> memberList = memberRepository.findByEmail(email);
        Member member = memberList.get(0);
        mimeEmailService.rejectFundingMailTest(member, funding);
        funding.rejectFunding();
        return funding;
    }
}