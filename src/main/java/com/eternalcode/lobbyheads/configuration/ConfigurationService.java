package com.eternalcode.lobbyheads.configuration;

import com.eternalcode.lobbyheads.configuration.serializer.HeadSerializer;
import com.eternalcode.lobbyheads.configuration.serializer.PositionSerializer;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ConfigurationService {

    private final Set<OkaeriConfig> configs = new HashSet<>();

    public <T extends OkaeriConfig> T create(Class<T> config, File file) {
        T configFile = ConfigManager.create(config);

        YamlSnakeYamlConfigurer yamlConfigurer = new YamlSnakeYamlConfigurer(this.createYaml());

        configFile
            .withConfigurer(yamlConfigurer, new SerdesCommons())
            .withSerdesPack(registry -> registry.register(new HeadSerializer()))
            .withSerdesPack(registry -> registry.register(new PositionSerializer()))
            .withBindFile(file)
            .withRemoveOrphans(true)
            .saveDefaults()
            .load(true);

        this.configs.add(configFile);

        return configFile;
    }

    private Yaml createYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        Constructor constructor = new Constructor(loaderOptions);

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.AUTO);
        dumperOptions.setIndent(2);
        dumperOptions.setSplitLines(false);

        Representer representer = new CustomRepresenter(dumperOptions);
        Resolver resolver = new Resolver();

        return new Yaml(constructor, representer, dumperOptions, loaderOptions, resolver);
    }

    public void reload() {
        for (OkaeriConfig config : this.configs) {
            config.load();
        }
    }

    public void save(OkaeriConfig config) {
        config.save();
    }

}
