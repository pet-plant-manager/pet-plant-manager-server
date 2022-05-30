package domain.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notice {
    long pk;
    long targetAccId;

    long targetPetId;
    String content;
    LocalDate noticedTime;

}