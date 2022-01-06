/*
 * Copyright 2021 DataCanvas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.dingodb.dingokv;

import com.alipay.sofa.jraft.util.FileOutputSignalHandler;
import com.alipay.sofa.jraft.util.MetricReporter;
import com.alipay.sofa.jraft.util.SystemPropertyUtil;
import io.dingodb.dingokv.metrics.KVMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

// Refer to SOFAJRaft: <A>https://github.com/sofastack/sofa-jraft/<A/>
public class DingoKVMetricsSignalHandler extends FileOutputSignalHandler {
    private static Logger LOG = LoggerFactory.getLogger(DingoKVMetricsSignalHandler.class);

    private static final String DIR = SystemPropertyUtil.get("dingokv.signal.metrics.dir", "");
    private static final String BASE_NAME = "dingokv_metrics.log";

    @Override
    public void handle(final String signalName) {
        try {
            final File file = getOutputFile(DIR, BASE_NAME);

            LOG.info("Printing dingokv metrics with signal: {} to file: {}.", signalName, file);

            try (final PrintStream out = new PrintStream(new FileOutputStream(file, true))) {
                final MetricReporter reporter = MetricReporter.forRegistry(KVMetrics.metricRegistry()) //
                    .outputTo(out) //
                    .prefixedWith("-- dingokv") //
                    .build();
                reporter.report();
            }
        } catch (final IOException e) {
            LOG.error("Fail to print dingokv metrics.", e);
        }
    }
}