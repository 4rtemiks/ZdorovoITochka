package zdorovo.tochka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zdorovo.tochka.dto.BaseBlock;
import zdorovo.tochka.dto.MenuStatus;
import zdorovo.tochka.dto.SubBlock;

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

    @Enumerated(EnumType.ORDINAL)
    private BaseBlock baseBlock;
    @Enumerated(EnumType.ORDINAL)
    private SubBlock subBlock;
    @Enumerated(EnumType.ORDINAL)
    private MenuStatus status;

}
