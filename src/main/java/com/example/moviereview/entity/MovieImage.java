package com.example.moviereview.entity;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString (exclude = "movie")
@Entity
public class MovieImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;
    private String imgName;
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;


}
