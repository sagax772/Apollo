package com.lunarclient.apollo.option;

import com.google.protobuf.Value;
import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.ApolloModule;
import com.lunarclient.apollo.network.NetworkOptions;
import com.lunarclient.apollo.player.ApolloPlayer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a container of {@link Option}s.
 *
 * @since 1.0.0
 */
public final class OptionsContainer extends AbstractOptions implements Options.Container {

    private final Map<ApolloPlayer, Single> views = new HashMap<>();
    private final Map<String, Option<?, ?, ?>> options = new HashMap<>();
    private final Map<String, Object> values = new HashMap<>();

    final ApolloModule module;

    /**
     * Creates a new container.
     *
     * @param module the module
     * @param options the options
     * @since 1.0.0
     */
    public OptionsContainer(ApolloModule module, Collection<Option<?, ?, ?>> options) {
        this.module = module;

        for(Option<?, ?, ?> option : options) {
            this.options.put(option.getKey(), option);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, C extends Option<T, ?, ?>> @Nullable T get(@NonNull C option) {
        Object value;
        return (value = this.values.get(option.getKey())) == null
                ? option.getDefaultValue()
                : (T) value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, C extends Option<T, ?, ?>> Optional<T> getDirect(C option) {
        return Optional.ofNullable((T) this.values.get(option.getKey()));
    }

    @Override
    public <T> void set(@NonNull Option<?, ?, ?> option, @Nullable T value) {
        if(!this.postUpdate(option, value)) return;
        boolean send;
        if(value == null || Objects.equals(value, option.getDefaultValue())) {
            send = this.values.remove(option.getKey()) != null;
        } else {
            this.values.put(option.getKey(), value);
            send = true;
        }

        if(send) {
            NetworkOptions.sendOption(
                    this.module,
                    option,
                    this.wrapValue(Value.newBuilder(), value),
                    Apollo.getPlayerManager().getPlayers()
            );
        }
    }

    @Override
    public <T> void add(@NonNull Option<?, ?, ?> option, @NonNull T value) {
        if(!this.postUpdate(option, value)) return;
        if(this.values.putIfAbsent(option.getKey(), value) == null) {
            NetworkOptions.sendOption(
                    this.module,
                    option,
                    this.wrapValue(Value.newBuilder(), value),
                    Apollo.getPlayerManager().getPlayers()
            );
        }
    }

    @Override
    public <T> void remove(@NonNull Option<?, ?, ?> option, @NonNull T compare) {
        if(!this.postUpdate(option, null)) return;
        if(this.values.remove(option.getKey(), compare)) {
            NetworkOptions.sendOption(
                    this.module,
                    option,
                    this.wrapValue(Value.newBuilder(), null),
                    Apollo.getPlayerManager().getPlayers()
            );
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void replace(@NonNull Option<?, ?, ?> option, @NonNull BiFunction<Option<?, ?, ?>, T, T> remappingFunction) {
        Object value = this.values.compute(option.getKey(), (key, current) -> remappingFunction.apply(option, (T) current));
        NetworkOptions.sendOption(
                this.module,
                option,
                this.wrapValue(Value.newBuilder(), value),
                Apollo.getPlayerManager().getPlayers()
        );
    }

    @Override
    public Single get(@NonNull ApolloPlayer player) {
        return this.views.computeIfAbsent(player, key -> new OptionsView(this, key));
    }

    @Override
    public Iterator<Option<?, ?, ?>> iterator() {
        return this.options.values().iterator();
    }

}
