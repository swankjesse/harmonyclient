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

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public final class JsonAdaptersTest {
  private final JsonAdapters jsonAdapters = new JsonAdapters();

  @Test public void clientMessageToJson() throws Exception {
    Command command = Command.setDeviceState("9c926b8f02f6aeeb#cm_m8#m8-176-16", "zwave5", true);
    String json = jsonAdapters.clientMessage.toJson(new ClientMessage(command));
    assertThat(json).isEqualTo(""
        + "{"
        + "" + "\"hbus\":{"
        + "" + "" + "\"cmd\":\"harmony.automation?setState\","
        + "" + "" + "\"id\":\"9c926b8f02f6aeeb#cm_m8#m8-176-16\","
        + "" + "" + "\"params\":{"
        + "" + "" + "" + "\"state\":{"
        + "" + "" + "" + "" + "\"zwave5\":{"
        + "" + "" + "" + "" + "" + "\"on\":true"
        + "" + "" + "" + "" + "}"
        + "" + "" + "" + "}"
        + "" + "" + "}"
        + "" + "}"
        + "}");
  }

  @Test public void serverMessageFromJson() throws Exception {
    String json = ""
        + "{"
        + "" + "\"type\":\"automation.state?notify\","
        + "" + "\"data\":{"
        + "" + "" + "\"zwave5\":{"
        + "" + "" + "" + "\"status\":0,"
        + "" + "" + "" + "\"on\":true"
        + "" + "" + "}"
        + "" + "}"
        + "}";

    ServerMessage serverMessage = jsonAdapters.serverMessage.fromJson(json);
    assertThat(serverMessage.type).isEqualTo("automation.state?notify");

    Map<String, Object> state = new LinkedHashMap<>();
    state.put("status", 0.0d);
    state.put("on", true);
    assertThat(serverMessage.data).containsExactly(entry("zwave5", state));
  }
}
