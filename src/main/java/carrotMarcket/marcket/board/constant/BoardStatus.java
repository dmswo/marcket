package carrotMarcket.marcket.board.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BoardStatus {
    WAIT, PROCESSING, COMPLETED;

    @JsonCreator
    public static BoardStatus statusEnum(String val){
        for(BoardStatus statusEnum : BoardStatus.values()){
            if(statusEnum.name().equals(val)){
                return statusEnum;
            }
        }
        return null;
    }
}
