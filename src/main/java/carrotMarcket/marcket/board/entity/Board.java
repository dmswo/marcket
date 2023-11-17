package carrotMarcket.marcket.board.entity;

import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.entity.common.BaseEntity;
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

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private final List<Comment> reply = new ArrayList<>();

    public void edit(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
