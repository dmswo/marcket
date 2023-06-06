package carrotMarcket.marcket.entity;

import carrotMarcket.marcket.entity.common.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ANSWER_ID")
    private Long id;

    @Column(columnDefinition = "Text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
}
