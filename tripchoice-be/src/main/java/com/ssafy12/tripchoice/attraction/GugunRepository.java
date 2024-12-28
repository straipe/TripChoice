package com.ssafy12.tripchoice.attraction;

import com.ssafy12.tripchoice.attraction.domain.GuGun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GugunRepository extends JpaRepository<GuGun, Long> {
    List<GuGun> findAllBySidoCode(Long sidoCode);
}
