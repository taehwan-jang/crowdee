package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
//변경감지되야해서 컨트롤럴에서 옮김
    private final MemberRepository memberRepository;

    @Transactional
    public Creator joinCreator(CreatorDTO creatorDTO){
        /**
         * 검증 시작
         */
        //검증 메서드 호출
       /* this.validationMemberId(creatorDTO)*/
        //검증 메서드 호출
        this.validationBankName(creatorDTO);

//검증메소드에서 빈값 검증해서 이게 필요한것인지...
//        if(){
//            return null;
//        }
        /////////////////검증 완료 후 진행////////////
        ////////////////////////////////////////////
        Member member = memberRepository.findById(creatorDTO.getMemberId());
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountNumber(creatorDTO.getAccountNumber());
        accountInfo.setBankBookImageUrl(creatorDTO.getBankBookImageUrl());
        accountInfo.setBankName(creatorDTO.getBankName());
        Creator creator = Creator.builder()
                .creatorId(creatorDTO.getMemberId())
                .BusinessNumber(creatorDTO.getBusinessNumber())
                .build();
        member.joinCreator(creator);
        return creator;
    }

    //memberID 값들어있나 검증
    public boolean validationMemberId(CreatorDTO creatorDTO) {
        if(creatorDTO.getMemberId()==null){
            return false;
        }
        return true;
    }

    //계좌번호 값들어있나 검증
    public boolean validationBankName(CreatorDTO creatorDTO) {
        if(creatorDTO.getBankName()==null){
            return false;
        }
        return true;
    }
}
