package com.tsTech.practice.IMS_v2.patient.dto.records;

import java.util.Set;

public record NextPriorityRecord (
        String nextPriority,
        Set<String> currentPriorities
) {
}
