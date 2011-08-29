/*
 * Copyright 2011 Damien Bourdette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dbourdette.glass.web.controller;

import javax.inject.Inject;

import com.github.dbourdette.glass.history.History;
import com.github.dbourdette.glass.util.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author damien bourdette
 */
@Controller
public class HistoryController {
    @Inject
    protected History history;

    @RequestMapping("/history")
    public String history(@RequestParam(defaultValue = "0") int index, Model model) {
        model.addAttribute("page", history.getLogs(Query.oneBasedIndex(index)));

        return "history";
    }

    @RequestMapping("/history/{jobGroup}/{jobName}")
    public String history(@PathVariable String jobGroup, @PathVariable String jobName, @RequestParam(defaultValue = "0") int index,  Model model) {
        model.addAttribute("page", history.getLogs(jobGroup, jobName, Query.oneBasedIndex(index)));

        return "history";
    }
}