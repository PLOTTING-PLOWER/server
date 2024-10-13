package com.plotting.server.plogging.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PloggingUserListResponse(
        Long currentPeople,
        Long maxPeople,
        List<PloggingUserResponse> ploggingUserList
) {
    public static PloggingUserListResponse of(Long currentPeople, Long maxPeople, List<PloggingUserResponse> ploggingUserList) {
        return PloggingUserListResponse.builder()
                .currentPeople(currentPeople)
                .maxPeople(maxPeople)
                .ploggingUserList(ploggingUserList)
                .build();
    }
}