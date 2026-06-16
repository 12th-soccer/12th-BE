package com.example.be12th.global.error;

import com.example.be12th.global.error.exception.App12thException;
import com.example.be12th.global.error.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    private static final String CHARACTER_ENCODING = "UTF-8";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (App12thException e) {
            ErrorCode errorCode = e.getErrorCode();
            writeErrorResponse(response, errorCode.getStatusCode(), ErrorResponse.of(errorCode, e.getMessage()));
        } catch (Exception e) {
            log.error("필터 체인 처리 중 예상하지 못한 에러", e);
            ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            writeErrorResponse(
                    response,
                    errorCode.getStatusCode(),
                    ErrorResponse.of(errorCode, errorCode.getErrorMessage())
            );
        }
    }

    private void writeErrorResponse(HttpServletResponse response, int statusCode, ErrorResponse errorResponse) throws IOException {
        if (response.isCommitted()) {
            return;
        }

        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
