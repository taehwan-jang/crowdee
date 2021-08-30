package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;
import team.crowdee.domain.Status;

import java.time.LocalDateTime;

@Getter
@Setter
public class FundingDTO {

    private Long fundingId;
    private String projectUrl;
    private String manageUrl;
    private String title;
    private String summary;//요약
    private String thumbNailUrl;//file이름 -> 여러건일경우 리스트?
    private String category;//카테고리
    private String tag;
    private String content;
    private String budget;
    private String schedule;
    private String aboutUs;
    private int goalFundraising;//목표금액
    private int minFundraising;//최소금액
    private String startDate;//시작일(yyyy-mm-dd)
    private String endDate;//종료일(yyyy-mm-dd)
    private int maxBacker;//최대후원자수
    private int totalBacker;//현재후원자수
    private LocalDateTime postDate;//등록일
    private int visitCount;//방문횟수(조회수)
    private int totalFundraising=0;//총 펀딩금액
    private Status status;//현재 펀딩의 상태(작성/심사/거절/진행/종료)

}
