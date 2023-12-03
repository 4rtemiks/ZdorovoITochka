package zdorovo.tochka.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Setter;
import zdorovo.tochka.constant.BaseEnum;

public enum SubBlock implements BaseEnum {

    FOOD, SETTINGS;

    @Setter
    private String subBlock;

    @Override
    @JsonValue
    public int getOrdinal(){
        return this.ordinal();
    }
}
