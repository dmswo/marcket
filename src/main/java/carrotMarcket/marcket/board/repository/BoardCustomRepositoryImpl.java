package carrotMarcket.marcket.board.repository;

import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.response.BoardListResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static carrotMarcket.marcket.board.entity.QBoard.board;

public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> boardList(BoardStatus status, String title, Pageable pageable) {
        List<Board> content = queryFactory.selectFrom(board)
                .where(statusEq(status)
                        , titleContains(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Board> count = queryFactory.selectFrom(board)
                .where(statusEq(status)
                        , titleContains(title));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }

    private Predicate statusEq(BoardStatus status) {
        if (status == null) {
            return null;
        }
        return board.boardStatus.eq(status);
    }

    private Predicate titleContains(String title) {
        if (title == null) {
            return null;
        }
        return board.title.contains(title);
    }
}
