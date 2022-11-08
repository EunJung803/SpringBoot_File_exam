package com.ll.exam.fileExam.app.fileUpload.repository;

import com.ll.exam.fileExam.app.fileUpload.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenFileRepository extends JpaRepository<GenFile, Long> {
}