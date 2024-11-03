package net.fabricmc.example;

import net.fabricmc.example.module.Mod;
import net.fabricmc.example.module.ModuleManager;
import net.fabricmc.example.ui.clickgui.ClickGUI;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.fabricmc.api.ModInitializer;
import org.lwjgl.glfw.GLFW;

public class JetmineAddon implements ModInitializer {

	public static final JetmineAddon INSTANCE = new JetmineAddon();
	public static final Logger logger = LogManager.getLogger(JetmineAddon.class);

private MinecraftClient mc = MinecraftClient.getInstance();

	@Override
	public void onInitialize() {
		logger.info("Hello, world!");
	}

	public void onKeyPress(int key, int action) {
		if (action == GLFW.GLFW_PRESS){
			for (Mod module : ModuleManager.INSTANCE.getModules()){
				if(key == module.getKey()) module.toggle();
			}

			if(key == GLFW.GLFW_KEY_RIGHT_SHIFT) mc.openScreen(ClickGUI.INSTANCE);

		}
	}

	public void onTick() {
		if(mc.player != null){
			for (Mod module : ModuleManager.INSTANCE.getEnableModules()){
				module.onTick();
			}
		}
	}



}
