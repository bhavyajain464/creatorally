package com.creatorally.centralplatform.models.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduledJob {

    private Long jobTime;
    private Integer jobValue;

}
