package org.example.screens.battleScene;

public class BattleRoundResult {
    private String message;
    private int damageDealt;
    private boolean wasSuccessful;
    private boolean didFaint;

    public BattleRoundResult(String message, int damageDealt, boolean wasSuccessful, boolean didFaint) {
        this.message = message;
        this.damageDealt = damageDealt;
        this.wasSuccessful = wasSuccessful;
        this.didFaint = didFaint;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDamageDealt() {
        return damageDealt;
    }
    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }
    public boolean wasSuccessful() {
        return wasSuccessful;
    }
    public void setWasSuccessful(boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }
    public boolean didFaint() {
        return didFaint;
    }
    public void setDidFaint(boolean didFaint) {
        this.didFaint = didFaint;
    }
}
