package carrotMarcket.marcket.board.entity;

import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.global.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "Text", nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    @ColumnDefault("0")
    private Long views;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private final List<Comment> comment = new ArrayList<>();

    public void update(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public void updateViews(Long views) {
        this.views = views;
    }
}
