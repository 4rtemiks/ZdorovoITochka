package zdorovo.tochka.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Setter;
import zdorovo.tochka.constant.BaseEnum;

public enum SubBlock implements BaseEnum {

    NONE, FOOD, SETTINGS;

    @Setter
    private String subBlock;

    @Override
    @JsonValue
    public int getOrdinal(){
        return this.ordinal();
    }
}
