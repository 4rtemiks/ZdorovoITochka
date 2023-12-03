package zdorovo.tochka.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private Long chatId;

    @Column(columnDefinition = "decimal(4,4) comment 'Member weigth in kg'")
    private BigDecimal weight;
    @Column(columnDefinition = "decimal(4,4) comment 'Member height in cm'")
    private BigDecimal height;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+3")
    private Date registrationTime;

    //#todo params like memberLevel and etc

}
