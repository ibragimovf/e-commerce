package uz.pdp.restservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@MappedSuperclass
@Getter
@Setter
public abstract class BaseModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    private boolean isInActive;

    @CreationTimestamp
    private Timestamp createdDate;

    @CreationTimestamp
    private Timestamp updatedDate;


    public BaseModelEntity(long id) {
        this.id = id;
    }

    public BaseModelEntity() {
    }
}
