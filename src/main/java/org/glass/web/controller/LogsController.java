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

package org.glass.web.controller;

import javax.inject.Inject;

import org.glass.log.Logs;
import org.glass.util.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author damien bourdette
 */
@Controller
public class LogsController {
    @Inject
    protected Logs logs;

    @RequestMapping("/logs")
    public String logs(@RequestParam(defaultValue = "0") int index, Model model) {
        model.addAttribute("page", logs.getLogs(Query.oneBasedIndex(index)));

        return "logs";
    }

    @RequestMapping("/logs/{executionId}")
    public String logs(@PathVariable Long executionId, @RequestParam(defaultValue = "0") int index, Model model) {
        model.addAttribute("executionId", executionId);
        model.addAttribute("page", logs.getLogs(executionId, Query.oneBasedIndex(index)));

        return "logs";
    }
}
