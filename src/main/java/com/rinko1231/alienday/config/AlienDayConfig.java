package com.rinko1231.alienday.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AlienDayConfig {

        private static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("alienday/skip_celebrations.json");
        private static final Gson GSON = new Gson();
        private static Set<String> skipSet = Collections.emptySet();

        public static void load() {
            if (!Files.exists(CONFIG_PATH)) {
                createDefaultConfig();
                return;
            }

            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                skipSet = new HashSet<>(GSON.fromJson(reader, new TypeToken<Set<String>>(){}.getType()));
            } catch (IOException e) {
                e.printStackTrace();
                skipSet = Collections.emptySet();
            }
        }

        private static void createDefaultConfig() {
            try {
                Files.createDirectories(CONFIG_PATH.getParent());
                Files.writeString(CONFIG_PATH, GSON.toJson(Collections.singleton("example1")));
                skipSet = Set.of("example1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static boolean shouldSkip(String name) {
            return skipSet.contains(name);
        }
    }