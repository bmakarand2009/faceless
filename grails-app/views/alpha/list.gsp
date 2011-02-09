
<%@ page import="com.canarylogic.focalpoint.Alpha" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'alpha.label', default: 'Alpha')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'alpha.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="c1" title="${message(code: 'alpha.c1.label', default: 'C1')}" />
                        
                            <g:sortableColumn property="c2" title="${message(code: 'alpha.c2.label', default: 'C2')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${alphaInstanceList}" status="i" var="alphaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${alphaInstance.id}">${fieldValue(bean: alphaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: alphaInstance, field: "c1")}</td>
                        
                            <td>${fieldValue(bean: alphaInstance, field: "c2")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${alphaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
