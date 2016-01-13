package com.sinjvf.tetris;

/**
 * Created by sinjvf on 09.01.16.
 */

public interface ActionResolver   {
    public boolean getSignedInGPGS();
    public void loginGPGS();
    public void submitScoreGPGS(int score);
    public void unlockAchievementGPGS(String achievementId);
    public void getLeaderboardGPGS();
    public void getAchievementsGPGS();
    public void onShowAchievementsRequested() ;
}