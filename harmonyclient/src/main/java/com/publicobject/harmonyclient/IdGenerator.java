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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates client request IDs like "9c926b8f02f6aeeb#cm_m8#m8-176-12" which is similar to the
 * format used by the Harmony Android app. These IDs can be used to map response messages to the
 * commands that triggered them.
 */
final class IdGenerator {
  private final String prefix;
  private final AtomicInteger nextId = new AtomicInteger(1);

  IdGenerator(String prefix) {
    if (prefix.length() != 16) throw new IllegalArgumentException("prefix.length() != 16");
    this.prefix = prefix;
  }

  static IdGenerator get() {
    return new IdGenerator(Util.randomByteString(8).hex());
  }

  String nextId() {
    String s = String.format("%011d", nextId.getAndIncrement());
    return prefix + "#" + s.substring(0, 2) + "_" + s.substring(2, 4) + "#" + s.substring(4, 6)
        + "-" + s.substring(6, 9) + "-" + s.substring(9, 11);
  }

  void skip(int delta) {
    nextId.getAndAdd(delta);
  }
}
