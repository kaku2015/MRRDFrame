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
package com.skr.mrrdframe.repository.network.service;

import com.skr.mrrdframe.repository.network.entity.Express;
import com.skr.mrrdframe.repository.network.entity.ResultModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author 咖枯
 * @since 2016/12/3
 */
public interface HttpService {

    @GET("query")
    Observable<ResultModel<List<Express>>> getExpress(
            @Query("type") String type,
            @Query("postid") String postid);
}