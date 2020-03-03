package com.ori.notebook.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
