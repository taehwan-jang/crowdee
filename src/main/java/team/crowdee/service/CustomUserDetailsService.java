package team.crowdee.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Member;
import team.crowdee.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    // 로그인 시 DB 에서 member 정보를 조회하는 메소드(권한정보와 함께)
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findOneWithAuthoritiesByUserId(userId)
                .stream().map(member -> createUser(userId, member))
                .findAny().orElseThrow(() -> new UsernameNotFoundException(userId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    // DB 에서 조회한 member 및 권한정보를 'org.springframework.security.core.userdetails.User' 객체로 변환하여 리턴
    private org.springframework.security.core.userdetails.User createUser(String userId, Member member){
        if(member==null){ // 수정필요한 코드
            log.info("//==========로그인 실패시 로직===========//");
            throw new RuntimeException(userId + " -> 활성화되어 있지 않습니다.");
        }
        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(member.getEmail(),
                member.getPassword(),
                grantedAuthorities);
    }
}
