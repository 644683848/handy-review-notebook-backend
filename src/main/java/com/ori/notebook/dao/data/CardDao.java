package com.ori.notebook.dao.data;

import com.ori.notebook.model.data.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CardDao extends JpaRepository<Card, String>, JpaSpecificationExecutor<String> {
}