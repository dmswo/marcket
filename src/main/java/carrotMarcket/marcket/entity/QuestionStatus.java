package carrotMarcket.marcket.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum QuestionStatus {
    WAIT, PROCESSING, COMPLETED;

    @JsonCreator
    public static QuestionStatus statusEnum(String val){
        for(QuestionStatus statusEnum : QuestionStatus.values()){
            if(statusEnum.name().equals(val)){
                return statusEnum;
            }
        }
        return null;
    }
}
