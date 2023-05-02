package com.example.moviereview.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"movie","member"})
@Builder
@Getter
@Entity
@Table(name = "review")
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rno")
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private int grade;
    private String text;


    //리뷰 평점, 내용 수정 메서드
    public void changeGrade(int grade){
        this.grade = grade;
    }
    public void changeText(String text){
        this.text = text;
    }

}
