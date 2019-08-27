package com.billlog.rest.salsapan.advice;

import com.billlog.rest.salsapan.advice.exception.*;
import com.billlog.rest.salsapan.model.response.CommonResult;
import com.billlog.rest.salsapan.service.ResponseService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/*
    ControllerAdvice의 annotation은 @ControllerAdvice @RestControllerAdvice 두 개가 있습니다.
    예외 발생 시 json형태로 결과를 반환하려면 @RestControllerAdvice로 선언하면 됩니다.
    어노테이션에 패키지를 적용하면 위에서 설명한 것처럼 특정 패키지 하위의 Controller에만 적용되게 할 수 있습니다.
    실습에서는 아무것도 적용하지 않아 프로젝트의 모든 Controller에 적용됩니다.
 */
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    private final MessageSource messageSource;

    public ExceptionAdvice(ResponseService responseService, MessageSource messageSource) {
        this.responseService = responseService;
        this.messageSource = messageSource;
    }
/*
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }
 */

    /**
     *  ===============================
     *  SALSA_COMMON
     *  ===============================
     **/

    // 글쓰기 작성 실패 예외처리
    @ExceptionHandler(CCommonWriteFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult commonWriteFailedException(HttpServletRequest request, CCommonWriteFailedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("commonWriteFailedException.code")), getMessage("commonWriteFailedException.msg"));
    }

    // 글 삭제 실패 예외처리
    @ExceptionHandler(CCommonDeleteFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult commonDeleteFailedException(HttpServletRequest request, CCommonDeleteFailedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("commonDeleteFailedException.code")), getMessage("commonDeleteFailedException.msg"));
    }

    // 글 수정 실패 예외처리
    @ExceptionHandler(CCommonUpdateFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult commonUpdateFailedException(HttpServletRequest request, CCommonUpdateFailedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("commonUpdateFailedException.code")), getMessage("commonUpdateFailedException.msg"));
    }

    /**
     * =======================================================
     * =======================================================
     */

    @ExceptionHandler(value = CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(value = CUserModifyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userModifyException(HttpServletRequest request, CUserModifyException e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("userModifyFailed.code")), getMessage("userModifyFailed.msg"));
    }

    //유저 삭제 예외처리.
    @ExceptionHandler(value = CUserDeleteException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userDeleteException(HttpServletRequest request, CUserDeleteException e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("userDeleteFailed.code")), getMessage("userDeleteFailed.msg"));
    }

    @ExceptionHandler(CEmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }
    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    //카카오와의 api통신중 문제가 발생하면 발생시킬 예외.
    @ExceptionHandler(CCommunicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult communicationException(HttpServletRequest request, CCommunicationException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("communicationError.code")), getMessage("communicationError.msg"));
    }

    //이미 가입한 회원인데 가입을 시도하려하면 예외처리합니다.
    @ExceptionHandler(CUserExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult userExistException(HttpServletRequest request, CUserExistException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("existingUser.code")), getMessage("existingUser.msg"));
    }

    //파일 업로드시 에러가 발생하면 익셉션처리
    @ExceptionHandler(CFileUploadException.class)
    public CommonResult fileUploadException(HttpServletRequest request, CFileUploadException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("fileUploadError.code")), getMessage("fileUploadError.msg"));
    }

    //파일 다운로드시 에러가 발생하면 익셉션처리.
    @ExceptionHandler(CFileDownloadException.class)
    public CommonResult fileDownloadException(HttpServletRequest request, CFileDownloadException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("fileDownloadError.code")), getMessage("fileDownloadError.msg"));
    }

    /**
     *  ===============================
     *  SALSA_INFO
     *  ===============================
     **/

    // 살사정보 게시글(클럽, 강습, 페스티벌)을 찾지 못했을 경우 에러
    @ExceptionHandler(CInfoArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult infoArticleNotFoundException(HttpServletRequest request, CInfoArticleNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("infoArticleNotFound.code")), getMessage("infoArticleNotFound.msg"));
    }

}
