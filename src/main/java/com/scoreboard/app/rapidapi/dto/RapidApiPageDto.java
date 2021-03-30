package com.scoreboard.app.rapidapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RapidApiPageDto {

    @JsonProperty(value = "total_pages")
    private Integer totalPages;
    @JsonProperty(value = "current_page")
    private Integer currentPage;
    @JsonProperty(value = "next_page")
    private Integer nextPage;
    @JsonProperty(value = "per_page")
    private Integer perPage;
    @JsonProperty(value = "total_count")
    private Integer totalCount;

}
