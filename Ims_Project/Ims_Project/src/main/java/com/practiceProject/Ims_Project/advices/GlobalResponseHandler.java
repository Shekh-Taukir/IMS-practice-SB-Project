package com.practiceProject.Ims_Project.advices;

import lombok.AllArgsConstructor;
import org.apiguardian.api.API;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tools.jackson.databind.ObjectMapper;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        /// Swagger Gui causing error as its return true from here, so have to return false, for swagger's GUI API response
        String declaringClass = returnType.getDeclaringClass().getName();
        if((declaringClass.contains("springdoc")) || declaringClass.contains("swagger")){
            return false;
        }

        //Start Mar 29, 2026 TaukirS (ER 1105 - Custom Library Integration)
        /// this change allows the ResponseEntity<String> to return String as api response, because we need to tell support function to return this response
//        if (converterType.isAssignableFrom(StringHttpMessageConverter.class))
//            return true;
        //End Mar 29, 2026 TaukirS (ER 1105 - Custom Library Integration)

        return true;
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResponse<?>){
            return body;
        }

        //Start Mar 29, 2026 TaukirS (ER 1105 - Custom Library Integration)
        /// String is now handled by Jackson, so just wrap it normally.
//        if(body instanceof String){
//            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//            return objectMapper.writeValueAsString(new ApiResponse<>(body));
//        }
        //End Mar 29, 2026 TaukirS (ER 1105 - Custom Library Integration)

        return new ApiResponse<>(body);
    }
}
