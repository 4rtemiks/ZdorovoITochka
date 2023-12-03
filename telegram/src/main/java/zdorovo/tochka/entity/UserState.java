package zdorovo.tochka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zdorovo.tochka.constant.BaseBlock;
import zdorovo.tochka.constant.MenuStatus;
import zdorovo.tochka.constant.SubBlock;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long chatId;
    private Long memberId;

    private Long createTime = System.currentTimeMillis();

    private Integer messageId;
    private String callbackData;

    @Enumerated(EnumType.ORDINAL)
    private BaseBlock baseBlock = BaseBlock.NONE;
    @Enumerated(EnumType.ORDINAL)
    private SubBlock subBlock = SubBlock.NONE;
    @Enumerated(EnumType.ORDINAL)
    private MenuStatus status = MenuStatus.NONE;

    private BigDecimal height;
    private BigDecimal weight;
}
