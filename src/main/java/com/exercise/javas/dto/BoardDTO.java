package com.exercise.javas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "BoardInfo")
public class BoardDTO implements Serializable {
    @Id
    private String postId;

    @Field("userId")
    private String userId;

    @Field("name")
    private String name;

    @NotBlank(message = "제목을 입력하세요!")
    @Size(min = 5, max = 100, message = "제목은 5 ~ 100자 사이로 입력하세요!")
    @Field("title")
    private String title;

    @NotBlank(message = "글 내용을 입력하세요!")
    @Size(min = 10, max = 1000, message = "내용은 10 ~ 1000자 사이로 입력하세요!")
    @Field("content")
    private String content;

    @Field("date")
    private String date;

    @Field("hit")
    private int hit;

    @Field("reviewCnt")
    private int reviewCnt;

    @NotBlank
    @Field("boardType")
    private String boardType;

}
