package com.lunarclient.apollo.module.type;

import com.lunarclient.apollo.module.ApolloModule;
import com.lunarclient.apollo.option.NumberOption;
import com.lunarclient.apollo.player.ApolloPlayer;
import io.leangen.geantyref.TypeToken;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public abstract class TntCountdown extends ApolloModule {

    /**
     * Set the amount of ticks before the TNT explodes.
     *
     * @since 1.0.0
     */
    public static final NumberOption<Integer> TNT_TICKS = NumberOption.<Integer>number()
            .comment("Set the amount of ticks before the TNT explodes.")
            .node("tnt-ticks").type(TypeToken.get(Integer.class))
            .defaultValue(80).min(1).max(Integer.MAX_VALUE).build();

    TntCountdown() {
        super("TntCountdown");

        this.registerOptions(
            TntCountdown.TNT_TICKS
        );
    }

    @Override
    public boolean isClientNotify() {
        return true;
    }

    /**
     * Set the amount of ticks before the specified TNT explodes.
     *
     * @param player the player
     * @param entityId the entity id
     * @param ticks the ticks
     * @since 1.0.0
     */
    public abstract void setTntTicks(ApolloPlayer player, int entityId, int ticks);

}
