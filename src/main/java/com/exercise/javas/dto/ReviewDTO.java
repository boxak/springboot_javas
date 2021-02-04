package com.exercise.javas.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Document(collection = "ReviewInfo")
public class ReviewDTO implements Serializable {
    @Id
    private String reviewId;
    @Field("postId")
    private String postId;
    @Field("reviewerId")
    private String reviewerId;
    @Field("targetId")
    private String targetId;
    @Field("comment")
    private String comment;
    @Field("date")
    private String date;
    @Field("rate")
    private double rate;
}
