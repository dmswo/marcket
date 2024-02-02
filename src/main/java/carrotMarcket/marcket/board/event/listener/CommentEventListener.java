package carrotMarcket.marcket.board.event.listener;

import carrotMarcket.marcket.board.event.CommentMailEvent;
import carrotMarcket.marcket.global.util.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final EmailSender emailSender;

    @Async
    @TransactionalEventListener
    public void handleCancelEvent(CommentMailEvent event) {
        emailSender.sendEmail(event);
    }

}
