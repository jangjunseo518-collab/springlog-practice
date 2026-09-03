package com.sprintlogpractice.sprintlogboot.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LearningActivityTest {


  @Test
  @DisplayName("정상적인 값으로 생성")
  void 정싱적인_깂으로_객체를_생성한다() {
    //When
    LearningActivity learningActivity = new LearningActivity(
        "타이틀", 1, Visibility.PUBLIC, ActivityCategory.LECTURE,
        "김강사",null, null
    );

    //Then
    assertEquals("타이틀",  learningActivity.getTitle());
    assertEquals(1, learningActivity.getMinutes());
    assertEquals(Visibility.PUBLIC, learningActivity.getVisibility());
    assertEquals(ActivityCategory.LECTURE, learningActivity.getCategory());
    assertEquals("김강사", learningActivity.getInstructorName());
    assertNull(learningActivity.getBookTitle());

  }

  @Test
  @DisplayName("제목이 null이면 예외가 발생한다.")
  void 제목이_null이면_예외() {
    // when & then
    assertThrows(IllegalArgumentException.class, () ->
     new LearningActivity(null, 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, "김강사", null, null)
    );
  }

  @Test
  @DisplayName("제목이 빈 문자열이면 예외가 발생한다.")
  void 제목이_빈_문자열이면_예외() {
    // when & then
    assertThrows(IllegalArgumentException.class, () ->
        new LearningActivity("", 1, Visibility.PRIVATE,
            ActivityCategory.LECTURE, "김강사", null, null)
    );
  }

  @Test
  @DisplayName("제목이 공백이면 예외가 발생한다.")
  void 제목이_공백이면_예외() {
    // when & then
    assertThrows(IllegalArgumentException.class, () ->
        new LearningActivity(" ", 1, Visibility.PRIVATE,
            ActivityCategory.LECTURE, "김강사", null, null)
    );
  }


  @Test
  @DisplayName("minutes가 0이면 예외가 발생한다.")
  void minutes가_0이면_예외() {
    // when & then
    assertThrows(IllegalArgumentException.class, () ->
        new LearningActivity("제목", 0, Visibility.PRIVATE,
            ActivityCategory.LECTURE, "김강사", null, null)
    );
  }

  @Test
  @DisplayName("minutes가 음수이면 예외가 발생한다.")
  void minutes가_음수이면_예외() {
    // when & then
    assertThrows(IllegalArgumentException.class, () ->
        new LearningActivity("제목", -1, Visibility.PRIVATE,
            ActivityCategory.LECTURE, "김강사", null, null)
    );
  }

  @Test
  @DisplayName("강사이름이 null인 경우 강사 미정으로 값이 채워진다.")
  void 강사이름이_null이면_강사미정으로_값이_채워진다() {
    //When
    LearningActivity learningActivity = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, null, null, null);

    //Then
    assertEquals("강사 미정",  learningActivity.getInstructorName());
  }

  @Test
  @DisplayName("강사이름이 빈 문자열 또는 공백인 경우 강사 미정으로 값이 채워진다.")
  void 강사이름이_빈_문자열_또는_공백이면_강사미정으로_값이_채워진다() {
    //When
    LearningActivity learningActivity = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, "", null, null);
    LearningActivity learningActivity2 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, " ", null, null);


    //Then
    assertEquals("강사 미정",  learningActivity.getInstructorName());
    assertEquals("강사 미정",   learningActivity2.getInstructorName());
  }

  @Test
  @DisplayName("카테고리가 강의가 아닐 떄 강사 이름에 null을 넣으면 null로 들어간다.")
  void 이하_동문(){
    LearningActivity learningActivity = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.READING, null, null, null);

    assertNull(learningActivity.getInstructorName());
  }

  @Test
  @DisplayName("완료율에 음수 또는 100을 초과하는 수를 넣으면 0 또는 100으로 보정된다.")
  void 완료율에_잘_못된_값을_넣으면_값이_보정된다() {
    LearningActivity learningActivity1 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, "김강사", -1, null);
    LearningActivity learningActivity2 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, "김강사", 101, null);

    //Then
    assertEquals(0, learningActivity1.getCompletionRate());
    assertEquals(100, learningActivity2.getCompletionRate());
  }

  @Test
  @DisplayName("독서 카테고리에서 책 제목을 null로 두면 책 미정으로 값이 들어온다.")
  void 독서카테고리애서_제목을_null로두면_책_미정이_값으로_들어온다() {
    LearningActivity learningActivity1 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.READING, null, 1, null);

    //Then
    assertEquals("책 미정", learningActivity1.getBookTitle());
  }


  @Test
  @DisplayName("독서 카테고리가 아닐 때 책 제목을 null로 두면 null로 유지된다.")
  void 독서카테고리가_아닐_때_제목을_null로두면_null이_유지된다() {
    LearningActivity learningActivity1 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, null, 1, null);

    //Then
    assertNull(learningActivity1.getBookTitle());
  }

  @Test
  @DisplayName("힉습 시간 증가")
  void 학습_시간이_증가한다() {
    // given
    LearningActivity learningActivity1 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, null, 1, null);


    // when
    learningActivity1.increaseInStudyTime(9);

    // then
    assertEquals(10,learningActivity1.getMinutes());
  }

  @Test
  @DisplayName("힉습 시간 증가 예외")
  void 학습_시간이을_0증가시키면_예외가_터진다() {
    // given
    LearningActivity learningActivity1 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, null, 1, null);


    // when & then
    assertThrows(IllegalArgumentException.class, () ->
    learningActivity1.increaseInStudyTime(0));
  }

  @Test
  @DisplayName("힉습 시간 증가 예외")
  void 학습_시간이을_음수로_증가시키면_예외가_터진다() {
    // given
    LearningActivity learningActivity1 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, null, 1, null);


    // when & then
    assertThrows(IllegalArgumentException.class, () ->
        learningActivity1.increaseInStudyTime(-1));
  }

  @Test
  @DisplayName("제목 변경")
  void 제목을_변경_한다() {
    // given
    LearningActivity learningActivity1 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, null, 1, null);


    // when
    learningActivity1.changeTitle("제목2");

    // then
    assertEquals("제목2",learningActivity1.getTitle());
  }

  @Test
  @DisplayName("변경할 제목이 null이면 예외")
  void 변경할_제목이_null이면_예외() {
    // given
    LearningActivity learningActivity1 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, null, 1, null);


    // when & then
    assertThrows(IllegalArgumentException.class, () ->
        learningActivity1.changeTitle(null));
  }

  @Test
  @DisplayName("비공개로 변경")
  void 비공개로_변경_한다() {
    // given
    LearningActivity learningActivity1 = new LearningActivity("제목", 1, Visibility.PUBLIC,
        ActivityCategory.LECTURE, null, 1, null);

    // when
    learningActivity1.switchToPrivate();

    // then
    assertEquals(Visibility.PRIVATE,learningActivity1.getVisibility());
  }

  @Test
  @DisplayName("공개로 변경")
  void 공개로_변경_한다() {
    // given
    LearningActivity learningActivity1 = new LearningActivity("제목", 1, Visibility.PRIVATE,
        ActivityCategory.LECTURE, null, 1, null);

    // when
    learningActivity1.switchToPublic();

    // then
    assertEquals(Visibility.PUBLIC,learningActivity1.getVisibility());
  }
  
}