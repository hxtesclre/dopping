package net.fabricmc.example.module;

import net.fabricmc.example.module.Render.PlayerRadar;
import net.fabricmc.example.module.Movement.Flight;
import net.fabricmc.example.module.Movement.Sprint;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public static final ModuleManager INSTANCE = new ModuleManager();
    public List<Mod> modules = new ArrayList<>();

    public ModuleManager(){
        addModules();
    }

    public List<Mod> getModules(){
        return modules;
    }

    public List<Mod> getEnableModules(){
        List<Mod> enabled = new ArrayList<>();
        for (Mod module : modules){
            if (module.isEnabled()) enabled.add(module);
        }
        return enabled;
    }

    private void addModules(){
        modules.add(new Flight());
        modules.add(new Sprint());
        modules.add(new PlayerRadar()); // Добавление модуля PlayerRadar
    }
}
