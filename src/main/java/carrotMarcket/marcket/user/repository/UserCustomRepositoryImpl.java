package carrotMarcket.marcket.user.repository;

import carrotMarcket.marcket.board.entity.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static carrotMarcket.marcket.board.entity.QBoard.board;

public class UserCustomRepositoryImpl implements UserCustomRepository{

    private final JPAQueryFactory queryFactory;

    public UserCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public List<Board> myBoardList(Long userId) {
        List<Board> list = queryFactory.selectFrom(board)
                .where(board.regID.eq(String.valueOf(userId)))
                .fetch();
        return list;
    }
}
