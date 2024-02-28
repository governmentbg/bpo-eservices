<%--
  Created by IntelliJ IDEA.
  User: denislav.veizov
  Date: 9.8.2018 г.
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="ns"><portlet:namespace/></c:set>
<portlet:resourceURL var="updateApplicationResult" id="updateApplicationResult"/>

<svg id="icon-sprite" style="display: none">
    <symbol id="icon-design" viewBox="0 0 50 50">
        <path fill="#054F87"
              d="M28.2,18.8l5.3-5.3L25,5l-8.5,8.5l5.3,5.3 l-1.5,1.5l-6.7-6.8L25,2l11.5,11.5l-6.7,6.8L28.2,18.8z M19.3,21.3l-1.5,1.5L14,19l-7,7l7,7l3.9-3.9l1.5,1.5L14,36L4,26l10-10 L19.3,21.3z M16.5,35.5l3.9-3.9l1.5,1.5l-2.4,2.4L24,40h0l0,8h-5v-1h3v-6L16.5,35.5z M31,47v1h-5l0-8h0l4.5-4.5l-2.4-2.4l1.5-1.5 l3.9,3.9L28,41v6H31z M30.8,24.2L29,26l1.6,1.6l-1.5,1.5L26,26l3.3-3.3L30.8,24.2z M25,22l1.8-1.8l1.5,1.5L25,25l-3.3-3.3l1.5-1.5 L25,22z M28.1,30.1l-1.5,1.5L25,30l-1.6,1.6l-1.5-1.5L25,27L28.1,30.1z M20.7,22.7L24,26l-3.1,3.1l-1.5-1.5L21,26l-1.8-1.8 L20.7,22.7z M32.1,29.1L36,33l7-7l-7-7l-3.7,3.7l-1.5-1.5L36,16l10,10L36,36l-5.4-5.5L32.1,29.1z"></path>
    </symbol>
    <symbol id="icon-trademark" viewBox="0 0 50 50">
        <path fill="#054F87"
              d="M25,49C11.7,49,1,38.3,1,25S11.7,1,25,1s24,10.7,24,24S38.3,49,25,49z M25,2 C12.3,2,2,12.3,2,25c0,12.7,10.3,23,23,23c12.7,0,23-10.3,23-23C48,12.3,37.7,2,25,2z M40.7,34l-0.8-15l-4.6,15h-3.4l-4.6-15 l-0.8,15H23l1.4-18h5.3l3.9,13.3L37.6,16h5.1L44,34H40.7z M15,34h-3V19H6v-3h15v3h-6V34z"></path>
    </symbol>
    <symbol id="icon-eSrvices" viewBox="0 0 50 50">
        <path fill="#054F87" d="M45,43H26v5h7c0.6,0,1,0.4,1,1s-0.4,1-1,1H17c-0.6,0-1-0.4-1-1 s0.4-1,1-1h7v-5H5c-1.1,0-2-0.9-2-2V19c0-1.1,0.9-2,2-2h10v2H6c-1,0-1,0-1,1v20c0,1,0,1,1,1h38c1,0,1,0,1-1V20c0-1,0-1-1-1h-9v-2 h10c1.1,0,2,0.9,2,2v22C47,42.1,46.1,43,45,43z M39,14c-1.9,0-3.4-1.3-3.9-3c0,0,0.1,0-1.1,0c-2,0-3,1-3,3c0,1,0,16,0,16 c0,0.6-0.4,1-1,1s-1-0.4-1-1V13c0-2,2-4,4-4h2.1c0.4-1.7,2-3,3.9-3c2.2,0,4,1.8,4,4S41.2,14,39,14z M26,7.9V35c0,0.6-0.4,1-1,1 s-1-0.4-1-1V7.9c-1.7-0.4-3-2-3-3.9c0-2.2,1.8-4,4-4s4,1.8,4,4C29,5.9,27.7,7.4,26,7.9z M21,30c0,0.6-0.4,1-1,1s-1-0.4-1-1 c0,0,0-15,0-16c0-2-1-3-3-3c-1,0-1.1,0-1.1,0c-0.4,1.7-2,3-3.9,3c-2.2,0-4-1.8-4-4s1.8-4,4-4c1.9,0,3.4,1.3,3.9,3H17c2,0,4,2,4,4 V30z"></path>
    </symbol>
    <symbol id="icon-DocSigned" viewBox="0 0 50 50">
        <path fill="#054F87" d="M45,50H13c-2.2,0-4-1.8-4-4v-3H4c-1.7,0-3-1.3-3-3v-8 c0-1.7,1.3-3,3-3h5V4c0-2.2,1.8-4,4-4h32c2.2,0,4,1.8,4,4v42C49,48.2,47.2,50,45,50z M4,30c-1.1,0-2,0.9-2,2v8c0,1.1,0.9,2,2,2h9.5 l4.5-6l-4.5-6H4z M47,5c0-2-1-3-3-3H14c-2,0-3,1-3,3v24h3l5,7l-5,7h-3v2c0,2,1,3,3,3h30c2,0,3-1,3-3V5z M40.7,39.3 c-1.9-0.3-3.7,0.8-5.6,0.4c-1.1-0.3-1.5-1-1.5-1s-2.6,1.8-4,1.1c-1.2-0.7-1-2.4-1-2.4s-1,0.8-2.3,1.7c-0.7,0.4-1.5-0.2-1.6-1 c-0.1-2.3,0.7-4.4,0.7-4.4s-1,1.2-1.2,1.6c-0.8,1.1-1.5,2.4-2.1,3.7c-0.5,1.3-2.6,0.7-2-0.6c0.8-2.1,1.9-3.9,3.3-5.6 c0.9-1.1,2.5-3.4,4.2-2.8c0.5,0.2,0.7,0.6,0.8,1.1c0.1,1-0.5,1.9-0.8,2.8c-0.1,0.5-0.4,1.7-0.4,1.7s0.8-0.5,2.1-1.4 c0.7-0.5,1.6,0.1,1.6,1c0,0.5,0,1.1,0,1.6c0,0.2,0,0.4,0,0.6c0,0,0,0,0,0c0.2-0.2,1.2-0.6,1.5-0.8c0.4-0.2,0.7-0.4,1.1-0.6 c0.4-0.2,0.9-0.2,1.3,0.2c0.3,0.4,0.5,0.9,0.9,1c0.5,0.2,1.5-0.3,2-0.4c1.2-0.3,2.4-0.2,3.7,0C42.6,37.4,42,39.6,40.7,39.3z M17,20 h24v1H17V20z M17,14h24v1H17V14z M17,8h24v1H17V8z M21,43h-1v-1h1V43z M24,43h-1v-1h1V43z M27,43h-1v-1h1V43z M30,43h-1v-1h1V43z M33,43h-1v-1h1V43z M36,43h-1v-1h1V43z M39,43h-1v-1h1V43z M42,43h-1v-1h1V43z"></path>
    </symbol>
    <symbol id="icon-utilityModel" viewBox="0 0 50 50">
        <path fill="#054F87" d="M47.5,28.1H45v2.5c0,0.8-0.7,1.5-1.5,1.5S42,31.4,42,30.6v-2.5 h-2.5c-0.8,0-1.5-0.7-1.5-1.5s0.7-1.5,1.5-1.5H42v-2.5c0-0.8,0.7-1.5,1.5-1.5s1.5,0.7,1.5,1.5v2.5h2.5c0.8,0,1.5,0.7,1.5,1.5 S48.3,28.1,47.5,28.1z M40.7,17.2c-1.3,0.6-2.8,0.1-3.4-1.2c0,0,0,0,0,0l-0.1,0C34,11.8,28.8,9,23,9C13.1,9,5,17.1,5,27.1 c0,6,2.9,11.2,7.3,14.5L12,42.2l8.3,4.8C9.4,45.6,1,36.4,1,25.1C1,12.9,10.9,3,23,3c8,0,15.1,4.3,18.9,10.8l0,0c0,0,0,0,0,0 C42.5,15.1,42,16.6,40.7,17.2z M26.6,43.3l-1.5,1.5l-1.6-1.6l-1.6,1.6l-1.5-1.5l3.1-3.1L26.6,43.3z M27.8,35.9l1.5,1.5l-1.8,1.8 l1.6,1.6l-1.5,1.5l-3.1-3.1L27.8,35.9z M23.5,15L35,26.6l-6.7,6.8l-1.5-1.5l5.3-5.3l-8.5-8.5L15,26.6l5.3,5.3l-1.5,1.5L12,26.6 L23.5,15z M23.5,35.1l1.8-1.8l1.5,1.5l-3.3,3.3l-3.3-3.3l1.5-1.5L23.5,35.1z M19.2,35.9l3.3,3.3l-3.1,3.1l-1.5-1.5l1.6-1.6 l-1.8-1.8L19.2,35.9z"/>
    </symbol>
    <symbol id="icon-patent" viewBox="0 0 50 50">
        <path fill="#054F87" d="M36.6,23.6c-1.6,3.3-4.4,5.6-7.5,6.7L24.5,28 l0-6c-0.6,0-0.9-0.2-1.5-0.5c-2.1-1.1-3-3.8-1.9-6c1.1-2.2,3.7-3.1,5.8-2s3,3.8,1.9,6c-0.5,1-1.5,1.7-2.4,2.1l0,5.2l2.8,1.3 c2.4-1,4.4-2.9,5.7-5.4C37.7,17,35.5,10.1,30,7.2s-12.1-0.6-14.9,5c-2.8,5.7-0.6,12.6,4.8,15.5c0,0,0,0,0,0v0l10.3,5.4v2.3 l-11.2-5.9v0c0,0,0,0,0,0c-6.4-3.4-8.9-11.5-5.7-18.1c3.3-6.6,11.1-9.3,17.5-5.9C37.3,8.9,39.8,17,36.6,23.6z M27.2,18.6 c0.6-1.2,0.1-2.7-1.1-3.4c-1.2-0.6-2.6-0.1-3.2,1.1c-0.6,1.2-0.1,2.7,1.1,3.4C25.1,20.3,26.6,19.9,27.2,18.6z M30.3,38l0,3 c0,3-2.2,5-4.8,5c-2.7,0-4.8-2-4.8-5l0-8.4L30.3,38z"/>
    </symbol>
    <symbol id="icon-EUpatent" viewBox="0 0 50 50">
        <path fill="#054F87" d="M44,33.1c-3.2,6-8.9,9.9-15.1,11.1v4.3H25v-2v-2v-3.9V5.4V1.5 c3.4,0,6.9,0.8,10.1,2.5C45.6,9.6,49.6,22.6,44,33.1z M33.3,7.5c-1.4-0.8-2.9-1.3-4.4-1.6v34.3c4.8-1.1,9.1-4.2,11.6-8.9 C45.1,22.7,41.8,12.1,33.3,7.5z M21.1,5.9C16.3,7,12,10.1,9.5,14.8c-1.1,2-1.7,4.2-1.9,6.3h13.6V25H7.5c0.6,5.6,3.9,10.8,9.2,13.6 c1.4,0.7,2.9,1.3,4.3,1.6v4c-2.1-0.4-4.2-1.1-6.2-2.2C4.4,36.5,0.4,23.4,6,12.9c3.2-6,8.8-9.9,15.1-11V5.9z"></path>
    </symbol>
    <symbol id="icon-spc" viewBox="0 0 50 50">
        <path fill="#054F87" d="M33,47c-4.3,0-8.2-1.8-10.9-4.8c-2,2.9-5.3,4.8-9.1,4.8 C6.9,47,2,42.1,2,36V14C2,7.9,6.9,3,13,3s11,4.9,11,11v6l0,0c2.5-1.9,5.6-3,9-3c8.3,0,15,6.7,15,15S41.3,47,33,47z M4,27 c0.6,0,1,0.4,1,1s-0.4,1-1,1v4c0.6,0,1,0.4,1,1s-0.4,1-1,1v1c0,5,4,9,9,9c3.3,0,6.2-1.8,7.7-4.4c-0.6-0.8-1.1-1.7-1.5-2.7 c-0.1,0-0.2,0-0.2,0c-0.6,0-1-0.4-1-1c0-0.4,0.2-0.7,0.6-0.9l0,0C18.2,34.8,18,33.4,18,32c0-2.1,0.5-4.2,1.3-6l0,0H4V27z M13,5 c-5,0-9,4-9,9v10h16.3c0.5-0.8,1.1-1.5,1.7-2.2V14C22,9,18,5,13,5z M23,20.9C23,20.9,23,20.9,23,20.9C23,20.9,23,20.9,23,20.9z M33,19c-7.2,0-13,5.8-13,13s5.8,13,13,13s13-5.8,13-13S40.2,19,33,19z M26.6,39.8c-0.4,0.4-1,0.4-1.4,0s-0.4-1,0-1.4l14.1-14.1 c0.4-0.4,1-0.4,1.4,0s0.4,1,0,1.4L26.6,39.8z M18,31c0-0.1,0-0.2,0.1-0.3c0,0.2,0,0.4,0,0.5C18,31.1,18,31.1,18,31z M16,41 c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S16.6,41,16,41z M16,35c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S16.6,35,16,35z M16,29 c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S16.6,29,16,29z M13,44c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S13.6,44,13,44z M13,38 c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S13.6,38,13,38z M13,32c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S13.6,32,13,32z M10,41 c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S10.6,41,10,41z M10,35c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S10.6,35,10,35z M10,29 c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S10.6,29,10,29z M7,38c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S7.6,38,7,38z M7,32 c-0.6,0-1-0.4-1-1s0.4-1,1-1s1,0.4,1,1S7.6,32,7,32z"></path>
    </symbol>
    <symbol id="icon-sortBreeds" viewBox="0 0 50 50">
        <path fill="#054F87" d="M27.7,6.5c-2.6,0-4.7,7.1-4.7,12.1c0,5.8,1.8,8.5,5.2,8.5c3.8,0,6.5-2.6,6.5-6C34.7,16.2,31.2,6.5,27.7,6.5z M28.2,26.1 c-1.8,0-4.3-0.8-4.3-7.6c0-5.4,2.2-11.2,3.8-11.2c2.5,0,6.1,8.8,6.1,13.6C33.8,24,31.5,26.1,28.2,26.1z M46.4,10.6 c-2-0.3-4.2-1-6.2-2.3c0-0.1,0-0.1-0.1-0.2c-1.6-4-4.9-6.1-8.4-6.1h-1.5c-4.1,0-7.1,2.7-8.1,5.4l-4.5,13.4 c-0.4,0.4-1.2,1.2-1.6,1.7c0.3,0.1,0.5,0.3,0.8,0.4c-0.2,0.2,1.7-1.8,1.7-1.8L23,7.7c0.9-2.3,3.5-4.8,7.3-4.8h1.5 c3.4,0,6.2,2.1,7.6,5.6c0.5,1.3,0.8,2.7,0.8,4.3c0,3.3-0.8,5.4-2.9,7.4C35.9,21.5,36,24,36,26v10h1V26c0-1.7-0.3-4.1,0.9-5.2 c0.3-0.3,0.6-0.6,0.8-0.9c1,0,2,0,3.2,0c3.8,0,6.4-2.2,7.5-5.4c0,0,0,0,0,0c0.4-1.1,0.6-2.3,0.6-3.5 C47.8,10.9,46.4,10.6,46.4,10.6z M41.9,19h-2.6c1.2-1.8,1.7-3.7,1.7-6.3c0-1.1-0.2-2.2-0.4-3.2c2,1.1,4.1,1.7,5.9,2 c0.2,1.2,1,2.2,2.1,2.7C47.6,16.9,45.5,19,41.9,19z M37.4,10.9c0-1-0.8-1.8-1.8-1.8c-1,0-1.8,0.8-1.8,1.8s0.8,1.8,1.8,1.8 C36.6,12.7,37.4,11.9,37.4,10.9z M13.9,38.8c3.1,6.5,7.5,9,8.4,9.2c0.9,0.2,2-1.4,1.7-1.9C18.2,43.8,13.9,38.8,13.9,38.8z M19.6,41.2c1.6,0.2,2.7-0.7,2.9-2.1c0-0.3-0.2-1.3-0.1-2.3c0.1-0.9,0.5-1.8,0.6-2.2c0.2-1.4-0.7-2.5-2.3-2.7 c-1.3-0.1-3.6,0.7-5.7,1.6c1.6-1.9,3.3-4,3.5-5.4c0.3-1.6-0.5-2.9-2-3.2c-0.4-0.1-1.5,0-2.5-0.1c-1-0.2-1.9-0.7-2.3-0.8 c-0.2,0-0.4-0.1-0.5-0.1c-1.2,0-2.2,0.8-2.5,2.2c-0.3,1.4,0.5,4.2,1.4,6.6c-1.9-1.7-4.2-3.6-5.7-4c-1.6-0.4-2.9,0.4-3.2,1.9 c-0.1,0.4,0,1.4-0.2,2.4c-0.2,1-0.8,1.9-0.8,2.2c-0.3,1.4,0.5,2.7,2.1,3.1c1.6,0.4,5.2-0.7,7.8-1.6c-2,1.8-4.5,4.4-5,6 c-0.4,1.5,0.3,2.8,1.7,3.2c0.3,0.1,1.4,0.1,2.4,0.3c0.9,0.3,1.8,0.8,2.2,0.9c1.4,0.4,2.7-0.3,3.1-1.9c0.5-1.7-0.5-5.5-1.3-8.1 C15.1,39,18,41,19.6,41.2z"></path>
    </symbol>
    <symbol id="icon-geoIndications" viewBox="0 0 50 50">
        <path fill="#054F87" d="M42.5,30c-1.1,0-2-0.9-2-2s0.9-2,2-2c1.1,0,2,0.9,2,2 S43.6,30,42.5,30z M33.5,21c-1.1,0-2-0.9-2-2c0-1.1,0.9-2,2-2c1.1,0,2,0.9,2,2C35.5,20.1,34.6,21,33.5,21z M41.4,24.2 c-0.6-3-2-5.7-3.9-7.8c-0.2,0.1-0.4,0.3-0.6,0.4c-0.3-0.5-0.7-0.8-1.2-1.1c0.2-0.1,0.4-0.3,0.6-0.4c-2-1.8-4.3-3.1-6.9-3.8 c0.9,1.2,1.7,2.6,2.4,4.1c-0.5,0.3-0.9,0.6-1.3,1c-1-2-2.1-3.9-3.3-5.6c-0.6-0.1-1.2-0.2-1.7-0.2v8.4c1.4-0.1,2.8-0.3,4.2-0.8 c0,0.2-0.1,0.4-0.1,0.6c0,0.3,0.1,0.7,0.1,1c-1.5,0.5-3,0.8-4.3,0.9v6.3h7.9c-0.1-1.4-0.3-2.8-0.6-4.3c0.3,0.1,0.5,0.1,0.8,0.1 c0.3,0,0.6,0,0.8-0.1c0.4,1.5,0.6,3,0.6,4.2h3.6c-0.1,0.3-0.1,0.6-0.1,0.8s0,0.6,0.1,0.8H35c-0.1,2.3-0.7,5.4-1.9,8.4 c1.7,0.7,3.2,1.5,4.4,2.3c1.9-2.2,3.3-4.8,3.9-7.8c0.4,0.1,0.7,0.2,1.1,0.2c0.2,0,0.4,0,0.6-0.1C41.3,40.6,33.8,47,24.5,47 c-10.6,0-19-8.4-19-19c0-4.4,1.4-8.4,3.9-11.5c-1.1-2.7-1.8-5.2-1.8-6.5c0-3.9,3.1-7,7-7c3.6,0,6.5,2.7,6.9,6.3 c1-0.2,2-0.3,3.1-0.3c9.3,0,16.8,6.4,18.6,15.1c-0.2,0-0.4-0.1-0.6-0.1C42.1,24,41.7,24.1,41.4,24.2z M36.2,40.8 c-1.1-0.8-2.3-1.5-3.8-2.1c-0.8,2.1-1.8,4.1-3.1,5.9C31.9,43.9,34.3,42.6,36.2,40.8z M19.7,44.5c-1.3-1.7-2.3-3.7-3.1-5.9 c-1.5,0.6-2.7,1.3-3.8,2.1C14.7,42.6,17.1,43.9,19.7,44.5z M14.5,6c-2.2,0-4,1.8-4,4s1.8,4,4,4s4-1.8,4-4S16.7,6,14.5,6z M23.7,10.7c-0.6,0-1.1,0.1-1.7,0.2c-0.3,0.4-0.5,0.7-0.8,1.1c-0.4,1.7-1.3,3.9-2.2,6.2c1.6,0.5,3.2,0.8,4.7,0.9V10.7z M23.7,32.1 v-3.3h-5.3c0.1-0.3,0.1-0.5,0.1-0.8s0-0.6-0.1-0.8h5.3v-6.3c-1.6-0.1-3.5-0.5-5.3-1.2c-1.1,2.6-2.2,5-3,6.6c0.7,0.3,1.1,1,1.1,1.8 c0,1.1-0.9,2-2,2c-1.1,0-2-0.9-2-2c0-0.8,0.5-1.5,1.1-1.8c-0.9-1.9-2.3-4.9-3.5-7.9c-1.7,2.5-2.7,5.5-2.8,8.8h3.3 c-0.1,0.3-0.1,0.6-0.1,0.8s0,0.6,0.1,0.8H7.3C7.5,33,9,36.8,11.6,39.6c1.2-0.8,2.7-1.6,4.4-2.3c-0.7-1.8-1.2-3.6-1.5-5.3 c0,0,0,0,0.1,0c0.6,0,1.1-0.1,1.6-0.3c0.3,1.7,0.8,3.3,1.4,5c1-0.4,2.1-0.8,3.1-1c0,0.1,0,0.2,0,0.3c0,0.5,0.1,0.9,0.2,1.3 c-0.9,0.2-1.8,0.4-2.7,0.8c1,2.5,2.3,5,3.9,7c0.6,0.1,1.2,0.2,1.7,0.2v-5.4c0.3,0.1,0.6,0.1,0.8,0.1s0.6,0,0.8-0.1v5.4 c0.6,0,1.1-0.1,1.7-0.2c1.6-2.1,2.9-4.5,3.9-7c-0.9-0.3-1.8-0.6-2.7-0.8c0.1-0.4,0.2-0.8,0.2-1.3c0-0.1,0-0.2,0-0.3 c1,0.3,2.1,0.6,3.1,1c1-2.7,1.6-5.4,1.7-7.9h-7.9v3.3C25.1,32,24.8,32,24.5,32S23.9,32,23.7,32.1z M26.5,36c0,1.1-0.9,2-2,2 c-1.1,0-2-0.9-2-2s0.9-2,2-2C25.6,34,26.5,34.9,26.5,36z"></path>
    </symbol>
    <symbol id="icon-integralSchemas" viewBox="0 0 50 50">
        <path fill="#054F87" d="M45.9,29.4c-2,0-3.6-1.4-4-3.2h-9.1l-3.2-3.4h-5.3l-11.4,8.8H3.4 c1.3,3.1,3.3,5.9,5.8,8.1l1.5-1.5l0,0h8.9l5.4,4.6c0.7-0.6,1.6-1,2.7-1c2.3,0,4.1,1.8,4.1,4.1S30,50,27.8,50 c-2.3,0-4.1-1.8-4.1-4.1c0-0.6,0.1-1.2,0.4-1.7L19.1,40h-7.9l-0.8,0.7c2.3,1.8,5.1,3.1,8,3.8c0,0,0,0,0,0c0.5,0.1,0.8,0.6,0.7,1.1 c-0.1,0.5-0.6,0.8-1.1,0.7l0,0C7.7,43.8,0,34.6,0,23.5c0-4.6,1.3-8.8,3.6-12.5c0,0,0,0,0,0c0.3-0.4,0.8-0.6,1.3-0.3 C5.3,11,5.4,11.6,5.2,12l0,0c-1.5,2.4-2.6,5-3,7.9h6.3l5.1-3.5c0.4-0.3,1-0.3,1.3,0.1c0.3,0.4,0.2,1-0.2,1.3l-5.7,3.9h-7 c0,0.6-0.1,1.2-0.1,1.8c0,0.5,0,1.1,0.1,1.6h9.8l6.2-4.7V14l-5.4-4.8C11.8,9.7,11,10,10.1,10C7.8,10,6,8.1,6,5.9 c0-2.3,1.8-4.1,4.1-4.1c2.3,0,4.1,1.8,4.1,4.1c0,0.7-0.2,1.4-0.5,1.9l6,5.3h0v0l0,0l0,0v7.6c0,0.1,0,0.1,0,0.2v0h0 c-0.1,0.2-0.2,0.4-0.4,0.6l-6.9,5.3H2.1c0.2,1,0.4,2,0.7,2.9H12L23.4,21h6.9l3.3,3.4h8.3c0.4-1.8,2.1-3.2,4-3.2 c2.3,0,4.1,1.8,4.1,4.1C50,27.6,48.2,29.4,45.9,29.4z M27.8,48.2c1.3,0,2.3-1,2.3-2.3c0-1.3-1-2.3-2.3-2.3s-2.3,1-2.3,2.3 C25.5,47.2,26.5,48.2,27.8,48.2z M10.1,3.6c-1.3,0-2.3,1-2.3,2.3c0,1.2,1,2.3,2.3,2.3c1.3,0,2.3-1,2.3-2.3 C12.4,4.6,11.4,3.6,10.1,3.6z M45.9,23.1c-1.3,0-2.3,1-2.3,2.3c0,1.2,1,2.3,2.3,2.3c1.3,0,2.3-1,2.3-2.3 C48.2,24.1,47.1,23.1,45.9,23.1z M12,6.3L12,6.3L12,6.3L12,6.3z M46.5,19.7c-0.5,0.1-1-0.2-1.1-0.7c0,0,0,0,0,0 c-0.3-1.4-0.8-2.8-1.3-4.1H32.5c-0.4,1.8-2.1,3.2-4,3.2c-2.3,0-4.1-1.8-4.1-4.1s1.8-4.1,4.1-4.1c2,0,3.6,1.4,4,3.2h10.7 c-3.2-5.8-9.1-10-16-11.1c-0.7,1.2-1.9,3.4-2.7,4.8c-0.1,0.2-0.3,0.4-0.5,0.5l-5.3,2c-0.5,0.2-1-0.1-1.2-0.5s0.1-1,0.5-1.2l4.9-1.9 l2.3-4c-0.4,0-0.9,0-1.4,0c-2.4,0-4.7,0.4-6.8,1.1c0,0,0,0,0,0c-0.5,0.2-1-0.1-1.2-0.6s0.1-1,0.6-1.1v0C18.8,0.4,21.3,0,23.9,0 c11.5,0,21.1,8,23.3,18.6l0,0C47.3,19.1,47,19.6,46.5,19.7z M28.4,11.8c-1.3,0-2.3,1-2.3,2.3c0,1.2,1,2.3,2.3,2.3s2.3-1,2.3-2.3 C30.7,12.8,29.7,11.8,28.4,11.8z M35.8,41.7C35.8,41.7,35.8,41.7,35.8,41.7c1.4-0.9,2.7-2,3.9-3.2l-3.7-5.3h-8.9 c-0.4,1.8-2.1,3.2-4,3.2c-2.3,0-4.1-1.8-4.1-4.1c0-2.2,1.8-4.1,4.1-4.1c2,0,3.6,1.4,4,3.2h9.7l4,5.7c1.5-1.8,2.7-3.9,3.6-6.2 c0,0,0,0,0,0c0.2-0.5,0.7-0.7,1.2-0.5c0.5,0.2,0.7,0.7,0.5,1.2l0,0c-1.8,4.8-5.1,8.9-9.4,11.7l0,0c-0.4,0.3-1,0.2-1.3-0.3 C35.3,42.6,35.4,42,35.8,41.7z M23.2,30.1c-1.3,0-2.3,1-2.3,2.3c0,1.3,1,2.3,2.3,2.3s2.3-1,2.3-2.3C25.5,31.1,24.4,30.1,23.2,30.1z"></path>
    </symbol>
