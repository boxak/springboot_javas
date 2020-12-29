package com.exercise.javas.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Document(collection = "UserInfo")
public class UserDTO implements Serializable {
    @Id
    @Field("id")
    @NotBlank(message = "아이디를 입력하세요!")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$",
            message = "아이디는 숫자와 영문 소문자의 4 ~ 15자 조합이어야 합니다!")
    private String id;

    @NotBlank(message = "비밀번호를 입력하세요!")
    @Pattern(regexp = "^[0-9a-zA-Z`~!@#$%^&*()-_+=|{};:\"'<>,.?]{6,20}$",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상 포함되어야 합니다!")
    @Field("password")
    private String password;

    @NotBlank(message = "이름을 입력하세요!")
    @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣]{2,20}$",
            message = "이름은 2 ~ 20자의 한글이어야 합니다!")
    @Field("name")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요!")
    @Field("email")
    @Email(message = "이메일 형식에 맞게 입력해주세요!")
    private String email;

    @NotBlank(message = "생년월일을 입력해주세요!")
    @Field("birthday")
    @Pattern(regexp = "^(19\\d{2}|20(0|1)\\d)-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d{1}|30|31)$",
            message = "생년월일은 0000-00-00 형식에 맞게 입력해주세요!")
    private String birthday;

    @NotBlank(message = "성별을 입력해주세요!")
    @Pattern(regexp = "^(female|male)$",
            message = "성별은 female, male로만 입력해주세요!")
    @Field("sex")
    private String sex;

    @NotBlank(message = "휴대폰 번호를 입력해주세요!")
    @Pattern(regexp = "^(010|011|012)-\\d{3,4}-\\d{4}$",
            message = "유효한 휴대폰 번호가 아닙니다!")
    @Field("phone")
    private String phone;

    @NotBlank(message = "주소를 입력해주세요!")
    @Field("address")
    private String address;

    @NotBlank
    @Field("date")
    private String date;

    @NotBlank(message = "활동 유형을 선택해주세요!")
    @Pattern(regexp = "^(jobad|wantad)$",
            message = "유효한 타입이 아닙니다!")
    @Field("userType")
    private String userType;
}
