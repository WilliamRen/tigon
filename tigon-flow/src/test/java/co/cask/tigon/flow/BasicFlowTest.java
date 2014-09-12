/*
 * Copyright 2014 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package co.cask.tigon.flow;

import co.cask.tigon.api.annotation.ProcessInput;
import co.cask.tigon.api.annotation.Tick;
import co.cask.tigon.api.flow.Flow;
import co.cask.tigon.api.flow.FlowSpecification;
import co.cask.tigon.api.flow.flowlet.AbstractFlowlet;
import co.cask.tigon.api.flow.flowlet.OutputEmitter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class BasicFlowTest extends EndtoEndFlowTest {

  @Test
  public void testFlow() {
    deployFlow(BasicFlow.class);
  }
}

class BasicFlow implements Flow {

  @Override
  public FlowSpecification configure() {
    return FlowSpecification.Builder.with()
      .setName("testFlow")
      .setDescription("")
      .withFlowlets()
      .add("generator", new GeneratorFlowlet())
      .add("sink", new SinkFlowlet())
      .connect()
      .from("generator").to("sink")
      .build();
  }
}

class GeneratorFlowlet extends AbstractFlowlet {

  private OutputEmitter<Integer> intEmitter;

  @Tick(delay = 1L, unit = TimeUnit.SECONDS)
  public void process() {
    intEmitter.emit(4);
  }
}

class SinkFlowlet extends AbstractFlowlet {

  @ProcessInput
  public void process(Integer value) {

  }
}
