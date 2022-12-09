package com.ums.usermanagementsystem.entity.reponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse {
    private String resultMessage;

    public BaseResponse(String resultMessage) {
        this.resultMessage = resultMessage;
    }


}
