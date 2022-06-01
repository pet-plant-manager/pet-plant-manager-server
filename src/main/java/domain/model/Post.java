package domain.model;

import lombok.*;

import java.io.File;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Post {
    private long pk;
    private String petPk;
    private String title;
    private String content;
    private Date postedTime;
    private File photo;
}