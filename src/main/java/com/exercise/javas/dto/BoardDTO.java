package com.exercise.javas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$",
            message = "유효한 아이디 형식이 아닙니다!")
    @Field("id")
    private String id;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣]{2,20}$",
            message = "유효한 이름이 아닙니다!")
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

    @NotBlank
    @Pattern(regexp = "^(202\\d)-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|30|31) ([0-1]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$")
    @Field("date")
    private String date;

    @Field("hit")
    private int hit;

    @Field("reviewCnt")
    private int reviewCnt;

    @Nullable
    @Field("reviewList")
    @JsonProperty("reviewList")
    private transient List<ReviewDTO> reviewList;

    @NotBlank
    @Field("boardType")
    private String boardType;

}
