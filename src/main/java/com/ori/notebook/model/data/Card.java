package com.ori.notebook.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "data_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    String id;
    String userId;
    String question;
    String answer;
    Date createTime;
}
