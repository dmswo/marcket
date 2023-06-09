package carrotMarcket.marcket.entity;

import carrotMarcket.marcket.entity.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "Text", nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private final List<Answer> answer = new ArrayList<>();

    public void edit(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
