package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Accesories.WobbleGoal;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Components.BasicChassis;

/**
 * Teleop w/ drivetrain, shooter(still in testing), and wobblegoal
 *
 * Current button mappings for gamepad 1 = right joystick for movement, left for turning,
 * x for slowmode on, a for slowmode off
 *
 * Current button mappings for gamepad 2 = y for shoot high goal, b for mid goal, a for low,
 * x for moving servo back and forth, right trigger for turning on shooter motor, dpad up and down
 * for wobblegoal
 *
 * @author  Nathan
 * @version 1.0
 * @since   2021-Jan-13
 *
 */


@TeleOp(name = "TwoGPTeleop ")
//@Disabled
public class TwoGPTeleop extends LinearOpMode {
        public void runOpMode() {
            telemetry.addData("Status", "Before new Robot");
            telemetry.update();
            Robot robot = new Robot(this, BasicChassis.ChassisType.ODOMETRY, false ,true);
            telemetry.addData("Status", "Done with new Robot");
            telemetry.update();
            //robot.navigateTeleOp();
            double magnitude;
            double angleInRadian;
            double angleInDegree;
            boolean slowMode = false;
            boolean wobble_goal_servo_is_up = true;
            boolean move_wobble_goal_servo = true;
            robot.openWobbleGoalClaw();
            WobbleGoal.Position currentWobbleGoalPosition = WobbleGoal.Position.REST;
            robot.moveLeftStick(0);
            robot.moveRightStick(1);

            telemetry.addData("Status", "Ready to go");
            telemetry.update();

            //Aiden - during competition day robot disconnected so we are trying this code
            while (!opModeIsActive() && !isStopRequested()) {
                telemetry.addData("status", "waiting for start command...");
                telemetry.update();
            }


            while (!isStopRequested()) {

                float left_stick_y = -gamepad1.left_stick_y;
                float left_stick_x = -gamepad1.left_stick_x;
                float right_stick_x = -gamepad1.right_stick_x;
                boolean move_wobble_goal_arm = gamepad2.b;
                boolean start_transfer_sys = gamepad1.right_bumper;
                float shooter = gamepad2.right_trigger;
                boolean odo_powershots = gamepad1.b;
                boolean shooter_servo = gamepad1.x;
                boolean wobble_goal_servo = gamepad2.y;
                boolean quick_reverse = gamepad1.a;
                boolean move_sticks_down = gamepad2.dpad_up;
                boolean move_sticks_up = gamepad2.dpad_down;



                angleInRadian = Math.atan2(left_stick_y, left_stick_x);
                angleInDegree = Math.toDegrees(angleInRadian);

                /**Powershots**/
                if(odo_powershots){
                    robot.setPosition(0,0,0);
                    robot.goToPosition(40,5 ,0,0.8);
                    robot.goToPosition(40,-40,-88,0.7);
                    robot.shootThreePowerShot();
                }

                /**Sticks**/

                if (move_sticks_down) {
                    robot.moveLeftStick(1);
                    robot.moveRightStick(0);
                }

                if (move_sticks_up){
                    robot.moveLeftStick(0.3);
                    robot.moveRightStick(0.8);
                }

                /**Shooter**/
                if (shooter_servo){
                    telemetry.addData("Servo", " SERVO Forward and Backward");
                    telemetry.update();
                    robot.moveServo(false);
                    robot.moveServo(true);
                }

                if (shooter != 0) {
                    robot.shootGoalTeleop(1000);
                } else {
                    robot.stopShooter();
                }

                magnitude = Math.sqrt(Math.pow(left_stick_x, 2) + Math.sqrt(Math.pow(left_stick_y, 2)));

                robot.moveMultidirectional(magnitude, angleInDegree, right_stick_x, slowMode); // It is 0.95, because the robot DCs at full power.

                // wobble goal movements
                telemetry.addData("Wobble Goal Toggle", move_wobble_goal_arm + ", " + currentWobbleGoalPosition);
                telemetry.update();
                if (move_wobble_goal_arm){
                    WobbleGoal.Position nextWobbleGoalPosition = WobbleGoal.Position.REST;
                    if (currentWobbleGoalPosition == WobbleGoal.Position.REST){
                        nextWobbleGoalPosition = robot.moveWobbleGoalToPosition(WobbleGoal.Position.GRAB);
                    } else if (currentWobbleGoalPosition == WobbleGoal.Position.GRAB) {
                        nextWobbleGoalPosition = robot.moveWobbleGoalToPosition(WobbleGoal.Position.DriveToWall);
                    } else if (currentWobbleGoalPosition == WobbleGoal.Position.DriveToWall) {
                        nextWobbleGoalPosition = robot.moveWobbleGoalToPosition(WobbleGoal.Position.DropOverWall);
                        this.sleep(600);
                        robot.openWobbleGoalClaw();
                    } else if(currentWobbleGoalPosition == WobbleGoal.Position.DropOverWall){
                        nextWobbleGoalPosition = robot.moveWobbleGoalToPosition(WobbleGoal.Position.GRAB);
                    }
                    else {
                        telemetry.addData("Wobble Goal", "u have made a STUPID MISTAKE");
                        telemetry.update();
                        sleep(500);
                    }
                    // added by Aiden; must have this otherwise if you hold onto the button multiple
                    // actions/movements will be executed by mistake
                    sleep(500);
                    currentWobbleGoalPosition = nextWobbleGoalPosition;
                }

                if (wobble_goal_servo) {
                    move_wobble_goal_servo = true;

                    if (wobble_goal_servo_is_up) {
                        wobble_goal_servo_is_up = false;
                    } else if (!wobble_goal_servo_is_up) {
                        wobble_goal_servo_is_up = true;
                    }
                } else {
                    move_wobble_goal_servo = false;
                }

                if (move_wobble_goal_servo) {
                    if (wobble_goal_servo_is_up) {
                        telemetry.addData("Wobble Goal Servo", " Wobble Goal UP y_button");
                        telemetry.update();
                        robot.closeWobbleGoalClaw();
                    } else if (!wobble_goal_servo_is_up) {
                        telemetry.addData("Wobble Goal Servo", " Wobble Goal DOWN y_button");
                        telemetry.update();
                        robot.openWobbleGoalClaw();
                    }
                }

                // transfer system
                if(start_transfer_sys){
                    robot.startIntake();
                    robot.startTransfer();
                } else if (!start_transfer_sys){
                    robot.stopIntake();
                    robot.stopTransfer();
                }

                // quick reverse
                if (quick_reverse){
                    robot.reverseIntake();
                    robot.reverseTransfer();
                    sleep(250);
                    robot.startIntake();
                    robot.startTransfer();
                }
            }
            idle();
        }
    }