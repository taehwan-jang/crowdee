package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.CreatorDTO;
import team.crowdee.domain.valuetype.AccountInfo;
import team.crowdee.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CreatorService {

    private final MemberRepository memberRepository;

    @Transactional
    public Creator joinCreator(CreatorDTO creatorDTO){
        /**
         * 검증 시작
         * 소셜 가입 회원인 경우 추가 정보를 입력하게만들기~
         */
        boolean result = validation(creatorDTO);
        if (!result) {
            throw new IllegalArgumentException("빈값이 있다.");
        }
        Member member = memberRepository.findById(creatorDTO.getMemberId());
        AccountInfo account = new AccountInfo();
        account.setAccountNumber(creatorDTO.getAccountNumber());
        account.setBankBookImageUrl(creatorDTO.getBankBookImageUrl());
        account.setBankName(creatorDTO.getBankName());
        Creator creator = Creator.builder()
                .creatorNickName(creatorDTO.getCreatorNickName())
                .BusinessNumber(creatorDTO.getBusinessNumber())
                .accountInfo(account)
                .build();

        member.joinCreator(creator);
        return creator;
    }

    //검증은 한번에 진행
    public boolean validation(CreatorDTO creatorDTO) {
        if(StringUtils.hasText(creatorDTO.getAccountNumber())){
            return false;
        }
        if(StringUtils.hasText(creatorDTO.getBankName())){
            return false;
        }
        if(StringUtils.hasText(creatorDTO.getCreatorNickName())){
            return false;
        }
        if(StringUtils.hasText(creatorDTO.getBankBookImageUrl())){
            return false;
        }
        return true;
    }




}
