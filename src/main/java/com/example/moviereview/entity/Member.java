package com.example.moviereview.entity;

import lombok.*;


import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "m_member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    private Long mid;
    private String email;
    private String pw;
    private String nickname;
}
