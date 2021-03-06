package FinalGame;

import java.util.Random;

public class Constants {
static double screen_width = 1280;
static double screen_height = 850;
static double epsilon = 0.1;
static Random rand = new Random();
static int health = 100;
static int enemyTankHealth = health*2;
static int health_boost = 50;
static double shellDamage = 5.5;
static double enemyShellDamage = shellDamage*2;
static double tankSpeed = 1.75d;
static final double  tankWidth = 20.0d;
static final double tankLength = 40.0d;
static final double  dTheta = 2.0f;
static double barLength  = 200.0d;
static double barWidth = 40.0d;
static double tankDiagnol = Math.hypot(tankWidth, tankLength);
public static int FPS = 60;


}
