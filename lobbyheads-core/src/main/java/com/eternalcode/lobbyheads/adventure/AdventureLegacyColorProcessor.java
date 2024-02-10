package com.eternalcode.lobbyheads.adventure;

import net.kyori.adventure.text.Component;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AdventureLegacyColorProcessor implements UnaryOperator<Component> {

    @Override
    public Component apply(Component component) {
        return component.replaceText(builder ->
            builder.match(Pattern.compile(".*"))
                .replacement((matchResult, input) -> AdventureLegacy.component(matchResult.group()))
        );
    }

}