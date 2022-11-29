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

package io.dingodb.sdk.operation.number;

import io.dingodb.sdk.operation.Cloneable;
import io.dingodb.sdk.operation.unit.Value;

import java.io.Serializable;

public interface ComputeNumber<N extends ComputeNumber<N>>
    extends Value<Number>, Cloneable<N>, Comparable<N>, Serializable {

    /**
     * Add num.
     */
    N add(ComputeNumber<?> num);

    /**
     * Subtract num.
     */
    N subtract(ComputeNumber<?> num);

    /**
     * Multiply num.
     */
    N multiply(ComputeNumber<?> num);

    /**
     * Divide num.
     */
    N divide(ComputeNumber<?> num);

    /**
     * Remainder num.
     */
    N remainder(ComputeNumber<?> num);

    /**
     * Set number value.
     */
    N value(Number value);

    /**
     * Signum.
     *
     * @return -1, 0, or 1 as the value of this num is negative, zero, or positive.
     */
    int signum();

    /**
     * Abs.
     */
    N abs();

    /**
     * Negate.
     */
    N negate();

    @Override
    N fastClone();

    /**
     * Return int value.
     */
    default int intValue() {
        return value().intValue();
    }

    /**
     * Return long value.
     */
    default long longValue() {
        return value().longValue();
    }

    /**
     * Return float value.
     */
    default float floatValue() {
        return value().floatValue();
    }

    /**
     * Return double value.
     */
    default double doubleValue() {
        return value().doubleValue();
    }

    /**
     * Return byte value.
     */
    default byte byteValue() {
        return value().byteValue();
    }

    /**
     * Return short value.
     */
    default short shortValue() {
        return value().shortValue();
    }

    /**
     * Return number for cls.
     */
    default <N extends ComputeNumber<N>> N cast(Class<N> cls) throws Exception {
        return cls.newInstance().value(this.value());
    }

    static <N extends ComputeNumber<N>> N max(N n1, N n2) {
        return n1.compareTo(n2) > 0 ? n1 : n2;
    }

    static <N extends ComputeNumber<N>> N min(N n1, N n2) {
        return n1.compareTo(n2) < 0 ? n1 : n2;
    }

}