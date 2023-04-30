package com.lunarclient.apollo.option;

import io.leangen.geantyref.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents an option builder.
 *
 * @param <V> the value type
 * @param <M> the option builder type
 * @param <I> the option type
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class OptionBuilder<V, M extends OptionBuilder<V, M, I>, I extends Option<V, M, I>> {

    String[] node;
    TypeToken<V> typeToken;
    String comment = null;
    V defaultValue = null;
    boolean notify;

    /**
     * Sets the option node to the provided {@link String} array and returns
     * this builder.
     *
     * @param key the node string array
     * @return this builder
     * @since 1.0.0
     */
    public M node(@NonNull String... key) {
        this.node = key;
        return (M) this;
    }

    /**
     * Sets the option type to the provided {@link TypeToken} and returns
     * this builder.
     *
     * @param typeToken the type token
     * @return this builder
     * @since 1.0.0
     */
    public M type(@NonNull TypeToken<V> typeToken) {
        this.typeToken = typeToken;
        return (M) this;
    }

    /**
     * Sets the option comment to the provided {@link String} and returns
     * this builder.
     *
     * @param comment the comment
     * @return this builder
     * @since 1.0.0
     */
    public M comment(String comment) {
        this.comment = comment;
        return (M) this;
    }

    /**
     * Sets the option default value to the provided {@code T} value and
     * returns this builder.
     *
     * @param value the default value
     * @return this builder
     * @since 1.0.0
     */
    public M defaultValue(V value) {
        this.defaultValue = value;
        return (M) this;
    }

    /**
     * Sets whether the option should be aware to the client to {@code true}
     * and returns this builder.
     *
     * @return this builder
     * @since 1.0.0
     */
    public M notifyClient() {
        this.notify = true;
        return (M) this;
    }

    /**
     * Returns a new {@code I} option from this builder.
     *
     * @return a new option
     * @since 1.0.0
     */
    public abstract I build();

}
