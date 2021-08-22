package team.crowdee.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
public class Follow {

    @Id
    @GeneratedValue
    @Column(name = "follow_id")
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
