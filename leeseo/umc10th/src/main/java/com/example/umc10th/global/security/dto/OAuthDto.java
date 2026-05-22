package com.example.umc10th.global.security.dto;

import com.example.umc10th.domain.member.enums.SocialType;

public interface OAuthDto {
    SocialType getSocialType();
    String getSocialUid();
    String getSocialEmail();
    String getName();
}
