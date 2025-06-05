package com.rinko1231.alienday.mixin;


import com.rinko1231.alienday.config.AlienDayConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.violetmoon.quark.base.client.config.QButton;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Mixin(value = QButton.class, remap = false)
public class QButtonMixin {

    // 确保配置在类初始化前加载
    static {
        if (FMLEnvironment.production) {
            AlienDayConfig.load();
        }
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void onStaticInit(CallbackInfo ci) {
        try {
            Field listField = QButton.class.getDeclaredField("CELEBRATIONS");
            listField.setAccessible(true);

            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) listField.get(null);

            if (!list.isEmpty()) {
                Object sample = list.get(0);
                Method getName = sample.getClass().getMethod("name");

                list.removeIf(celebration -> {
                    try {
                        String name = (String) getName.invoke(celebration);
                        return AlienDayConfig.shouldSkip(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}