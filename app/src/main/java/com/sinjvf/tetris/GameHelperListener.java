package com.sinjvf.tetris;

/**
 * Created by sinjvf on 09.01.16.
 */
public interface GameHelperListener {
    /**
     * Вызывается при неудачной попытке входа. В этом методе можно показать
     * пользователю кнопку «Sign-in» для ручного входа
     */
    void onSignInFailed();

    /** Вызывается при удачной попытке входа */
    void onSignInSucceeded();
}