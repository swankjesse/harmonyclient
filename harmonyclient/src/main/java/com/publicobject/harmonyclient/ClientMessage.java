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

/**
 * A JSON-encoded websocket message from client to server.
 *
 * <h3>harmony.automation?getState</h3>
 *
 * <pre>{@code
 *
 *   {
 *     "hbus":{
 *       "id":"9c926b8f02f6aeeb#cm_m8#m8-176-13",
 *       "cmd":"harmony.automation?getState",
 *       "params":{
 *       }
 *     }
 *   }
 * }</pre>
 *
 * <h3>connect.statedigest?get</h3>
 *
 * <pre>{@code
 *
 *   {
 *     "hbus":{
 *       "id":"9c926b8f02f6aeeb#cm_m8#m8-176-14",
 *       "cmd":"connect.statedigest?get",
 *       "params":{
 *         "format":"json"
 *       }
 *     }
 *   }
 * }</pre>
 *
 * <h3>harmony.automation?setState</h3>
 *
 * <pre>{@code
 *
 *   {
 *     "hbus":{
 *       "id":"9c926b8f02f6aeeb#cm_m8#m8-176-16",
 *       "cmd":"harmony.automation?setState",
 *       "params":{
 *         "state":{
 *           "zwave5":{
 *             "on":true
 *           }
 *         }
 *       }
 *     }
 *   }
 * }</pre>
 */
final class ClientMessage {
  @Json(name = "hbus") final Command command;

  public ClientMessage(Command command) {
    this.command = command;
  }
}
