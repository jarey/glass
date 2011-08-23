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

import org.glass.configuration.Configuration;
import org.joda.time.DateTime;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

/**
 * The home page !
 */
@Controller
public class ConfigurationController {

    @Inject
    private Configuration configuration;

    @RequestMapping("/configuration")
    public String configuration(Model model) throws SchedulerException {
        model.addAttribute("configuration", configuration);

        return "configuration";
    }

}