</svg>

<div class="form type homePage" style="margin-bottom: -35px;">
    <section>
        <header>
            <h2><spring:message code="section.header"/></h2>
        </header>
        <div class="portlet-layout row-fluid">
            <div class="portlet-column yui3-dd-drop">
                <div>
                    <div>
                        <div class="w25 float-left ">
                            <div class="align-center font-size-15 mB10px">
                                <span class="jumping-text" id="${ns}trademark-count"></span>
                            </div>
                            <div class="align-center zoom">
                                <a target="_blank" href="${urlMap['trademarks']}">
                                    <svg class="icon align-center w60px h60px">
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-trademark"></use>
                                    </svg>
                                </a>
                            </div>
                            <div class="align-center">
                                <span class="a-btn-text font-size-15"><spring:message code="mark.title"/></span>
                            </div>
                        </div>

                        <div class="w25 float-left">
                            <div class="align-center font-size-15 mB10px">
                                <span class="jumping-text" id="${ns}patent-count"></span>
                            </div>
                            <div class="align-center zoom">
                                <a target="_blank" href="${urlMap['patents']}">
                                    <svg class="icon align-center w60px h60px">
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-patent"></use>
                                    </svg>
                                </a>
                            </div>
                            <div class="align-center">
                                <span class="a-btn-text font-size-15"><spring:message code="patent.title"/></span>
                            </div>
                        </div>

                        <div class="w25 float-left">
                            <div class="align-center font-size-15 mB10px">
                                <span class="jumping-text" id="${ns}utility-model-count"></span>
                            </div>
                            <div class="align-center zoom">
                                <a target="_blank" href="${urlMap['utilityModels']}">
                                    <svg class="icon align-center w60px h60px">
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-utilityModel"></use>
                                    </svg>
                                </a>
                            </div>
                            <div class="align-center">
                                <span class="a-btn-text font-size-15"><spring:message code="utility.model.title"/></span>
                            </div>
                        </div>

                        <div class="w25 float-left">
                            <div id="${ns}design-count-div" class="align-center font-size-15 mB10px">
                                <span id="${ns}design-count"></span>
                            </div>
                            <div class="align-center zoom">
                                <a target="_blank" href="${urlMap['designs']}">
                                    <svg class="icon align-center w60px h60px">
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-design"></use>
                                    </svg>
                                </a>
                            </div>
                            <div class="align-center">
                                <span class="a-btn-text font-size-15"><spring:message code="disign.title"/></span>
                            </div>
                        </div>

                        <div class="clear"></div>
                    </div>

                    <hr>

                    <div>
                        <div class="w25 float-left">
                            <div class="align-center font-size-15 mB10px">
                                <span class="jumping-text" id="${ns}eu-patent-count"></span>
                            </div>
                            <div class="align-center zoom">
                                <a target="_blank" href="${urlMap['euPatents']}">
                                    <svg class="icon align-center w60px h60px">
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-EUpatent"></use>
                                    </svg>
                                </a>
                            </div>
                            <div class="align-center">
                                <span class="a-btn-text font-size-15"><spring:message code="eu.patent.title"/></span>
                            </div>
                        </div>

                        <div class="w25 float-left">
                            <div class="align-center font-size-15 mB10px">
                                <span id="${ns}spc-count"></span>
                            </div>
                            <div class="align-center zoom">
                                <a target="_blank" href="${urlMap['spc']}">
                                    <svg class="icon align-center w60px h60px">
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-spc"></use>
                                    </svg>
                                </a>
                            </div>
                            <div class="align-center">
                                <span class="a-btn-text font-size-15"><spring:message code="spc.title"/></span>
                            </div>
                        </div>

                        <div class="w25 float-left">
                            <div class="align-center font-size-15 mB10px">
                                <span id="${ns}sort-breeds-count"></span>
                            </div>
                            <div class="align-center zoom">
                                <a target="_blank" href="${urlMap['sortBreeds']}">
                                    <svg class="icon align-center w60px h60px">
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-sortBreeds"></use>
                                    </svg>
                                </a>
                            </div>
                            <div class="align-center">
                                <span class="a-btn-text font-size-15"><spring:message code="sortBreeds.title"/></span>
                            </div>
                        </div>

                        <div class="w25 float-left">
                            <div class="align-center font-size-15 mB10px">
                                <span id="${ns}geo-indications-count"></span>
                            </div>
                            <div class="align-center zoom">
                                <a target="_blank" href="${urlMap['geoIndications']}">
                                    <svg class="icon align-center w60px h60px">
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-geoIndications"></use>
                                    </svg>
                                </a>
                            </div>
                            <div class="align-center">
                                <span class="a-btn-text font-size-15"><spring:message code="geo.indications.title"/></span>
                            </div>
                        </div>

                        <div class="clear"></div>

                    </div>

                    <hr>

                    <div>
                        <div class="w25 float-left">
                            <div class="align-center font-size-15 mB10px">
                                <span id="${ns}integral-schemas-count"></span>
                            </div>
                            <div class="align-center zoom">
                                <a target="_blank" href="${urlMap['integralSchemas']}">
                                    <svg class="icon align-center w60px h60px">
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-integralSchemas"></use>
                                    </svg>
                                </a>
                            </div>
                            <div class="align-center">
                                <span class="a-btn-text font-size-15"><spring:message code="integral.schemas.title"/></span>
                            </div>
                        </div>

                        <div class="w25 float-left">
                            <div class="align-center font-size-15 mB10px">
                                <span id="${ns}eservice-count"></span>
                            </div>
                            <div class="align-center zoom">
                                <a target="_blank" href="${urlMap['eservices']}">
                                    <svg class="icon align-center w60px h60px">
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-eSrvices"></use>
                                    </svg>
                                </a>
                            </div>
                            <div class="align-center">
                                <span class="a-btn-text font-size-15"><spring:message code="eservices.title"/></span>
                            </div>
                        </div>

                        <div class="clear mB1em"></div>

                    </div>

                </div>
            </div>
        </div>
    </section>
