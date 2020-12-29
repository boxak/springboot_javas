package com.exercise.javas.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ReviewDTO implements Serializable {
    @Field("reviewId")
    private String reviewId;
    @Field("id")
    private String reviewerId;
    @Field("comment")
    private String comment;
    @Field("date")
    private String date;
    @Field("rate")
    private float rate;
}
