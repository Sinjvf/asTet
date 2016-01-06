package com.example.tetris;

import android.graphics.Color;

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

    //colors of fields in game
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
    public static final int[] COLOR_FIGURES_CORE = { COLOR_CORE_RED, COLOR_CORE_GREEN, COLOR_CORE_YELLOW,COLOR_CORE_BLUE};
    public static final int[] COLOR_FIGURES_EDGE = { COLOR_EDGE_RED, COLOR_EDGE_GREEN, COLOR_EDGE_YELLOW,COLOR_EDGE_BLUE};
    public final static int MAX_FIG=3;
    public final static int MAX_COLOR = 5;
    public final static int MAX_PACE = 4;
    public final static int COLOR_PRIMARY = Color.parseColor("#ff0060a0");
    public final static int COLOR_SECONDARY = Color.parseColor("#ff004090");

    public static final String LOG_TAG = "myLogs";

    public static final int[] PACE = {800, 700, 450, 300, 200, 100, 80};
    public static final int[] POINT_FOR_LINE ={1, 2, 4, 6, 10, 15, 20};
    public static final int[] LEAVE_LEVEL ={10, 10, 8, 8, 6, 5 };
    public static final float[] COMPLEX_PACE_COEFICIENT = {1, 1.3f ,1.5f, 2};
    public static final int SENSIBILITY =7; // for moving.

    //Data Base
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final String[] TABLE_NAME = {"standart", "awry", "hex"};
    public static final String NAME_COLUMN = "name";
    public static final String SCORE_COLUMN = "score";
    public static final int N_BEST = 10;


    //arrays of constants for standart, awry, hex
    //numbers of rows/columns in screens: standart, awry, hex
    public static final int[] NW={10, 8, 6};
    public static final int[] NH={20, 16, 10};
    public static final int[] MOVING_STEP = {1, 2, 1};
    public static final int[] MODES = {4, 4, 6};
    public static final int[] PRIMARY_Y = {-1, -3, -3};


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



    public static final String ABOUT  = "about";
    public static final int ABOUT_RULE  = 1;
    public static final int ABOUT_APP  = 2;
}
