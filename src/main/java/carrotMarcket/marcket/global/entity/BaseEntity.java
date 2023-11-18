package carrotMarcket.marcket.global.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseEntity {

    @CreatedBy
    @Column(updatable = false)
    private String regID;

    @CreatedDate
    private LocalDateTime regDate;

    @LastModifiedBy
    private String editID;

    @LastModifiedDate
    private LocalDateTime editDate;
}
