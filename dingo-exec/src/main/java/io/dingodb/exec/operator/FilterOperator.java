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

package io.dingodb.exec.operator;

import io.dingodb.exec.dag.Vertex;
import io.dingodb.exec.fin.Fin;
import io.dingodb.exec.operator.params.FilterParam;

public final class FilterOperator extends SoleOutOperator {
    public static final FilterOperator INSTANCE = new FilterOperator();

    public FilterOperator() {
    }

    @Override
    public synchronized boolean push(int pin, Object[] tuple, Vertex vertex) {
        FilterParam params = vertex.getParam();
        // The eval result may be `null`
        Boolean v = (Boolean) params.getFilter().eval(tuple);
        if (v != null && v) {
            return vertex.getSoleEdge().transformToNext(tuple);
        }
        return true;
    }

    @Override
    public  void fin(int pin, Fin fin, Vertex vertex) {
        vertex.getSoleEdge().fin(fin);
    }

}
