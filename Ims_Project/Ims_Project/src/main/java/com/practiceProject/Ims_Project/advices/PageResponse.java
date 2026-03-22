package com.practiceProject.Ims_Project.advices;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practiceProject.Ims_Project.dto.PatientDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"totalPages", "data"})
public class PageResponse<T> {
    private int totalPages;
    private List<T> data;

    public PageResponse(int totalPages, List<T> data) {
        this.totalPages = totalPages;
        this.data = data;
    }
}
