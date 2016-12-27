/*
 * Copyright (C) 2016 Jesse Wilson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.publicobject.harmonyclient;

import okhttp3.HttpUrl;

/**
 * Sample app to muck with the Harmony device.
 */
public final class HarmonyClientCommand {
  public static void main(String[] args) {
    boolean flip = false;

    HarmonyClient client = new HarmonyClient.Builder()
        .url(HttpUrl.parse("http://192.168.2.208:8088/"))
        .hubId("7908680")
        .build();

    client.connect();
    System.out.println();
    client.getHubState();
    client.getDeviceStates();
    client.setDeviceState("zwave5", flip);
    client.setDeviceState("zwave3", !flip);
  }
}
