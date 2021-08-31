package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.*;
import team.crowdee.domain.dto.*;
import team.crowdee.repository.CreatorRepository;
import team.crowdee.repository.FundingRepository;
import team.crowdee.repository.MemberRepository;
import team.crowdee.util.Utils;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
@Transactional(readOnly = true)
public class AdminService {
    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final FundingRepository fundingRepository;

    //어드민로그인
    public String login(LoginDTO loginDTO) {
        List<Member> byEmail = memberRepository.findByEmail(loginDTO.getEmail());
        if (byEmail.isEmpty() && !(byEmail.get(0).getSecessionDate() == null)) {
            throw new IllegalArgumentException("존재하지않는 회원입니다.");
        }
        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), byEmail.get(0).getPassword());
        if (!matches) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        Set<Authority> authorities = byEmail.get(0).getAuthorities();
        Iterator<Authority> iterator = authorities.iterator();
        String admin = null;
        while (iterator.hasNext()) {
            admin = iterator.next().getAuthorityName();
        }
        return admin;
    }

    //백커 전체조회
    public List<BackerAllDTO> backerAll() {
        List<Member> backer = memberRepository.findByStatus(Status.member);
        List<Member> creator = memberRepository.findByStatus(Status.confirm);
        if (backer.isEmpty()) {
            return null;
        }
        List<BackerAllDTO> list = new ArrayList<>();
        for (int i = 0; i < backer.size(); i++) {
            BackerAllDTO backerAllDTO = Utils.allBackEToD(backer.get(i));
            list.add(backerAllDTO);
        }
        for(int i=0; i<creator.size(); i++) {
            BackerAllDTO backerAllDTO = Utils.allBackEToD(creator.get(i));
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
        for (int i = 0; i < creator.size(); i++) {
            CreatorAllDTO creatorAllDTO = Utils.allCreatorEToD(creator.get(i));
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
        Creator creator = creatorRepository.findById(createId);

        if (creator == null) {
            return null;
        }
        return Utils.creatorViewEToD(creator);

    }

    //심사중 전체조회
    public List<CreatorViewDTO> inspectionAll() {
        List<Creator> creator = creatorRepository.findByStatus(Status.inspection);

        if(creator.isEmpty()){
            return null;
        }
        List<CreatorViewDTO> list = new ArrayList<>();
        for(int i=0; i<creator.size(); i++) {
            CreatorViewDTO inspectionDTO = Utils.inspectionEToD(creator.get(i));
            list.add(inspectionDTO);
        }
        return list;
    }

    //크리에이터심사 승인
    @Transactional
    public Member confirmChange(Long creatorId) {
        Creator creator = creatorRepository.findById(creatorId);
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

    //거절 미완성
    @Transactional
    public void refuseChange(Long memberId){
        Member member = memberRepository.findById(memberId);

    }

    //펀딩 전체조회
    public List<FundingAllDTO> fundingAll() {
        List<Funding> fundingList = fundingRepository.findAll();

        if (fundingList.isEmpty()) {
            return null;
        }
        List<FundingAllDTO> list = new ArrayList<>();
        for (int i = 0; i < fundingList.size(); i++) {
            if(!(fundingList.get(i).getStatus().equals(Status.inspection))){
                FundingAllDTO fundingAllDTO = Utils.allFundingEToD(fundingList.get(i));
                list.add(fundingAllDTO);
            }
        }
        return list;
    }

    //심사중 전체조회
    public List<FundingAllDTO> inspectionFunding() {
        List<Funding> inspectionList = fundingRepository.findByStatus(Status.inspection);

        if(inspectionList.isEmpty()){
            return null;
        }
        List<FundingAllDTO> list = new ArrayList<>();
        for(int i=0; i<inspectionList.size(); i++) {
            FundingAllDTO inspectionDTO = Utils.allFundingEToD(inspectionList.get(i));
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

    //펀딩심사 거절
    @Transactional
    public Funding rejectFunding(Long fundingId) {
        Funding funding = fundingRepository.findById(fundingId);
        if (!(funding.getStatus().equals(Status.inspection))) {
            return null;
        }

        funding.rejectFunding();
        return funding;
    }
}