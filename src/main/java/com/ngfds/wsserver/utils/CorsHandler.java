package com.ngfds.wsserver.utils;

import jakarta.servlet.http.HttpServletResponse;

public class CorsHandler {

    public static void handleCorsHeaders(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
    }

}
