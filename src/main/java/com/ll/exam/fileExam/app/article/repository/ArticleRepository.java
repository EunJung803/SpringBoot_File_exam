package com.ll.exam.fileExam.app.article.repository;

import com.ll.exam.fileExam.app.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}