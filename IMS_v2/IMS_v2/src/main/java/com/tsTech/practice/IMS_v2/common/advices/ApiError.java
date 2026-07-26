package com.tsTech.practice.IMS_v2.common.advices;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

/////////////////////////////////////////////
//
// Name: Api Error
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1002 - patient mst apis)
// v1.2 || type : Change || Jul 23, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

//Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes) - changed from data to following annotations
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = {"status"})
@Builder
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> subErrors;
}
