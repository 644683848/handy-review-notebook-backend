package com.ori.notebook.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "data_label")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Label {
    @Id
    String id;
    String userId;
    String labelName;

//    @JsonIgnore
//    @ManyToMany(mappedBy="labels")
//    private Set<Card> cards = new HashSet<>(0);
}
