package zdorovo.tochka.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Setter;
import zdorovo.tochka.constant.BaseEnum;

public enum MenuStatus implements BaseEnum {

    NONE,  WRITE_HEIGHT, WRITE_WEIGHT;

    @Setter
    private String status;

    @Override
    @JsonValue
    public int getOrdinal(){
        return this.ordinal();
    }
}
