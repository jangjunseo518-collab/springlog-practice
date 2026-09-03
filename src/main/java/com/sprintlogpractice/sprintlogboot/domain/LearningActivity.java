package com.sprintlogpractice.sprintlogboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "activities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningActivity extends BaseEntity {

  //------공통필드-------//
  @Column(nullable = false)//DB 테이블에 제약을 건다.
  private String title;

  @Column(nullable = false)
  private int minutes;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Visibility visibility;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ActivityCategory category;

  //------카테고리별 속성(필드)-------//
  @Column(length = 20)
  private String instructorName;

  private Integer completionRate;

  @Column(length = 50)
  private String bookTitle;

  public LearningActivity(String title, int minutes, Visibility visibility,
      ActivityCategory category,
      String instructorName, Integer completionRate, String bookTitle) {
    validateTitle(title);
    validateMinutes(minutes);
    this.title = title.trim();
    this.minutes = minutes;
    this.visibility = visibility;
    this.category = category;
    this.instructorName = instructorNameNormalization(category,instructorName);
    this.completionRate = completionRateNormalization(completionRate);
    this.bookTitle = bookTitleNormalization(category,bookTitle);
  }

  // 타이틀 필수, 학습 시간은 1분 이상 강제.
  private void validateTitle(String title) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("학습 제목은 비워둘 수 없습니다.");
    }
  }

  private void validateMinutes(int minutes) {
    if (minutes <= 0) {
      throw new IllegalArgumentException("학습 시가은 1분 이상이여야 합니다." + minutes);
    }
  }

  // 공개여부 변경
  public void switchToPublic() {
    this.visibility = Visibility.PUBLIC;
  }

  public void switchToPrivate() {
    this.visibility = Visibility.PRIVATE;
  }

  //강사 이름 정규화
  private static String instructorNameNormalization(ActivityCategory category,
      String instructorName) {
    if (category == ActivityCategory.LECTURE && (instructorName == null
        || instructorName.isBlank())) {
      // && (...||...): &&가 ||보다 우선 순위라서 널이거나 빈 값일때를 분리하기 위해 ||를 ()로 감싼다.
      return "강사 미정";
    }
    return instructorName;
  }

  //완료율 정규화
  private static Integer completionRateNormalization(Integer completionRate) {
    if (completionRate == null) {
      return null; //실습이 아니면 completionRate 컬럼은 null이다.
    }
    if (completionRate <= 0) {
      return 0;
    }
    if (completionRate > 100) {
      return 100;
    }
    return completionRate;
  }

  //책 제목 정규화
  private static String  bookTitleNormalization(ActivityCategory category,
      String bookTitle) {
    if (category == ActivityCategory.READING && (bookTitle == null
        || bookTitle.isBlank())) {
      return "책 미정";
    }
    return bookTitle;
  }

  //학습 시간 증가
  public void increaseInStudyTime(int additionalStudyMinutes) {
    if(additionalStudyMinutes <= 0) {
      throw new IllegalArgumentException("추가 학습시간은 1분 이상이여야 합니다.");
    }
    this.minutes += additionalStudyMinutes;
  }

  // 제목 변경
  public void changeTitle(String newTitle) {
    validateTitle(newTitle);
    this.title = newTitle.trim();
  }

}
