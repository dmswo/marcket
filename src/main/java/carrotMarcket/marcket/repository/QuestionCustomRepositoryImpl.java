package carrotMarcket.marcket.repository;

import carrotMarcket.marcket.entity.QQuestion;
import carrotMarcket.marcket.entity.QuestionStatus;
import carrotMarcket.marcket.request.QuestionListDto;
import carrotMarcket.marcket.response.QQuestionListResponse;
import carrotMarcket.marcket.response.QuestionListResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static carrotMarcket.marcket.entity.QQuestion.question;

public class QuestionCustomRepositoryImpl implements QuestionCustomRepository{

    private final JPAQueryFactory queryFactory;

    public QuestionCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page questionAllList(QuestionListDto questionListDto, Pageable pageable) {
        List<QuestionListResponse> content = queryFactory.select(new QQuestionListResponse(question.id, question.title, question.questionStatus, question.regDate))
                .from(question)
                .where(statusEq(questionListDto.getStatus())
                        , titleContains(questionListDto.getTitle()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<QuestionListResponse> count = queryFactory.select(new QQuestionListResponse(question.id, question.title, question.questionStatus, question.regDate))
                .from(question)
                .where(statusEq(questionListDto.getStatus())
                        , titleContains(questionListDto.getTitle()));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }

    private Predicate statusEq(QuestionStatus status) {
        if (status == null) {
            return null;
        }
        return question.questionStatus.eq(status);
    }

    private Predicate titleContains(String title) {
        if (title == null) {
            return null;
        }
        return question.title.contains(title);
    }
}
