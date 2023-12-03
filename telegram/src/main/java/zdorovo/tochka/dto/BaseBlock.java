package zdorovo.tochka.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Setter;
import zdorovo.tochka.constant.BaseEnum;

public enum BaseBlock implements BaseEnum {

    FOOD, SETTINGS;


    @Setter
    private String baseBlock;

    @Override
    @JsonValue
    public int getOrdinal(){
        return this.ordinal();
    }
}
