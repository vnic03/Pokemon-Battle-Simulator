package org.example.Gui.battleScene;

public class BattleRoundResult {
    private String message;
    private int damageDealt;
    private boolean wasSuccessful;
    private boolean didFaint;
    private boolean isSuperEffective;
    private boolean isNotVeryEffective;
    private boolean noEffect;
    private boolean isCriticalHit;


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
    public boolean isSuperEffective() {
        return isSuperEffective;
    }
    public void setSuperEffective(boolean superEffective) {
        isSuperEffective = superEffective;
    }
    public boolean isNotVeryEffective() {
        return isNotVeryEffective;
    }
    public void setNotVeryEffective(boolean notVeryEffective) {
        isNotVeryEffective = notVeryEffective;
    }
    public boolean hasNoEffect() {return noEffect; }
    public void setNoEffect(boolean hasNoEffect) {
        noEffect = hasNoEffect;
    }
    public boolean isCriticalHit() {return isCriticalHit;}
    public void setCriticalHit(boolean isCriticalHit) {
        this.isCriticalHit = isCriticalHit;
    }

}
