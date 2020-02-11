/*
 * #%L
 * Alfresco Share WAR
 * %%
 * Copyright (C) 2005 - 2020 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software. 
 * If the software was purchased under a paid Alfresco license, the terms of 
 * the paid license agreement will prevail.  Otherwise, the software is 
 * provided under the following open source license terms:
 * 
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package org.alfresco.web.scripts;

import org.alfresco.web.site.servlet.config.AIMSConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.servlet.WebScriptServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

public class AIMSEnablement extends DeclarativeWebScript
{
    private static Log logger = LogFactory.getLog(AIMSEnablement.class);

    private boolean enabled = false;

    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {
        if (req instanceof WebScriptServletRequest)
        {
            try
            {
                WebScriptServletRequest webScriptServletRequest = (WebScriptServletRequest) req;
                ServletContext servletContext = webScriptServletRequest.getHttpServletRequest().getServletContext();
                WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
                AIMSConfig config = (AIMSConfig) webApplicationContext.getBean("aimsConfig");
                this.enabled = config.isAIMSEnabled();
            }
            catch (Exception e)
            {
                if (logger.isErrorEnabled())
                {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("aimsEnabled", enabled);

        return model;
    }
}
