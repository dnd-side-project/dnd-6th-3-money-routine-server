package com.example.dnd6th3moneyroutineserver.common;

public class ResponseMessage {
    public static final String JOIN_SUCCESS = "회원 가입 성공";
    public static final String JOIN_FAIL = "회원 가입 실패";

    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";

    public static final String WRITE_SUCCESS = "지출 입력 성공";
    public static final String WRITE_FAIL = "지출 입력 실패";

    public static final String WEEKLY_STATISTICS_SUCCESS = "주간 소비 통계, 내역 조회 성공";
    public static final String WEEKLY_STATISTICS_FAIL = "주간 소비 통계, 내역 조회 실패";

    public static final String MONTHLY_STATISTICS_SUCCESS = "월간 소비 통계 조회 성공";
    public static final String MONTHLY_STATISTICS_FAIL = "월간 소비 통계 조회 실패";

    public static final String MONTHLY_DETAILS_SUCCESS = "월간 소비 내역 조회 성공";
    public static final String MONTHLY_DETAILS_FAIL = "월간 소비 내역 조회 실패";

    public static final String WEEKLY_TENDENCY_SUCCESS = "주별 소비 동향 조회 성공";
    public static final String WEEKLY_TENDENCY_FAIL = "주별 소비 동향 조회 실패";
    public static final String MONTHLY_TENDENCY_SUCCESS = "월별 소비 동향 조회 성공";
    public static final String MONTHLY_TENDENCY_FAIL = "월별 소비 동향 조회 실패";

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";

    // Goal, GoalCategory
    public static final String CREATE_GOAL_SUCCESS = "목표 생성 성공";
    public static final String CREATE_GOAL_FAIL = "목표 생성 실패";
    public static final String GOAL_INFO_SUCCESS = "목표 정보 조회 성공";
    public static final String GOAL_INFO_FAIL = "목표 정보 조회 실패";
    public static final String CONTINUE_SUCCESS = "이전 달의 목표 연장 성공";
    public static final String CONTINUE_FAIL = "이전 달의 목표 연장 실패";
    public static final String BUDGET_CHANGE_SUCCESS = "전체 예산 변경 성공";
    public static final String BUDGET_CHANGE_FAIL = "전체 예산 변경 실패";
    public static final String REMOVE_GOAL_CATEGORY_SUCCESS = "목표 카테고리 삭제 성공";
    public static final String REMOVE_GOAL_CATEGORY_FAIL = "목표 카테고리 삭제 실패";
    public static final String MODIFY_GOAL_CATEGORY_SUCCESS = "목표 카테고리 수정 성공";
    public static final String MODIFY_GOAL_CATEGORY_FAIL = "목표 카테고리 수정 실패";
    public static final String DIRECT_ADD_SUCCESS = "목표 카테고리 직접 추가 성공";
    public static final String DIRECT_ADD_FAIL = "목표 카테고리 직접 추가 실패";


    // Category, Custom Category
    public static final String CREATE_CUSTOM_CATEGORY_SUCCESS = "카테고리 생성 성공";
    public static final String CREATE_CUSTOM_CATEGORY_FAIL = "카테고리 생성 실패";
    public static final String CATEGORY_LIST_SUCCESS = "카테고리 리스트 조회 성공";
    public static final String CATEGORY_LIST_FAIL = "카테고리 리스트 조회 실패";
    public static final String GOAL_CATEGORY_LIST_SUCCESS = "목표 카테고리 리스트 조회 성공";
    public static final String GOAL_CATEGORY_LIST_FAIL = "목표 카테고리 리스트 조회 성공";
    public static final String EXCEPT_CATEGORY_LIST_SUCCESS = "지출 분야 추가 리스트 조회 성공";
    public static final String EXCEPT_CATEGORY_LIST_FAIL = "지출 분야 추가 리스트 조회 실패";
}