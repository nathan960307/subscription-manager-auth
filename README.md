# Auth Server

JWT 기반 인증 서버

---

## 프로젝트 개요

사용자의 인증 및 인가를 처리하는 서버로,  
JWT 기반 Stateless 인증 구조를 통해 확장성과 성능을 고려하여 설계했습니다.

본 서버는 인증과 비즈니스 로직을 분리한 구조에서  
인증을 전담하는 역할을 수행합니다.

---

## 기술 스택
- Java 21
- Spring Boot
- Spring Security
- JWT
- Redis
- Docker

---

## 주요 기능

- 로그인 및 JWT 발급 (Access / Refresh Token)
- Access Token 검증
- Refresh Token 기반 재발급
- 로그아웃 처리 (토큰 무효화)
- JWT 기반 인증/인가 (Stateless)

---

## 아키텍처

    [ Client ]
         ↓
    [ Auth Server ] → Redis (토큰 / 블랙리스트)

---

## 인증 흐름

1. 사용자가 로그인 요청
2. 서버가 Access / Refresh Token 발급
3. 클라이언트는 Access Token으로 API 요청
4. 만료 시 Refresh Token으로 재발급 요청
5. 로그아웃 시 Redis에 토큰 무효화 처리

---

## 설계 포인트

- Stateless 인증 구조 (세션 미사용)
- Access / Refresh Token 분리 설계
- Redis를 활용한 토큰 관리 및 TTL 기반 만료 처리
- 인증 / 인가 로직 분리

---

## 트러블슈팅

### 토큰 중복 발급 문제 (동시성)

- 문제  
  토큰 재발급 시 동시에 여러 요청이 들어오면 중복 발급 발생

- 해결  
  Redis 분산 락과 TTL을 적용하여 한 번에 하나의 요청만 처리되도록 개선

- 결과  
  중복 토큰 발급 방지 및 인증 안정성 확보

---

### 로그아웃 후 토큰 재사용 문제

- 문제  
  로그아웃 이후에도 기존 Access Token으로 요청 가능

- 해결  
  Redis 기반 블랙리스트를 도입하여 토큰 무효화 처리

---

## 설계 문서

- 기획서 및 설계서  
  https://www.notion.so/2f59c512d83480079a57fa688289a1b3

---

## 연관 프로젝트

- Business Server  
  https://github.com/nathan960307/subscription-manager-business

- 현재 서비스는 AWS EC2 환경에서 운영 중
- 보안상 실제 주소는 공개하지 않음
