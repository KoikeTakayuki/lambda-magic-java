package jp.lambdamagic.web.http.service.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface HttpRestHandler {
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException;
}