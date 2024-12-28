package com.ssafy12.tripchoice.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class CleanUp {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void all() {
        // 제외할 테이블 리스트 정의
        Set<String> excludeTables = Set.of("attractions", "sidos", "guguns", "contenttypes");

        // 외래 키 제약 조건 비활성화
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        //        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        // 메타모델의 엔티티 목록 가져오기 및 제외 조건 추가
        entityManager.getMetamodel().getEntities().stream()
                .map(et -> et.getJavaType().getAnnotation(Table.class).name()) // 테이블 이름 가져오기
                .filter(table -> !excludeTables.contains(table)) // 제외할 테이블 필터링
                .forEach(table ->{
                        entityManager.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
                        System.out.println("Table = " + table);
                });

        // 외래 키 제약 조건 활성화
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        //        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
