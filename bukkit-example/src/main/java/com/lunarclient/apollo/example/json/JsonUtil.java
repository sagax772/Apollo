/*
 * This file is part of Apollo, licensed under the MIT License.
 *
 * Copyright (c) 2023 Moonsworth
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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.lunarclient.apollo.example.json;

import com.google.gson.JsonObject;
import java.awt.Color;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JsonUtil {

    public static String createDurationObject(@NotNull Duration duration) {
        long seconds = duration.getSeconds();
        int nanos = duration.getNano();

        // Is there a better way to do this?
        String durationString;
        if (nanos == 0) {
            durationString = seconds + "s";
        } else {
            durationString = String.format("%d.%09ds", seconds, nanos)
                .replaceAll("0+$", "")
                .replaceAll("\\.$", "");
        }

        return durationString;
    }

    public static JsonObject createColorObject(@NotNull Color color) {
        JsonObject colorObject = new JsonObject();
        colorObject.addProperty("color", color.getRGB());
        return colorObject;
    }

    public static JsonObject createUuidObject(@NotNull UUID uuid) {
        JsonObject uuidObject = new JsonObject();
        uuidObject.addProperty("high64", Long.toUnsignedString(uuid.getMostSignificantBits()));
        uuidObject.addProperty("low64", Long.toUnsignedString(uuid.getLeastSignificantBits()));
        return uuidObject;
    }

    public static JsonObject createEnableModuleObjectWithType(@NotNull String module, Map<String, Object> properties) {
        JsonObject enableModuleObject = JsonPacketUtil.createEnableModuleObject(module, properties);
        enableModuleObject.addProperty("@type", "type.googleapis.com/lunarclient.apollo.configurable.v1.ConfigurableSettings");
        return enableModuleObject;
    }

    public static JsonObject createLocationObject(@NotNull Location location) {
        JsonObject locationObject = new JsonObject();
        locationObject.addProperty("world", location.getWorld().getName());
        locationObject.addProperty("x", location.getX());
        locationObject.addProperty("y", location.getY());
        locationObject.addProperty("z", location.getZ());
        return locationObject;
    }

    public static JsonObject createBlockLocationObject(@NotNull Location location) {
        JsonObject locationObject = new JsonObject();
        locationObject.addProperty("world", location.getWorld().getName());
        locationObject.addProperty("x", location.getBlockX());
        locationObject.addProperty("y", location.getBlockY());
        locationObject.addProperty("z", location.getBlockZ());
        return locationObject;
    }

    public static JsonObject createEntityIdObject(@NotNull Entity entity) {
        JsonObject entityIdObject = new JsonObject();
        entityIdObject.addProperty("entity_id", entity.getEntityId());
        entityIdObject.add("entity_uuid", JsonUtil.createUuidObject(entity.getUniqueId()));
        return entityIdObject;
    }

    public static JsonObject createCuboid2DObject(double minX, double minZ, double maxX, double maxZ) {
        JsonObject cuboid2DObject = new JsonObject();
        cuboid2DObject.addProperty("min_x", minX);
        cuboid2DObject.addProperty("min_z", minZ);
        cuboid2DObject.addProperty("max_x", maxX);
        cuboid2DObject.addProperty("max_z", maxZ);
        return cuboid2DObject;
    }

    public static JsonObject createItemStackIconObject(@Nullable String itemName, int itemId, int customModelData) {
        JsonObject itemIconObject = new JsonObject();
        if (itemName != null) {
            itemIconObject.addProperty("item_name", itemName);
        } else {
            itemIconObject.addProperty("item_id", itemId);
        }

        itemIconObject.addProperty("custom_model_data", customModelData);

        JsonObject iconObject = new JsonObject();
        iconObject.add("item_stack", itemIconObject);
        return iconObject;
    }

    public static JsonObject createSimpleResourceLocationIconObject(String resourceLocation, int size) {
        JsonObject simpleIconObject = new JsonObject();
        simpleIconObject.addProperty("resource_location", resourceLocation);
        simpleIconObject.addProperty("size", size);

        JsonObject iconObject = new JsonObject();
        iconObject.add("simple_resource_location", simpleIconObject);

        return iconObject;
    }

    public static JsonObject createAdvancedResourceLocationIconObject(String resourceLocation, float width, float height,
                                                               float minU, float maxU, float minV, float maxV) {
        JsonObject advancedIcon = new JsonObject();
        advancedIcon.addProperty("resource_location", resourceLocation);
        advancedIcon.addProperty("width", width);
        advancedIcon.addProperty("height", height);
        advancedIcon.addProperty("min_u", minU);
        advancedIcon.addProperty("max_u", maxU);
        advancedIcon.addProperty("min_v", minV);
        advancedIcon.addProperty("max_v", maxV);

        JsonObject iconObject = new JsonObject();
        iconObject.add("advanced_resource_location", advancedIcon);

        return iconObject;
    }

    private JsonUtil() {
    }

}