</div>

<script>

    function updateResult() {
        $.ajax({
            dataType: "json",
            url: "${updateApplicationResult}",
            success: function (data) {
                var trademarks = data['TM'];
                setCountValue(trademarks, '${ns}trademark-count');

                var designs = data['DS'];
                setCountValue(designs, '${ns}design-count');

                var eservices = data['ES_EFILING'];
                setCountValue(eservices, '${ns}eservice-count');

                var patents = data['PT_EFILING'];
                setCountValue(patents, '${ns}patent-count');

                var utilityModel = data['UM_EFILING'];
                setCountValue(utilityModel, '${ns}utility-model-count');

                var euPatents = data['EP_EFILING'];
                setCountValue(euPatents, '${ns}eu-patent-count');

                var spcCount = data['SPC_EFILING'];
                setCountValue(spcCount, '${ns}spc-count');

                var sortBreedsCount = data['SV_EFILING'];
                setCountValue(sortBreedsCount, '${ns}sort-breeds-count');

                var geoIndicationsCount = data['GI_EFILING'];
                setCountValue(geoIndicationsCount, '${ns}geo-indications-count');

                var integralSchemasCount = data['IS_EFILING'];
                setCountValue(integralSchemasCount, '${ns}integral-schemas-count');
            }
        });
        setTimeout(updateResult, 60000);
    }

    function setCountValue(row, id) {
        var count = row.submittedCount;
        var errors = row.errorsCount;
        var content;
        if (count === 1) {
            content = count + ' ново';
        } else if (count > 1) {
            content = count + ' нови';
        } else if (count === -1) {
            content = '';
        } else {
            content = 'Няма нови';
        }
        if (errors > 0) {
            content += "<br />" + errors + " с грешки";
        }
        $("#" + id).html(content);
    }

    $(document).ready(function () {
        updateResult();
    });
</script>