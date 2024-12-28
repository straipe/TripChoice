package com.ssafy12.tripchoice.attraction;

import com.ssafy12.tripchoice.algo.JamoUtils;
import com.ssafy12.tripchoice.algo.trie.ChosungTrie;
import com.ssafy12.tripchoice.algo.trie.PrefixTrie;
import com.ssafy12.tripchoice.algo.trie.Trie;
import com.ssafy12.tripchoice.attraction.domain.Attraction;
import com.ssafy12.tripchoice.attraction.dto.AttractionSearchDto;
import com.ssafy12.tripchoice.attraction.dto.response.AttractionDto;
import com.ssafy12.tripchoice.attraction.dto.response.GugunDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanSearchDto;
import com.ssafy12.tripchoice.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionService {

    static Trie prefixTrie = PrefixTrie.getInstance();
    static Trie chosungTrie = ChosungTrie.getInstance();
    static List<String> titles;
    private final AttractionRepository attractionRepository;
    private final AttractionQueryRepository attractionQueryRepository;
    private final GugunRepository gugunRepository;


    public AttractionDto getById(Integer attractionId) {
        return attractionRepository.findById(attractionId)
                .map(this::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public Page<AttractionDto> search(AttractionSearchDto dto) {
        return attractionQueryRepository.search(dto).map(this::toDto);
    }

    public List<AttractionDto> searchTop4(){
        return attractionQueryRepository.searchTop4().stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    private AttractionDto toDto(Attraction attraction) {
        return AttractionDto.builder()
                .no(attraction.getNo())
                .title(attraction.getTitle())
                .contentType(attraction.getContentType().getContentTypeName())
                .imgUrl(attraction.getFirstImage1())
                .address(attraction.getAddr1().concat(attraction.getAddr2()))
                .latitude(attraction.getLocation().latitude())
                .longitude(attraction.getLocation().longitude())
                .build();
    }

    public List<GugunDto> getGuguns(Long sidoCode) {
        return gugunRepository.findAllBySidoCode(sidoCode).stream()
                .map(g -> GugunDto.builder()
                        .text(g.getGugunName())
                        .value(g.getGugunCode()).build())
                .toList();
    }

    public List<String> autoComplete(String keyword) {
        if (titles == null) {
            titles = attractionRepository.findAll().stream().map(Attraction::getTitle).toList();
            prefixTrie.init(titles);
            chosungTrie.init(titles);
        }
        return searchAndGetTitle(keyword);
    }

    private static List<String> searchAndGetTitle(String keyword) {
        System.out.println("=====search START=====");
        System.out.println("Search Keyword = " + keyword);
        List<String> searchResult;
        if (JamoUtils.isChosungString(keyword)) {
            searchResult = chosungTrie.searchAll(keyword);
        } else {
            searchResult = prefixTrie.searchAll(keyword);
        }

        return searchResult.stream().limit(10).toList();

    }
}
