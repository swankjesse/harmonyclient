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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class IdGeneratorTest {
  @Test public void test() throws Exception {
    IdGenerator idGenerator = new IdGenerator("aaaabbbbccccdddd");
    assertThat(idGenerator.nextId()).isEqualTo("aaaabbbbccccdddd#00_00#00-000-01");
    assertThat(idGenerator.nextId()).isEqualTo("aaaabbbbccccdddd#00_00#00-000-02");
    assertThat(idGenerator.nextId()).isEqualTo("aaaabbbbccccdddd#00_00#00-000-03");
    idGenerator.skip(200);
    assertThat(idGenerator.nextId()).isEqualTo("aaaabbbbccccdddd#00_00#00-002-04");
    idGenerator.skip(500000);
    assertThat(idGenerator.nextId()).isEqualTo("aaaabbbbccccdddd#00_00#05-002-05");
  }
}
