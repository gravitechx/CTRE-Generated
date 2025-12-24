// package frc.robot;

// import dev.doglog.DogLog;
// import frc.robot.subsystems.ClawSubsystem;
// import frc.robot.subsystems.ElevatorSubsystem;
// import frc.robot.subsystems.PivotSubsystem;
// import frc.robot.subsystems.WristSubsystem;

// public class RobotManager {
//     RobotState rState = RobotState.STARTING;
//     ClawSubsystem claw = new ClawSubsystem();
//     ElevatorSubsystem elev = new ElevatorSubsystem();
//     PivotSubsystem pivot = new PivotSubsystem();
//     WristSubsystem wrist = new WristSubsystem();

//     public void setState(String state){
//         if(state.equals("STARTING")){
//             rState = RobotState.STARTING;
//         }
//         else if(state.equals("IDLE")){
//             rState = RobotState.IDLE;
//         }
//         else if(state.equals("CORAL_INTAKE")){
//             rState = RobotState.CORAL_INTAKE;
//         }
//         else if(state.equals("INTAKE_DOWN")){
//             rState = RobotState.INTAKE_DOWN;
//         }
//         else if(state.equals("ALGAE_INTAKE")){
//             rState = RobotState.ALGAE_INTAKE;
//         }
//         else if(state.equals("PROCESSOR")){
//             rState = RobotState.PROCESSOR;
//         }
//         else if(state.equals("L1")){
//             rState = RobotState.L1;
//         }
//         else if(state.equals("L2")){
//             rState = RobotState.L2;
//         }
//         else if(state.equals("L3")){
//             rState = RobotState.L3;
//         }
//         else if(state.equals("L4")){
//             rState = RobotState.L4;
//         }
//         else if(state.equals("ALGAE_L2")){
//             rState = RobotState.ALGAE_L2;
//         }
//         else if(state.equals("ALGAE_L3")){
//             rState = RobotState.ALGAE_L3;
//         }
//         else if(state.equals("ALGAE_HOLDING")){
//             rState = RobotState.ALGAE_HOLDING;
//         }
//         else if(state.equals("CORAL_OUTTAKE")){
//             rState = RobotState.CORAL_OUTTAKE;
//         }
//         else if(state.equals("ALGAE_OUTTAKE")){
//             rState = RobotState.ALGAE_OUTTAKE;
//         }
//         else if(state.equals("WRIST_FLIPPED")){
//             rState = RobotState.WRIST_FLIPPED;
//         }
//         else if(state.equals("L2L3_SCORING")){
//             rState = RobotState.L2L3_SCORING;
//         }
//         else if(state.equals("L4_SCORING")){
//             rState = RobotState.L4_SCORING;
//         }
//         else if(state.equals("CLAW_NUETRAL")){
//             rState = RobotState.CLAW_NUETRAL;
//         }
//     }

//     public void periodic(){

//         switch(rState){
//             case STARTING:
//                 claw.setState("NUETRAL");
//                 elev.setState("BOTTOM");
//                 pivot.setState("BACK");
//                 wrist.setState("HORIZANTAL");
//                 break;
//             case IDLE:
//                 claw.setState("NUETRAL");
//                 elev.setState("BOTTOM");
//                 pivot.setState("IDLE");
//                 wrist.setState("HORIZANTAL");
//                 break;
//             case CORAL_INTAKE:
//                 claw.setState("CORAL_INTAKE");
//                 elev.setState("INTAKE");
//                 pivot.setState("CORAL_INTAKE");
//                 wrist.setState("VERTICAL");
//                 break;
//             case INTAKE_DOWN:
//                 claw.setState("CORAL_INTAKE");
//                 elev.setState("INTAKE");
//                 pivot.setState("DOWN");
//                 wrist.setState("VERTICAL");
//                 break;
//             case ALGAE_INTAKE:
//                 claw.setState("ALGAE_INTAKE");
//                 elev.setState("BOTTOM");
//                 pivot.setState("ALGAE_INTAKE");
//                 wrist.setState("VERTICAL");
//                 break;
//             case PROCESSOR:
//                 claw.setState("ALGAE_HOLDING");
//                 elev.setState("BOTTOM");
//                 pivot.setState("PROCESSOR");
//                 wrist.setState("VERTICAL");
//                 break;
//             case L1:
//                 claw.setState("NUETRAL");
//                 elev.setState("L1");
//                 pivot.setState("L1");
//                 wrist.setState("VERTICAL");
//                 break;
//             case L2:
//                 claw.setState("NUETRAL");
//                 elev.setState("L2");
//                 pivot.setState("L2L3");
//                 wrist.setState("HORIZANTAL");
//                 break;
//             case L3:
//                 claw.setState("NUETRAL");
//                 elev.setState("L3");
//                 pivot.setState("L2L3");
//                 wrist.setState("HORIZANTAL");
//                 break;
//             case L4:
//                 claw.setState("NUETRAL");
//                 elev.setState("L4");
//                 pivot.setState("L4");
//                 wrist.setState("HORIZANTAL");
//                 break;
//             case ALGAE_L2:
//                 claw.setState("ALGAE_INTAKE");
//                 elev.setState("L2ALGAE");
//                 pivot.setState("L2L3ALGAE");
//                 wrist.setState("HORIZANTAL");
//                 break;
//             case ALGAE_L3:
//                 claw.setState("ALGAE_INTAKE");
//                 elev.setState("L3ALGAE");
//                 pivot.setState("L2L3ALGAE");
//                 wrist.setState("HORIZANTAL");
//                 break;
//             case ALGAE_HOLDING:
//                 claw.setState("ALGAE_HOLDING");
//                 elev.setState("BOTTOM");
//                 pivot.setState("IDLE");
//                 wrist.setState("HORIZANTAL");
//                 break;
//             case CORAL_OUTTAKE:
//                 claw.setState("CORAL_OUTTAKE");
//                 break;
//             case ALGAE_OUTTAKE:
//                 claw.setState("ALGAE_OUTTAKE");
//                 break;
//             case WRIST_FLIPPED:
//                 wrist.setState("FLIPPED");
//                 break;
//             case L2L3_SCORING:
//                 pivot.setState("SCORING");
//             case L4_SCORING:
//                 elev.setState("BOTTOM");
//                 break;
//             case CLAW_NUETRAL:
//                 claw.setState("NUETRAL");
//         }
        
//         DogLog.log("bot state", rState);
//     }
// }
