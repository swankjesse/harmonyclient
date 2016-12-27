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

/**
 * A JSON-encoded websocket message from server to client.
 *
 * <h2>Response Messages</h2>
 *
 * Response messages start with a "cmd" key and always contain an "id" of the request being
 * responded to. They also contain a code (like 200), a message (like "OK"), and request-specific
 * data.
 *
 * <h3>harmony.automation?getState</h3>
 *
 * <pre>{@code
 *
 *   {
 *     "cmd":"harmony.automation?getState",
 *     "code":200,
 *     "id":"9c926b8f02f6aeeb#cm_m8#m8-176-13",
 *     "msg":"OK",
 *     "data":{
 *       "zwave3":{
 *         "status":0,
 *         "on":false
 *       },
 *       "zwave5":{
 *         "status":0,
 *         "on":false
 *       },
 *       "zwave4":{
 *         "status":0,
 *         "on":true
 *       },
 *       "zwave2":{
 *         "status":0,
 *         "on":true
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
 *     "cmd":"connect.statedigest?get",
 *     "code":200,
 *     "id":"9c926b8f02f6aeeb#cm_m8#m8-176-12",
 *     "msg":"OK",
 *     "data":{
 *       "sleepTimerId":-1,
 *       "runningZoneList":[],
 *       "contentVersion":50,
 *       "activityId":"17767040",
 *       "syncStatus":0,
 *       "time":1482771361,
 *       "stateVersion":280,
 *       "tzOffset":"-18000",
 *       "mode":3,
 *       "hubSwVersion":"4.12.36",
 *       "deviceSetupState":[],
 *       "tzoffset":"-18000",
 *       "isSetupComplete":true,
 *       "discoveryServer":"http:\/\/svcs.myharmony.com\/Discovery\/Discovery.svc",
 *       "sequence":false,
 *       "runningActivityList":"17767040",
 *       "activityStatus":2,
 *       "wifiStatus":1,
 *       "tz":"EST5EDT,M3.2.0,M11.1.0",
 *       "updates":{
 *         "106":"4.12.36"
 *       },
 *       "activitySetupState":false,
 *       "hubUpdate":false,
 *       "configVersion":101,
 *       "accountId":"7543287"
 *     }
 *   }
 * }</pre>
 *
 * <h2>Notifications</h2>
 *
 * Notifications start with a "type" key and contain data on the state of a particular device.
 *
 * <h3>automation.state?notify</h3>
 *
 * <pre>{@code
 *
 *  {
 *    "type":"automation.state?notify",
 *    "data":{
 *      "zwave5":{
 *        "status":0,
 *        "on":true
 *      }
 *    }
 *  }
 * }</pre>
 */
final class ServerMessage {
  /** The command being responded to, like "harmony.automation?setState". */
  @Json(name = "cmd") String command;

  /** A message type, like "automation.state?notify". */
  @Json(name = "type") String type;

  /** Payload data, like {"zwave5":{"status":0,"on":false}. */
  @Json(name = "data") Map<String, Object> data;

  /** The unique ID of the command being responded to, like "9c926b8f02f6aeeb#cm_m8#m8-176-12". */
  @Json(name = "id") String id;

  /** The status code of the response, like 200. */
  @Json(name = "code") int code;

  /** The status message of the response, like "OK". */
  @Json(name = "msg") String message;
}
