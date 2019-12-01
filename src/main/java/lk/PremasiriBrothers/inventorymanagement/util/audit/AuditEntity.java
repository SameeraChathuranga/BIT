package lk.PremasiriBrothers.inventorymanagement.util.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@MappedSuperclass
@EntityListeners( AuditingEntityListener.class )
@JsonIgnoreProperties( value = {"id","createdAt", "updatedAt", "createdBy", "updatedBy"}, allowGetters = true )
public abstract class AuditEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @CreatedDate
    @Basic( optional = false )
    @Column( updatable = false, nullable = false )
    private LocalDateTime createdAt;

    @CreatedBy
    @Basic( optional = false )
    @Column( updatable = false, nullable = false )
    private String createdBy;

    @LastModifiedDate
    @Basic( optional = false )
    @Column( nullable = false )
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Basic( optional = false )
    @Column( nullable = false )
    private String updatedBy;
}
