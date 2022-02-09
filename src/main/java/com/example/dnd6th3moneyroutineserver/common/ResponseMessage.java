package com.example.dnd6th3moneyroutineserver.common;

public class ResponseMessage {
    public static final String JOIN_SUCCESS = "회원 가입 성공";
    public static final String JOIN_FAIL = "회원 가입 실패";
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String WRITE_SUCCESS = "지출 입력 성공";
    public static final String WRITE_FAIL = "지출 입력 실패";
    public static final String STATISTICS_SUCCESS = "기간 소비 내역 조회 성공";
    public static final String STATISTICS_FAIL = "기간 소비 내역 조회 실패";
    public static final String WEEKLY_SUCCESS = "주별 소비 동향 조회 성공";
    public static final String WEEKLY_FAIL = "주별 소비 동향 조회 실패";
    public static final String MONTHLY_SUCCESS = "월별 소비 동향 조회 성공";
    public static final String MONTHLY_FAIL = "월별 소비 동향 조회 실패";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";

    // Goal
    public static final String CREATE_GOAL_SUCCESS = "목표 생성 성공";
    public static final String CREATE_GOAL_FAIL = "목표 생성 실패";


    // Category, Custom Category
    public static final String CREATE_CUSTOM_CATEGORY_SUCCESS = "카테고리 생성 성공";
    public static final String CREATE_CUSTOM_CATEGORY_FAIL = "카테고리 생성 실패";
    public static final String CATEGORY_LIST_SUCCESS = "카테고리 리스트 조회 성공";
    public static final String CATEGORY_LIST_FAIL = "카테고리 리스트 조회 실패";
}