package ru.mario.player;

public class PlayerData {

    private boolean isCraft;
    private boolean isFoundExit;
    private boolean isMined;
    private int ores;

    PlayerData(boolean isCraft, boolean isFoundExit, boolean isMined, int ores) {
        this.isCraft = isCraft;
        this.isFoundExit = isFoundExit;
        this.isMined = isMined;
        this.ores = ores;
    }

    public boolean isCraft() {
        return isCraft;
    }

    public boolean isFoundExit() {
        return isFoundExit;
    }

    public boolean isMined() {
        return isMined;
    }

    public int getOres() {
        return ores;
    }

    public void setCraft(boolean craft) {
        isCraft = craft;
    }

    public void setFoundExit(boolean foundExit) {
        isFoundExit = foundExit;
    }

    public void setMined(boolean mined) {
        isMined = mined;
    }

    public void addOre(){
        this.ores++;
    }
}
