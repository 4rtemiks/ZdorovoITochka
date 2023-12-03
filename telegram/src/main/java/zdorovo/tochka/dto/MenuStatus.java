package zdorovo.tochka.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Setter;
import zdorovo.tochka.constant.BaseEnum;

public enum MenuStatus implements BaseEnum {

    WRITE_HEIGHT, WRITE_WEIGHT;

    @Setter
    private String status;

    @Override
    @JsonValue
    public int getOrdinal(){
        return this.ordinal();
    }
}
