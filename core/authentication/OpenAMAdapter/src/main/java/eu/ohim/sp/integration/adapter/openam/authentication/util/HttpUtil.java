/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2008 Sun Microsystems Inc. All Rights Reserved
 * 
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at
 * https://opensso.dev.java.net/public/CDDLv1.0.html or
 * opensso/legal/CDDLv1.0.txt
 * See the License for the specific language governing
 * permission and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at opensso/legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * 
 * "Portions Copyrighted 2008 Robert Dale <robdale@gmail.com>"
 * 
 * $Id: HttpUtil.java,v 1.2 2009-02-26 18:20:57 wstrange Exp $
 * 
 */
package eu.ohim.sp.integration.adapter.openam.authentication.util;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpUtil {

    public static void printCookies(HttpServletRequest request) {
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = (String) headers.nextElement();
            System.out.println("Header: " + header + " =" + request.getHeader(header));
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("Cookies are null!");
            return;
        }
        if (cookies.length == 0) {
            System.out.println("Cookies are empty!");
        } else {
            System.out.println("Cookies.length: " + cookies.length);
            for (Cookie cookie : cookies) {
                String comment = cookie.getComment();
                String domain = cookie.getDomain();
                Integer maxAge = cookie.getMaxAge();
                String name = cookie.getName();
                String path = cookie.getPath();
                Boolean secure = cookie.getSecure();
                String value = cookie.getValue();
                Integer version = cookie.getVersion();
                System.out.println("Cookie: name: " + name + " domain: " + domain + " path: " + path + " value: "
                        + value + " secure: " + secure + " maxAge: " + maxAge + " version: " + version
                        + " comment " + comment);

            }
        }
    }

    public static HttpServletRequest unwrapOriginalHttpServletRequest(HttpServletRequest request) {
        if (request instanceof HttpServletRequestWrapper) {
            System.out.println("Found HttpServletRequestWrapper: unwrapping..");
            HttpServletRequestWrapper wrapper = (HttpServletRequestWrapper) request;
            ServletRequest servletRequest = wrapper.getRequest();
            if (servletRequest instanceof HttpServletRequest) {
                System.out.println("Unwrapped original HttpServletRequest");
                request = (HttpServletRequest) servletRequest;
            } else {
                System.out.println("Unwrapped a " + servletRequest);
            }
        } else {
            System.out.println("Found a " + request);
        }
        return request;
    }
}
