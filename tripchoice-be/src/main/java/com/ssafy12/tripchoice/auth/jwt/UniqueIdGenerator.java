package com.ssafy12.tripchoice.auth.jwt;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UniqueIdGenerator {
    private static AtomicInteger seq = new AtomicInteger();
    public long generate(){
        long id = ((long) Instant.now().getNano()) << 32 | seq.incrementAndGet();
        seq.compareAndSet(Integer.MAX_VALUE, 0);
        return id; //todo timestamp:serverId:seq 형태의 유일 아이디 생성
    }
}
