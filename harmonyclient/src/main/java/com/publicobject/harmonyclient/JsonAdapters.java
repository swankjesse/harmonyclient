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

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

final class JsonAdapters {
  final JsonAdapter<Command> command;
  final JsonAdapter<ClientMessage> clientMessage;
  final JsonAdapter<ServerMessage> serverMessage;

  JsonAdapters() {
    Moshi moshi = new Moshi.Builder().build();
    command = moshi.adapter(Command.class);
    clientMessage = moshi.adapter(ClientMessage.class);
    serverMessage = moshi.adapter(ServerMessage.class);
  }
}
