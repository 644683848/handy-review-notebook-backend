package com.ori.notebook.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    LocalDate createTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="label_card",joinColumns={@JoinColumn(name="card_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="label_id",referencedColumnName="id")}
    )
    private Set<Label> labels = new HashSet<>();
}
