package uz.pdp.restservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "db.user")
public class User extends BaseModelEntity{
    private String userName;
    private String fullName;

}


