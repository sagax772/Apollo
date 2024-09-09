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
package com.lunarclient.apollo.example.modules.impl.json;

import com.google.gson.JsonObject;
import com.lunarclient.apollo.example.modules.impl.CooldownExample;
import com.lunarclient.apollo.example.utilities.JsonPacketUtil;
import com.lunarclient.apollo.example.utilities.JsonUtil;
import com.lunarclient.apollo.example.utilities.objects.icon.ItemStackIcon;
import com.lunarclient.apollo.example.utilities.objects.icon.SimpleResourceLocationIcon;
import java.time.Duration;
import org.bukkit.entity.Player;

public class CooldownJsonExample extends CooldownExample {

    @Override
    public void displayCooldownItemExample(Player viewer) {
        JsonObject message = new JsonObject();
        message.addProperty("@type", "type.googleapis.com/lunarclient.apollo.cooldown.v1.DisplayCooldownMessage");
        message.addProperty("name", "enderpearl-cooldown");
        message.add("duration", JsonUtil.createDurationObject(Duration.ofSeconds(15)));
        message.add("icon", JsonUtil.createIconObject(
            ItemStackIcon.builder()
                .itemName("ENDER_PEARL")
                .build())
        );

        JsonPacketUtil.sendPacket(viewer, message);
    }

    @Override
    public void displayCooldownResourceExample(Player viewer) {
        JsonObject message = new JsonObject();
        message.addProperty("@type", "type.googleapis.com/lunarclient.apollo.cooldown.v1.DisplayCooldownMessage");
        message.addProperty("name", "lunar-cooldown");
        message.add("duration", JsonUtil.createDurationObject(Duration.ofSeconds(15)));
        message.add("icon", JsonUtil.createIconObject(
            SimpleResourceLocationIcon.builder()
                .resourceLocation("lunar:logo/logo-200x182.svg")
                .size(12)
                .build())
        );

        JsonPacketUtil.sendPacket(viewer, message);
    }

    @Override
    public void removeCooldownExample(Player viewer) {
        JsonObject message = new JsonObject();
        message.addProperty("@type", "type.googleapis.com/lunarclient.apollo.cooldown.v1.RemoveCooldownMessage");
        message.addProperty("name", "lunar-cooldown");

        JsonPacketUtil.sendPacket(viewer, message);
    }

    @Override
    public void resetCooldownsExample(Player viewer) {
        JsonObject message = new JsonObject();
        message.addProperty("@type", "type.googleapis.com/lunarclient.apollo.cooldown.v1.ResetCooldownsMessage");

        JsonPacketUtil.sendPacket(viewer, message);
    }

}