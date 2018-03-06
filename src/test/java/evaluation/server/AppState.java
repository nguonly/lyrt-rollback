package evaluation.server;

import evaluation.roles.AES;
import evaluation.roles.LZ;
import evaluation.roles.LZX;

/**
 * Created by nguonly on 5/10/17.
 */
public class AppState {
    static Channel channel;
    private static AppState appState;

    static int evaluationMode = 0; //0: Baseline, 1: Restart, 2: Rollback

    public AppState(){}

    public static synchronized AppState getInstance(){
        if(appState == null){
            appState = new AppState();
        }
        return appState;
    }

}
