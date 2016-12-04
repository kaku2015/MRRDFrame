/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
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
 *
 */
package com.skr.mrrdframe.repository.network.subscriber;

import rx.functions.Func1;

/**
 * @author 咖枯
 * @since 2016/12/4
 */
public class ResultMap<T> implements Func1<ResultModel<T>, T> {


    @Override
    public T call(ResultModel<T> httpResult) {
        if ("200".equals(httpResult.getStatus())) {
            return httpResult.getData();
        } else {
            throw new RuntimeException("请求失败(code=" + httpResult.getStatus() + ",message=" + httpResult.getMessage() + ")");
        }
    }
}
