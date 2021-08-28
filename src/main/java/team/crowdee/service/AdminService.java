package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Funding;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.*;
import team.crowdee.domain.valuetype.AccountInfo;
import team.crowdee.repository.CreatorRepository;
import team.crowdee.repository.FundingRepository;
import team.crowdee.repository.MemberRepository;
import team.crowdee.util.Utils;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
@Transactional(readOnly = true)
public class AdminService {
    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;
    private final FundingRepository fundingRepository;

    /**
     * 펀딩 상태 변경 로직
     */
    public List<Funding> inspectionList() {
        List<Funding> toInspection = fundingRepository.findToInspection();
        /**
         * 위 toInspection 에 funding 들이 담겨있고 creator 도 포함되어 있음.
         * 필요한 데이터 DTO에 담아서 list로 뿌려줄것
         */
        return null;
    }

    public BackerDTO oneBacker(Long backerId) {
        Member member = memberRepository.findById(backerId);
        if (member == null) {
            return null;
        }
        return  Utils.backEToD(member);
    }

    public CreatorBackDTO oneCreator(Long createId) {
        Creator creator = creatorRepository.findById(createId);
        Member member = memberRepository.findById(createId);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountNumber(creator.getAccountInfo().getAccountNumber());
        accountInfo.setBankName(creator.getAccountInfo().getBankName());
        accountInfo.setBankBookImageUrl(creator.getAccountInfo().getBankBookImageUrl());
        System.out.println("하..."+accountInfo.getBankName());
        if (creator == null || member == null || accountInfo ==null) {
            return null;
        }
        return  Utils.creatorBackEToD(creator, member, accountInfo);

    }

    public List<AllMemberDTO> AllMember() {
        List<Member> members = memberRepository.findAll();
        if (members.isEmpty()) {
            return null;
        }
        List<AllMemberDTO> list = new ArrayList<>();
        for (int i=0; i<members.size(); i++){
            AllMemberDTO allMemberDTO = Utils.allMemberEToD(members.get(i));
            list.add(allMemberDTO);
        }
        return list;
    }
}