/*
 * Project:  jBrandy Core
 * Outline:  jBrandy framework core components
 *
 * File:     AbstractSecretary.java
 * Folder:   src/main/java/com/soulwarelabs/jbrandy/core/filters
 * Revision: 1.03, 01 December 2014
 * Created:  28 November 2014
 * Authors:  Ilya Gubarev
 *
 * Copyright (c) 2014 Soulware Labs, Ltd.
 * Contact information is available at "http://www.soulwarelabs.com".
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       "http://www.apache.org/licenses/LICENSE-2.0".
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soulwarelabs.jbrandy.core.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web filter for resource dispatching.
 *
 * @see Filter
 *
 * @since v1.0.0
 *
 * @author Ilya Gubarev
 * @version 01 December 2014
 */
public abstract class AbstractSecretary implements Filter {

    /**
     * Filter action.
     *
     * @since v1.0.0
     */
    public static enum Action {

        /**
         * Forwarding HTTP request.
         *
         * @since v1.0.0
         */
        FORWARD,

        /**
         * Redirecting HTTP request.
         *
         * @since v1.0.0
         */
        REDIRECT;
    }

    /**
     * Default filter action.
     *
     * @since v1.0.0
     */
    public static final Action DEFAULT_ACTION = Action.FORWARD;

    /**
     * Default path converter.
     *
     * @since v1.0.0
     */
    public static final String DEFAULT_CONVERTER = "%s";

    private Action action = DEFAULT_ACTION;
    private String converter = DEFAULT_CONVERTER;
    private final Set<String> exclusions = new HashSet<String>();
    private final Set<String> triggers = new HashSet<String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String context = httpRequest.getContextPath();
        String url = httpRequest.getRequestURI().substring(context.length());
        for (String exclusion : getExclusions()) {
            if (match(url, exclusion)) {
                chain.doFilter(request, response);
                return;
            }
        }
        boolean triggered = true;
        for (String trigger : getTriggers()) {
            triggered = false;
            if (match(url, trigger)) {
                triggered = true;
                break;
            }
        }
        if (!triggered) {
            chain.doFilter(request, response);
            return;
        }
        url = convert(url);
        if (getAction() == Action.REDIRECT) {
            ((HttpServletResponse) response).sendRedirect(context + url);
        } else {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 
     *
     * @return 
     *
     * @since v1.0.0
     */
    protected Action getAction() {
        return action;
    }

    /**
     * 
     *
     * @param action 
     *
     * @since v1.0.0
     */
    protected void setAction(Action action) {
        this.action = action;
    }

    /**
     * 
     *
     * @return 
     *
     * @since v1.0.0
     */
    protected String getConverter() {
        return converter;
    }

    /**
     * 
     *
     * @param converter 
     *
     * @since v1.0.0
     */
    protected void setConverter(String converter) {
        this.converter = converter;
    }

    /**
     * 
     *
     * @return 
     *
     * @since v1.0.0
     */
    protected Set<String> getExclusions() {
        return exclusions;
    }

    /**
     * 
     *
     * @param exclusions 
     *
     * @since v1.0.0
     */
    protected void addExclusions(String... exclusions) {
        this.exclusions.addAll(Arrays.asList(exclusions));
    }

    /**
     * 
     *
     * @param exclusion 
     *
     * @since v1.0.0
     */
    protected void removeExclusion(String exclusion) {
        exclusions.remove(exclusion);
    }

    /**
     * 
     *
     * @return 
     *
     * @since v1.0.0
     */
    protected Set<String> getTriggers() {
        return triggers;
    }

    /**
     * 
     *
     * @param triggers 
     *
     * @since v1.0.0
     */
    protected void addTriggers(String... triggers) {
        this.triggers.addAll(Arrays.asList(triggers));
    }

    /**
     * 
     *
     * @param trigger 
     *
     * @since v1.0.0
     */
    protected void removeTrigger(String trigger) {
        triggers.remove(trigger);
    }

    /**
     * 
     *
     * @param original
     * @return 
     *
     * @since v1.0.0
     */
    protected String convert(String original) {
        return String.format(converter, original);
    }

    /**
     * 
     *
     * @param string
     * @param pattern
     * @return 
     *
     * @since v1.0.0
     */
    protected boolean match(String string, String pattern) {
        return string.matches(pattern);
    }
}
