package com.ori.notebook.model.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    String id;
    String username;
    String nickname;
    String password;
    String salt;

}
