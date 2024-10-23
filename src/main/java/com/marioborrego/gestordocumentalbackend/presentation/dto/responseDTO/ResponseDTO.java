package com.marioborrego.gestordocumentalbackend.presentation.dto.responseDTO;

import lombok.*;

@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
@Builder
public class ResponseDTO {
    private String status;
    private String message;
}
