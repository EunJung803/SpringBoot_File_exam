package com.ll.exam.fileExam.app.member.entity;

import com.ll.exam.fileExam.app.base.AppConfig;
import com.ll.exam.fileExam.app.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.File;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String profileImg;

    public Member(long id) {
        super(id);
    }

    public void removeProfileImgOnStorage() {   // 스토리지에서 지우게 된다 프로필 이미지를
        if (profileImg == null || profileImg.trim().length() == 0) return;  // 예외처리

        String profileImgPath = getProfileImgPath();

        new File(profileImgPath).delete();
    }

    private String getProfileImgPath() {    // 프로필 이미지의 경로를 얻는 클래스
        return AppConfig.GET_FILE_DIR_PATH + "/" + profileImg;
        // 엔티티 내부에 절대 경로를 넣을 수 없기에 AppConfig을 만들어서 사용
    }

    public String getProfileImgUrl() {
        if (profileImg == null) {
            return null;
        }
        return "/gen/" + profileImg;
    }
}