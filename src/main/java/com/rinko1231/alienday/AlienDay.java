package com.rinko1231.alienday;

import com.rinko1231.alienday.config.AlienDayConfig;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(AlienDay.MODID)
public class AlienDay
{
    public static final String MODID = "alienday";


    public AlienDay()
    {
        MinecraftForge.EVENT_BUS.register(this);
        if (!FMLEnvironment.production) {
            AlienDayConfig.load();
        }
    }

}
