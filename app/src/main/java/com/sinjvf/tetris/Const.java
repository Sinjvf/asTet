package com.sinjvf.tetris;

import android.graphics.Color;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by sinjvf on 20.11.15.
 */
public class Const {
//Global settings
    public static final int STANDART = 0;
    public static final int AWRY = 1;
    public static final int HEX = 2;
    public static final int SHIFTX = 2;  //x shift of grid
    public static final int TRACE = 1; //width of line in grid
    public static final int NEW_GAME_LAYOUT = 0;
    public static final int RESULTS_LAYOUT = 1;
    public static final int SCORE_FOR_RESULTS = -1;
    //colors of fields in game
    public static final int COLOR_NULL = -1;
    public static final int COLOR_CORE_RED = Color.argb(255, 255, 0, 0);
    public static final int COLOR_EDGE_RED = Color.argb(255, 055, 0, 0);
    public static final int COLOR_CORE_YELLOW= Color.argb(255, 255, 255, 0);
    public static final int COLOR_EDGE_YELLOW = Color.argb(255, 050, 050, 0);
    public static final int COLOR_CORE_GREEN = Color.argb(255, 0, 255, 0);
    public static final int COLOR_EDGE_GREEN = Color.argb(255, 0, 050, 0);
    public static final int COLOR_CORE_BLUE = Color.argb(255, 102, 204, 255);
    public static final int COLOR_EDGE_BLUE = Color.argb(255, 0, 40, 90);
    public static final int COLOR_FIRST_LINE_CORE = Color.argb(100, 255, 255, 0);
    public static final int COLOR_FIRST_LINE_EDGE = Color.argb(100, 050, 050, 0);
    public static final int COLOR_ARROW = Color.argb(100, 0, 050, 0);
    public static final int[] COLOR_FIGURES_CORE = { COLOR_CORE_RED, COLOR_CORE_GREEN, COLOR_CORE_YELLOW, COLOR_CORE_BLUE};
    public static final int[] COLOR_FIGURES_EDGE = { COLOR_EDGE_RED, COLOR_EDGE_GREEN, COLOR_EDGE_YELLOW, COLOR_EDGE_BLUE};
    public final static int MAX_FIG=3;
    public final static int MAX_COLOR = 5;
    public final static int MAX_PACE = 4;
    public final static int COLOR_PRIMARY = Color.parseColor("#ff0060a0");
    public final static int COLOR_SECONDARY = Color.parseColor("#ff004090");

    public static final String LOG_TAG = "myLogs";

    public static final int[] PACE = {800, 700, 600, 500, 400, 300, 200};
    public static final int[] POINT_FOR_LINE ={1, 2, 4, 6, 10, 15, 20};
    public static final int[] LEAVE_LEVEL ={20, 20, 15, 15, 10, 10 };
    public static final float[] COMPLEX_PACE_COEFICIENT = {1, 1.33f ,1.67f, 2};
    public static final int SENSIBILITY =7; // for moving.


    //in CanMoveOrRotate
    public final static int MOVE_LAY=1;
    public final static int MOVE_BOTTOM = 1;
    public final static int MOVE_GAME_OVER = 2;
    public final static int MOVE_OTHER_PREVENT = 0;
    public final static int MOVE_WALL=0;
    public final static int MOVE_CANNOT=0;
    public final static int MOVE_CAN=-1;

    //in CanMoveOrRotateWithOther

    public final static int MOVE_WITH_CANNOT=1;
    public final static int MOVE_WITH_CAN=-1;
    public final static int MOVE_WITH_OTHER_LAY=2;


    //Data Base
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final String[] TABLE_NAME = {"standart", "awry", "hex"};
    public static final String NAME_COLUMN = "name";
    public static final String SCORE_COLUMN = "score";
    public static final int N_BEST = 10;


    //arrays of constants for standart, awry, hex
    //numbers of rows/columns in screens: standart, awry, hex
    public static final int[] NW={10, 8, 5};
    public static final int[] NH={20, 16, 10};
    public static final int[] MOVING_STEP = {1, 2, 1};
    public static final int[] MODES = {4, 4, 6};
    public static final int[] PRIMARY_Y = {-1, -3, -3};

    public static final String LAYOUT = "layout";
    public static final String SIGN_IN = "sign_in";
    //background & complexity
    public static final String PROPERTIES = "properties";
    public static final String BACK = "background";
    public static final String COMPLEX_NUMB = "complexity_numb";
    public static final String[] COMPLEX_COLORS = {"complexity_colors0", "complexity_colors1", "complexity_colors2"};
    public static final String COMPLEX_PACE = "complexity_pace";
    public static final String COMPLEXITY = "complexity";
    public static final int DEFAULT_COMPLEX_NUMB =0;
    public static final int DEFAULT_COMPLEX_COLOR =0;
    public static final int DEFAULT_COMPLEX_PACE =0;
    public static final int DEFAULT_BACK = 0;
    public static final String SETTINGS  = "settings";
    public static final String COMPLEX_PREVENT= "complexity_prevent";
    public static final int SWITCH_DISTRACT =1;
    public static final int SWITCH_PREVENT= 3;
    public static final int DEFAULT_COMPLEX_PREVENT =SWITCH_DISTRACT;

    //between 2 falling of one prevent figure
    public static final int PERIOD = 5;
    public static final int REAL_FIG = 1;
    public static final int REAL_FIG_TIME = 0;
    public static final int[] MISCH_FIG = {2, 3, 4};



    public static final String ABOUT  = "about";
    public static final int ABOUT_RULE  = 1;
    public static final int ABOUT_APP  = 2;

    public static String Next;
    public static String Level;
    public static String Score;
    public static boolean Connect=false;


 //   public static GoogleApiClient mGoogleApiClient;

}
