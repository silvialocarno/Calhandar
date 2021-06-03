package it.polimi.db2.calhandar.entities.Enum;

public enum Commitment_type {
    LESSON("LESSON"),
    MEETING("MEETING"),
    PERSONAL_COMMITMENT("PERSONAL COMMITMENT");

    private String name;

    Commitment_type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
