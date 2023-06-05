package carrotMarcket.marcket.entity.common;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    private String regID;

    @CreatedDate
    private LocalDateTime regDate;

    private String editID;

    @LastModifiedDate
    private LocalDateTime editDate;
}
