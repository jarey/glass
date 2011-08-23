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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author damien bourdette
 * @version \$Revision$
 */
@Controller
public class LogsController {
    @Inject
    protected Logs logs;

    @RequestMapping("/logs")
    public String history(Model model) {
        model.addAttribute("logs", logs.getLogs());

        return "logs";
    }
}
