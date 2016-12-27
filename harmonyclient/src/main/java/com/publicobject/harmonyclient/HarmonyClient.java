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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public final class HarmonyClient {
  private final JsonAdapters jsonAdapters = new JsonAdapters();
  private final IdGenerator idGenerator;
  private final HttpUrl url;
  private final String hubId;
  private final OkHttpClient httpClient;

  private WebSocket webSocket;

  private HarmonyClient(Builder builder) {
    if (builder.idGenerator == null) throw new IllegalArgumentException("idGenerator == null");
    if (builder.url == null) throw new IllegalArgumentException("url == null");
    if (builder.hubId == null) throw new IllegalArgumentException("hubId == null");

    this.idGenerator = builder.idGenerator;
    this.url = builder.url;
    this.hubId = builder.hubId;
    this.httpClient = builder.httpClient != null ? builder.httpClient : new OkHttpClient();
  }

  public synchronized void disconnect() {
    if (webSocket != null) {
      webSocket.close(1000, null);
      webSocket = null;
    }
  }

  public synchronized void connect() {
    HttpUrl webSocketUrl = url.newBuilder()
        .addQueryParameter("domain", "svcs.myharmony.com")
        .addQueryParameter("hubId", hubId)
        .build();
    Request request = new Request.Builder()
        .url(webSocketUrl)
        .build();
    webSocket = httpClient.newWebSocket(request, new Listener());
  }

  /** Returns true if the action was attempted. */
  public boolean getDeviceStates() {
    return send(Command.getDeviceStates(idGenerator.nextId()));
  }

  /** Returns true if the action was attempted. */
  public boolean getHubState() {
    return send(Command.getHubState(idGenerator.nextId()));
  }

  /** Returns true if the action was attempted. */
  public boolean setDeviceState(String device, boolean on) {
    return send(Command.setDeviceState(idGenerator.nextId(), device, on));
  }

  /** Returns true if the command was enqueued to send. */
  private synchronized boolean send(Command command) {
    if (webSocket == null) return false; // Cannot send, not connected.
    return webSocket.send(jsonAdapters.clientMessage.toJson(new ClientMessage(command)));
  }

  private final class Listener extends WebSocketListener {
    @Override public void onOpen(WebSocket webSocket, Response response) {
      System.out.println("OPEN: " + response);
    }

    @Override public void onMessage(WebSocket webSocket, String text) {
      System.out.println("TEXT: " + text);
    }

    @Override public void onMessage(WebSocket webSocket, ByteString bytes) {
      System.out.println("BYTES: " + bytes);
    }

    @Override public void onClosing(WebSocket webSocket, int code, String reason) {
      synchronized (HarmonyClient.this) {
        if (webSocket != HarmonyClient.this.webSocket) return;
        System.out.println("CLOSING: " + code + ": " + reason);
        disconnect();
        connect();
      }
    }

    @Override public void onFailure(WebSocket webSocket, Throwable t, Response response) {
      synchronized (HarmonyClient.this) {
        if (webSocket != HarmonyClient.this.webSocket) return;
        System.out.println("FAILURE: " + t + " /" + response);
        disconnect();
      }
    }
  }

  public static final class Builder {
    private IdGenerator idGenerator = IdGenerator.get();
    private HttpUrl url;
    private String hubId;
    private OkHttpClient httpClient;

    public Builder url(HttpUrl url) {
      this.url = url;
      return this;
    }

    public Builder hubId(String hubId) {
      this.hubId = hubId;
      return this;
    }

    public Builder httpClient(OkHttpClient httpClient) {
      this.httpClient = httpClient;
      return this;
    }

    public HarmonyClient build() {
      return new HarmonyClient(this);
    }
  }
}
