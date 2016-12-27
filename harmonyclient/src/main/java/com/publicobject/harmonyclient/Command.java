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

import com.squareup.moshi.Json;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

final class Command {
  /** Like "9c926b8f02f6aeeb#cm_m8#m8-176-16". */
  @Json(name = "id") final String requestId;

  /** Like "harmony.automation?setState". */
  @Json(name = "cmd") final String command;

  final Params params;

  private Command(String requestId, String command, Params params) {
    this.requestId = requestId;
    this.command = command;
    this.params = params;
  }

  static Command getDeviceStates(String requestId) {
    Params params = new Params(emptyMap());
    return new Command(requestId, "harmony.automation?getState", params);
  }

  static Command getHubState(String requestId) {
    Params params = new Params(singletonMap("format", "json"));
    return new Command(requestId, "connect.statedigest?get", params);
  }

  static Command setDeviceState(String requestId, String device, boolean on) {
    Params params = new Params(singletonMap(device, new OnOffState(on)));
    return new Command(requestId, "harmony.automation?setState", params);
  }

  private static final class Params {
    /** A map whose keys are device names and whose values are the states to change to. */
    @Json(name = "state") final Map<String, Object> deviceToState;

    public Params(Map<String, Object> deviceToState) {
      this.deviceToState = Util.immutableMap(deviceToState); // Defensive copy.
    }
  }

  private static final class OnOffState {
    final boolean on;

    public OnOffState(boolean on) {
      this.on = on;
    }
  }
}
