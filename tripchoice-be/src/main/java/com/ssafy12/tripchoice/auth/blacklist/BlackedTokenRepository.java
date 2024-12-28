package com.ssafy12.tripchoice.auth.blacklist;

import org.springframework.data.repository.CrudRepository;

public interface BlackedTokenRepository extends CrudRepository<BlackedToken, Long> {
}
