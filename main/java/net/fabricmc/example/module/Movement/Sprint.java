package net.fabricmc.example.module.Movement;

import net.fabricmc.example.module.Mod;
import org.lwjgl.glfw.GLFW;

public class Sprint extends Mod {
    public Sprint(){
        super("Sprint","Keeps your sprint", Category.MOVEMENT);
    }


    @Override
    public void onTick() {
        mc.player.setSprinting(true);
        this.setKey(GLFW.GLFW_KEY_V);
        super.onTick();
    }
}
