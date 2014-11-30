package com.soulwarelabs.jbrandy.core;

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

public abstract class AbstractSecretaryFilter implements Filter {

    public static enum Action {

        FORWARD,

        REDIRECT;
    }

    public static final Action DEFAULT_ACTION = Action.FORWARD;

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

    protected Action getAction() {
        return action;
    }

    protected void setAction(Action action) {
        this.action = action;
    }

    protected String getConverter() {
        return converter;
    }

    protected void setConverter(String converter) {
        this.converter = converter;
    }

    protected Set<String> getExclusions() {
        return exclusions;
    }

    protected void addExclusions(String... exclusions) {
        this.exclusions.addAll(Arrays.asList(exclusions));
    }

    protected void removeExclusion(String exclusion) {
        exclusions.remove(exclusion);
    }

    protected Set<String> getTriggers() {
        return triggers;
    }

    protected void addTriggers(String... triggers) {
        this.triggers.addAll(Arrays.asList(triggers));
    }

    protected void removeTrigger(String trigger) {
        triggers.remove(trigger);
    }

    protected String convert(String original) {
        return String.format(converter, original);
    }

    protected boolean match(String string, String pattern) {
        return string.matches(pattern);
    }
}
