/*
 * Copyright (c) 2024 Lumina Games
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.vedantmulay.neptuneapi.bungee.config;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.*;

import java.util.Map;

public class NeptuneBungeeConfig {

    private final Plugin plugin;
    private Configuration config;
    private final String name;

    public NeptuneBungeeConfig(String name, Plugin plugin) {
        this.plugin = plugin;
        this.name = name;
        // Check if the config file exists, if not, create it
        File configFile = new File(plugin.getDataFolder(), name + ".yml");
        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();
            try (InputStream inputStream = plugin.getResourceAsStream("config.yml")) {
                Files.copy(inputStream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to create config file: " + e.getMessage());
            }
        }
        loadConfig();
        copyDefaults(configFile);
    }
    private void loadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), name + ".yml"));
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to load config file: " + e.getMessage());
        }
    }
    private void copyDefaults(File configFile) {
        // Load default config from resources
        InputStream defaultConfigStream = plugin.getResourceAsStream(name + ".yml");
        if (defaultConfigStream == null) {
            return;
        }
        // Copy default config if absent in the plugin folder
        try {
            Files.copy(defaultConfigStream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to copy default config file: " + e.getMessage());
        }
    }

    public Configuration getConfig() {
        return config;
    }
}
