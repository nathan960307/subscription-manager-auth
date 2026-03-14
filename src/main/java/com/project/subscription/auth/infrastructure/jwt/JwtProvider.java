package com.project.subscription.auth.infrastructure.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.util.Date;

@Component
public class JwtProvider {

    // secret key 설정, 32자 이상의 충분히 긴 문자 사용
    private final String secretKey = "my-secret-key";

    // access token 만료 시간 설정
    private final long accessTokenExpire = 1000 * 60 * 30; // 1초(1000ms) * 60 * 30 = 30분

    // refresh token 만료 시간 설정
    private final long refreshTokenExpire = 1000 * 60 * 60 * 24 * 7; // 7일


    // at 생성
    public String createAccessToken(Long userId) {

        // 시각 객체 생성
        Date now = new Date(); // 현재 시간 객체 생성
        Date expireDate = new Date(now.getTime() + accessTokenExpire); // 만료 시각 객체 생성

        // jwt 객체 조립
        String accessToken = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now) // 토큰 생성 시각
                .setExpiration(expireDate) // 토큰 만료 시각
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();


        return accessToken;
    }

    // rt 생성
    public String createRefreshToken(Long userId) {

        // 시각 객체 생성
        Date now = new Date(); // 현재 시각 객체 설정
        Date expireDate = new Date(now.getTime() + refreshTokenExpire); // 만료 시각 객체 설정

        // jwt 객체 조립
        String refreshToken = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now) // 토큰 생성 시각
                .setExpiration(expireDate) // 토큰 만료 시각
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return refreshToken;
    }

    // token 검증
    // 1. 변조되지 않았는지
    // 2. 만료되지 않았는지

    // token에서 user id 추출
}
