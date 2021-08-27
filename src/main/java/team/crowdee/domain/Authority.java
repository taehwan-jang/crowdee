package team.crowdee.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Authority {
    @Id
    @GeneratedValue
    @Column(name ="authority_id",length = 50)
    private Long authorityId;

    @Column(name = "authority_name",length = 50)
    private String authorityName;
}
