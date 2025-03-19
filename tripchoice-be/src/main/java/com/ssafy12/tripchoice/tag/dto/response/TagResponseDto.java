package com.ssafy12.tripchoice.tag.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record TagResponseDto (List<String> content){
}
