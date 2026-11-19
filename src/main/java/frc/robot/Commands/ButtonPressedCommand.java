package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class ButtonPressedCommand extends Command{

    private String character;

    public ButtonPressedCommand(String character) {
        this.character = character;
    }

    @Override
    public void initialize() {
        System.out.println(character + " pressed");
    }

    @Override
    public void execute() {
        System.out.println(character + " is currently pressed");
        
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println(character + " released");
    }
}