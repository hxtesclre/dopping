package net.fabricmc.example.module;

import net.fabricmc.example.module.Movement.AutoWalk;
import net.fabricmc.example.module.Movement.NoFall;
import net.fabricmc.example.module.Render.ParticleTracker;
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

    public List<Mod> getModulesInCategory(Mod.Category category){
        List<Mod> categoryModules = new ArrayList<>();

        for (Mod mod : modules){
            if(mod.getCategory() == category){
                categoryModules.add(mod);
            }
        }
        return categoryModules;
    }

    private void addModules(){
        modules.add(new Flight());
        modules.add(new Sprint());
        modules.add(new PlayerRadar()); // Добавление модуля PlayerRadar
        modules.add(new NoFall());
        modules.add(new AutoWalk());
        modules.add(new ParticleTracker());
    }
}
